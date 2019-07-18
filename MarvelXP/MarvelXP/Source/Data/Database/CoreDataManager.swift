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
    
    public static func loadContext(completion: @escaping (NSManagedObjectContext?) -> Void) {
        DispatchQueue.safeSync {
            let appDelegate = UIApplication.shared.delegate as? AppDelegate
            let context = appDelegate?.persistentContainer.viewContext
            
            DispatchQueue.async {
                completion(context)
            }
        }
    }
    
    public static func addFavorite(_ characterEntity: CharacterEntity, completion: @escaping (Bool)-> Void ) {
        self.loadContext { (optionalContext) in
            
            guard let context = optionalContext,
            let characterCoreDataEntity = NSEntityDescription.entity(forEntityName: "Character", in: context)
            else {
                completion(false)
                return
            }
            
            var managedCharacter = self.fetchManagedFavorite(characterEntity.id ?? -1, context: context) ?? NSManagedObject(entity: characterCoreDataEntity, insertInto: context)
            managedCharacter = characterEntity.toManagedObject(context, managedCharacter: managedCharacter)
            do {
                try context.save()
                completion(true)
            } catch {
                completion(false)
            }
        }
    }
    
    public static func fetchFavorites(_ completion: @escaping ([CharacterEntity]?) -> Void) {
        self.loadContext { (optionalContext) in
            guard let context = optionalContext else {
                completion(nil)
                return
            }
            
            let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Character")
            request.sortDescriptors = [NSSortDescriptor(key: "name", ascending: true)]
            request.returnsObjectsAsFaults = false
            
            do {
                let result = try context.fetch(request)
                guard let characters = result as? [NSManagedObject] else {
                    completion(nil)
                    return
                }
                var characterEntities: [CharacterEntity] = []
                for character in characters {
                    characterEntities.append(CharacterEntity(from: character))
                }
                completion(characterEntities)
            } catch {
                completion(nil)
            }
        }
    }
    
    public static func removeFavorite(_ characterID: Int, completion: @escaping (Bool) -> Void) {
        self.loadContext { (optionalContext) in
            guard let context = optionalContext,
            let managedCharacter = fetchManagedFavorite(characterID, context: context)
            else {
                completion(false)
                return
            }
            
            do {
                context.delete(managedCharacter)
                try context.save()
                completion(true)
            } catch {
                completion(false)
            }
        }
    }
    
    public static func fetchFavorite(_ characterID: Int, completion: @escaping (CharacterEntity?) -> Void) {
        self.loadContext { (optionalContext) in
            guard let context = optionalContext,
            let managedCharacter = fetchManagedFavorite(characterID, context: context)
            else {
                completion(nil)
                return
            }
            
            completion(CharacterEntity(from: managedCharacter))
        }
    }

    private static func fetchManagedFavorite(_ characterID: Int, context: NSManagedObjectContext) -> NSManagedObject? {
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
    
    #if DEBUG
    public static func removeAllFavorites(completion: (() -> Void)?) {
        self.loadContext { (optionalContext) in
            guard let context = optionalContext else { return }
            
            let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Character")
            request.returnsObjectsAsFaults = false
            
            do {
                let result = try context.fetch(request)
                guard let characters = result as? [NSManagedObject] else { return }
                for character in characters {
                    context.delete(character)
                }
                try context.save()
                completion?()
            } catch {
                return
            }
        }
    }
    #endif
}
