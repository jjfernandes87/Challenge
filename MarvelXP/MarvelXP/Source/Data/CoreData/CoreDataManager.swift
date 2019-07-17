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

public class CoreDataManager {
    
    @discardableResult public static func addFavorite(_ characterEntity: CharacterEntity) -> Bool {
        
        guard let context = self.getContext(),
        let characterCoreDataEntity = NSEntityDescription.entity(forEntityName: "Character", in: context)
            else { return false }
        
        var managedCharacter = self.getFavoriteByID(characterEntity.id ?? -1) ?? NSManagedObject(entity: characterCoreDataEntity, insertInto: context)
        managedCharacter = characterEntity.toManagedObject(context, managedCharacter: managedCharacter)
        
        do {
            try context.save()
            return true
        } catch {
            return false
        }
    }
    
    public static func fetchFavorites() -> [CharacterEntity]? {
        guard let context = self.getContext() else { return nil }
        
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Character")
        request.sortDescriptors = [NSSortDescriptor(key: "name", ascending: true)]
        request.returnsObjectsAsFaults = false
        
        do {
            let result = try context.fetch(request)
            guard let characters = result as? [NSManagedObject] else { return nil }
            var characterEntities: [CharacterEntity] = []
            for character in characters {
                characterEntities.append(CharacterEntity(from: character))
            }
            return characterEntities
        } catch {
            return nil
        }
    }
    
    @discardableResult public static func removeFavorite(_ characterEntity: CharacterEntity) -> Bool {
        
        guard let context = self.getContext() else { return false }
        
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Character")
        request.predicate = NSPredicate(format: "id = %i", characterEntity.id ?? -1)
        request.returnsObjectsAsFaults = false
        
        do {
            let result = try context.fetch(request)
            guard let managedCharacter = (result as? [NSManagedObject])?.first else { return false }
            context.delete(managedCharacter)
            try context.save()
            return true
        } catch {
            return false
        }
    }

    private static func getFavoriteByID(_ characterID: Int) -> NSManagedObject? {
        
        guard let context = self.getContext() else { return nil }
        
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
    
    private static func getContext() -> NSManagedObjectContext? {
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else { return nil }
        return appDelegate.persistentContainer.viewContext
    }
    
    #if DEBUG
    public static func removeAllFavorites() {
        guard let context = self.getContext() else { return }
        
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Character")
        request.returnsObjectsAsFaults = false
        
        do {
            let result = try context.fetch(request)
            guard let characters = result as? [NSManagedObject] else { return }
            for character in characters {
                context.delete(character)
            }
            try context.save()
        } catch {
            return
        }
    }
    #endif
}
