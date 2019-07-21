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
    public var id: Int?
    public var name: String?
    public var description: String?
    public var thumbnail: ThumbnailEntity?
    
    public var isFavorited: Bool?
    public var favoriteComics: [ComicEntity]?
    public var favoriteSeries: [SerieEntity]?
    
    private enum CodingKeys: String, CodingKey {
        case id,
        name,
        description,
        thumbnail
    }
}

import CoreData

public extension CharacterEntity {
    
    init(from coreDataManagedObject: NSManagedObject) {
        self.id = coreDataManagedObject.value(forKey: "id") as? Int
        self.name = coreDataManagedObject.value(forKey: "name") as? String
        self.description = coreDataManagedObject.value(forKey: "desc") as? String
        self.isFavorited = true
        
        if let managedThumbnail = coreDataManagedObject.value(forKey: "thumbnail") as? NSManagedObject {
            self.thumbnail = ThumbnailEntity(from: managedThumbnail)
        }
        
        if let managedComics = coreDataManagedObject.value(forKey: "comics") as? NSMutableOrderedSet {
            self.favoriteComics = []
            for case let managedComic as NSManagedObject in managedComics {
                self.favoriteComics?.append(ComicEntity(from: managedComic))
            }
        }
        
        if let managedSeries = coreDataManagedObject.value(forKey: "series") as? NSMutableOrderedSet {
            self.favoriteSeries = []
            for case let managedSerie as NSManagedObject in managedSeries {
                self.favoriteSeries?.append(SerieEntity(from: managedSerie))
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
        
        if let comicEntities = self.favoriteComics {
            let managedComics = managedCharacter.mutableOrderedSetValue(forKey: "comics")
            for comicEntity in comicEntities {
                if let comic = comicEntity.toManagedObject(context) {
                    managedComics.add(comic)
                }
            }
            managedCharacter.setValue(managedComics, forKey: "comics")
        }
        
        if let serieEntities = self.favoriteSeries {
            let managedSeries = managedCharacter.mutableOrderedSetValue(forKey: "series")
            for serieEntity in serieEntities {
                if let serie = serieEntity.toManagedObject(context) {
                    managedSeries.add(serie)
                }
            }
            managedCharacter.setValue(managedSeries, forKey: "series")
        }
        
        return managedCharacter
    }
}
