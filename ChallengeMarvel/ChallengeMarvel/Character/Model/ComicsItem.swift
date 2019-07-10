//
//  ComicsItem.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation
import CoreData

class ComicsItem: Codable {
    var resourceURI, name: String?
    
    init() {
        self.resourceURI = ""
        self.name = ""
    }
    
    enum CodingKeys: String, CodingKey {
        case resourceURI, name
    }
    
    func managedObject(in context: NSManagedObjectContext, comics: FavoriteComics) -> FavoriteComicsItem {
        let comicsItem = FavoriteComicsItem(context: context)
        comicsItem.name = self.name
        comicsItem.comics = comics
        return comicsItem
    }
}

extension FavoriteComicsItem {
    func getComicsItem() -> ComicsItem {
        let comicsItem = ComicsItem()
        comicsItem.name = self.name
        return comicsItem
    }
}
