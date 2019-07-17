//
//  CharacterEntity.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 15/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import RogueKit

public struct CharacterEntity: Entity {
    var id: Int?
    var name: String?
    var description: String?
    var thumbnail: ThumbnailEntity?
    var comics: [ComicEntity]?
    var serieses: [SeriesEntity]?
}

import CoreData

public extension CharacterEntity {
    
    init(from coreDataManagedObject: NSManagedObject) {
        self.id = coreDataManagedObject.value(forKey: "id") as? Int
        self.name = coreDataManagedObject.value(forKey: "name") as? String
        self.description = coreDataManagedObject.value(forKey: "desc") as? String
        
        if let managedThumbnail = coreDataManagedObject.value(forKey: "thumbnail") as? NSManagedObject {
            self.thumbnail = ThumbnailEntity(from: managedThumbnail)
        }
        
        if let managedComics = coreDataManagedObject.value(forKey: "comics") as? NSMutableOrderedSet {
            self.comics = []
            for case let managedComic as NSManagedObject in managedComics {
                self.comics?.append(ComicEntity(from: managedComic))
            }
        }
        
        if let managedSerieses = coreDataManagedObject.value(forKey: "serieses") as? NSMutableOrderedSet {
            self.serieses = []
            for case let managedSeries as NSManagedObject in managedSerieses {
                self.serieses?.append(SeriesEntity(from: managedSeries))
            }
        }
    }
    
    func toManagedObject(_ context: NSManagedObjectContext, managedCharacter: NSManagedObject) -> NSManagedObject {
        
        managedCharacter.setValue(self.id, forKey: "id")
        managedCharacter.setValue(self.name, forKey: "name")
        managedCharacter.setValue(self.description, forKey: "desc")
        
        if let thumbnail = self.thumbnail?.toManagedObject(context) {
            managedCharacter.setValue(thumbnail, forKey: "thumbnail")
        }
        
        if let comicEntities = self.comics {
            let managedComics = managedCharacter.mutableOrderedSetValue(forKey: "comics")
            for comicEntity in comicEntities {
                if let comic = comicEntity.toManagedObject(context) {
                    managedComics.add(comic)
                }
            }
            managedCharacter.setValue(managedComics, forKey: "comics")
        }
        
        if let seriesEntities = self.serieses {
            let managedSerieses = managedCharacter.mutableOrderedSetValue(forKey: "serieses")
            for seriesEntity in seriesEntities {
                if let series = seriesEntity.toManagedObject(context) {
                    managedSerieses.add(series)
                }
            }
            managedCharacter.setValue(managedSerieses, forKey: "serieses")
        }
        
        return managedCharacter
    }
}
