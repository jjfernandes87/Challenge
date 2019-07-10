//
//  CharacterViewModel.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation
import CoreData
import UIKit

class CharacterViewModel {
    
    private let character: Character?
    
    let context: NSManagedObjectContext?
    let name: String
    let description: String
    var favorite: Bool = false
    let comics: [ComicsItem]?
    let series: [ComicsItem]?
    var image: UIImage?
    let id: Int
    
    var thumbnailViewModel: ThumbnailViewModel?
    
    init(context: NSManagedObjectContext?, character: Character?, managedObject: NSManagedObject?) {
        if let managedObject = managedObject {
            self.character = (managedObject as? FavoriteCharacter)?.getCharacter()
        } else {
            self.character = character
        }
        
        self.id = character?.id ?? 0
        self.context = context
        self.name = self.character?.name ?? ""
        self.description = self.character?.resultDescription ?? ""
        self.comics = self.character?.comics?.items
        self.series = self.character?.series?.items
        
        isFavorite()
        
        if let thumbnail = self.character?.thumbnail {
            thumbnailViewModel = ThumbnailViewModel(thumbnail: thumbnail)
            self.image = thumbnailViewModel?.image
        }
    }
    
    private func isFavorite() {
        if let _ = getFavoriteCharacter() {
            favorite = true
        }
    }
    
    func save(completion: @escaping () -> Void) {
        guard let context = context else { return }
        
        if let managedObject = getFavoriteCharacter() {
            context.delete(managedObject)
            
            do {
                try context.save()
                favorite = false
            } catch {
                print("Failed delete")
            }
            
        } else {
            let _ = character?.managedObject(in: context)
            
            do {
                try context.save()
                favorite = true
            } catch {
                print("Failed saving")
            }
        }
        
        completion()
    }
    
    private func getFavoriteCharacter() -> NSManagedObject? {
        guard let context = context else { return nil }
        
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Character")
        request.predicate = NSPredicate(format: "id == %@", NSNumber(value: character?.id ?? 0))
        request.returnsObjectsAsFaults = false
        
        let result = try? context.fetch(request)
        
        if let managedObject = result?.first as? NSManagedObject {
            return managedObject
        }
        return nil
    }
    
}
