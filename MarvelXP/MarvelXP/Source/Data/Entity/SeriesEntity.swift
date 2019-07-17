//
//  SeriesEntity.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 15/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import RogueKit

public struct SeriesEntity: Entity {
    var title: String?
    var thumbnail: ThumbnailEntity?
}

import CoreData

public extension SeriesEntity {
    init(from coreDataManagedObject: NSManagedObject) {
        self.title = coreDataManagedObject.value(forKey: "title") as? String
        if let managedThumbnail = coreDataManagedObject.value(forKey: "thumbnail") as? NSManagedObject {
            self.thumbnail = ThumbnailEntity(from: managedThumbnail)
        }
    }
    
    func toManagedObject(_ context: NSManagedObjectContext) -> NSManagedObject? {
        guard let coreDataEntity = NSEntityDescription.entity(forEntityName: "Series", in: context) else { return nil }
        let managedSeries = NSManagedObject(entity: coreDataEntity, insertInto: context)
        managedSeries.setValue(self.title, forKey: "title")
        
        if let thumbnail = self.thumbnail?.toManagedObject(context) {
            managedSeries.setValue(thumbnail, forKey: "thumbnail")
        }
        
        return managedSeries
    }
}
