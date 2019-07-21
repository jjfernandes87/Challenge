//
//  CoreDataManager.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 16/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import UIKit
import CoreData
import PaladinKit

public class CoreDataManager {
    
    private static func getContext() -> NSManagedObjectContext? {
        precondition(Thread.isMainThread, "This method should be called from the main thread.")
        
        let appDelegate = UIApplication.shared.delegate as? AppDelegate
        return appDelegate?.persistentContainer.viewContext
    }
    
    private static func fetchManagedFavorite(_ characterID: Int, context: NSManagedObjectContext) -> NSManagedObject? {
        precondition(Thread.isMainThread, "This method should be called from the main thread.")
        
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Character")
        request.predicate = NSPredicate(format: "id = %i", characterID)
        request.returnsObjectsAsFaults = false
            
        do {
            let result = try context.fetch(request)
            return (result as? [NSManagedObject])?.first
        } catch {
            return nil
        }
    }
    
    public static func addFavorite(_ characterEntity: CharacterEntity, completion: @escaping (Bool)-> Void ) {
        DispatchQueue.safeSync {
          
            guard let context = getContext(),
                let characterCoreDataEntity = NSEntityDescription.entity(forEntityName: "Character", in: context)
                else {
                    DispatchQueue.async { completion(false) }
                    return
            }
            
            var managedCharacter = self.fetchManagedFavorite(characterEntity.id ?? -1, context: context) ?? NSManagedObject(entity: characterCoreDataEntity, insertInto: context)
            managedCharacter = characterEntity.toManagedObject(context, managedCharacter: managedCharacter)
            do {
                try context.save()
                DispatchQueue.async { completion(true) }
            } catch {
                DispatchQueue.async { completion(false) }
            }
        }
    }
    
    public static func removeFavorite(_ characterID: Int, completion: @escaping (Bool) -> Void) {
        DispatchQueue.safeSync {
            
            guard let context = getContext(),
                let managedCharacter = fetchManagedFavorite(characterID, context: context)
                else {
                    DispatchQueue.async { completion(false) }
                    return
            }
            
            do {
                context.delete(managedCharacter)
                try context.save()
                DispatchQueue.async { completion(true) }
            } catch {
                DispatchQueue.async { completion(false) }
            }
        }
        
    }
    
    public static func fetchFavorites(_ completion: @escaping ([CharacterEntity]?) -> Void) {
        DispatchQueue.safeSync {
        
            guard let context = getContext() else {
                DispatchQueue.async { completion(nil) }
                return
            }
            
            let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Character")
            request.sortDescriptors = [NSSortDescriptor(key: "name", ascending: true)]
            request.returnsObjectsAsFaults = false
            
            do {
                let result = try context.fetch(request)
                guard let characters = result as? [NSManagedObject] else {
                    DispatchQueue.async { completion(nil) }
                    return
                }
                var characterEntities: [CharacterEntity] = []
                for character in characters {
                    characterEntities.append(CharacterEntity(from: character))
                }
                DispatchQueue.async { completion(characterEntities) }
            } catch {
                DispatchQueue.async { completion(nil) }
            }
        }
    }
    
    public static func fetchFavorite(_ characterID: Int, completion: @escaping (CharacterEntity?) -> Void) {
        DispatchQueue.safeSync {
        
            guard let context = getContext(),
            let managedCharacter = fetchManagedFavorite(characterID, context: context)
            else {
                DispatchQueue.async { completion(nil) }
                return
            }
            
            DispatchQueue.async { completion(CharacterEntity(from: managedCharacter)) }
        }
    }

    #if DEBUG
    public static func removeAllFavorites(completion: (() -> Void)?) {
        DispatchQueue.safeSync {
            guard let context = getContext() else { return }
            
            let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Character")
            request.returnsObjectsAsFaults = false
            
            do {
                let result = try context.fetch(request)
                guard let characters = result as? [NSManagedObject] else { return }
                for character in characters {
                    context.delete(character)
                }
                try context.save()
                DispatchQueue.async { completion?() }
            } catch {
                return
            }
        }
    }
    #endif
}
