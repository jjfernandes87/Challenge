//
//  ThumbnailEntity.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 15/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import RogueKit

public struct ThumbnailEntity: Entity {
    var path: String?
    var `extension`: String?
    
    public func getURLPath() -> String {
        return "\(path ?? "").\(`extension` ?? "")"
    }
}

import CoreData

public extension ThumbnailEntity {
    init(from coreDataManagedObject: NSManagedObject) {
        self.path = coreDataManagedObject.value(forKey: "path") as? String
        self.`extension` = coreDataManagedObject.value(forKey: "ext") as? String
    }
    
    func toManagedObject(_ context: NSManagedObjectContext) -> NSManagedObject? {
        guard let coreDataEntity = NSEntityDescription.entity(forEntityName: "Thumbnail", in: context) else { return nil }
        let managedThumbnail = NSManagedObject(entity: coreDataEntity, insertInto: context)
        managedThumbnail.setValue(self.path, forKey: "path")
        managedThumbnail.setValue(self.extension, forKey: "ext")
        return managedThumbnail
    }
}
