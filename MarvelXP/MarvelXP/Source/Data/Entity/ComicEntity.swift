//
//  ComicEntity.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 15/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import RogueKit

public struct ComicEntity: Entity {
    var title: String?
    var thumbnail: ThumbnailEntity?
}

import CoreData

public extension ComicEntity {
    init(from coreDataManagedObject: NSManagedObject) {
        self.title = coreDataManagedObject.value(forKey: "title") as? String
        if let managedThumbnail = coreDataManagedObject.value(forKey: "thumbnail") as? NSManagedObject {
            self.thumbnail = ThumbnailEntity(from: managedThumbnail)
        }
    }
    
    func toManagedObject(_ context: NSManagedObjectContext) -> NSManagedObject? {
        guard let coreDataEntity = NSEntityDescription.entity(forEntityName: "Comic", in: context) else { return nil }
        let managedComic = NSManagedObject(entity: coreDataEntity, insertInto: context)
        managedComic.setValue(self.title, forKey: "title")
        
        if let thumbnail = self.thumbnail?.toManagedObject(context) {
            managedComic.setValue(thumbnail, forKey: "thumbnail")
        }
        
        return managedComic
    }
}
