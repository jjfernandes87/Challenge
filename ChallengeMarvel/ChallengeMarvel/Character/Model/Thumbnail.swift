//
//  Thumbnail.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation
import CoreData
import UIKit

class Thumbnail: Codable {
    var path, thumbnailExtension: String?
    var image: UIImage?
    
    init() {
        self.path = ""
        self.thumbnailExtension = ""
    }
    
    enum CodingKeys: String, CodingKey {
        case path
        case thumbnailExtension = "extension"
    }
    
    func managedObject(in context: NSManagedObjectContext, character: FavoriteCharacter) -> FavoriteThumbnail {
        let thumbnail = FavoriteThumbnail(context: context)
        thumbnail.path = self.path
        thumbnail.thumbnailExtension = self.thumbnailExtension
        thumbnail.character = character
        
        Download.downloadImage(withURL: "\(self.path ?? "").\(self.thumbnailExtension ?? "")") { (image) in
            thumbnail.image = image
        }
        
        return thumbnail
    }
}

extension FavoriteThumbnail {
    func getThumbnail() -> Thumbnail {
        let thumbnail = Thumbnail()
        thumbnail.path = self.path
        thumbnail.thumbnailExtension = self.thumbnailExtension
        thumbnail.image = self.image as? UIImage
        return thumbnail
    }
}
