//
//  Comics.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation
import CoreData

enum ComicsType {
    case comics
    case series
}

class Comics: Codable {
    var available, returned: Int?
    var collectionURI: String?
    var items: [ComicsItem]?
    
    init() {
        self.available = 0
        self.returned = 0
        self.collectionURI = ""
        self.items = []
    }
    
    enum CodingKeys: String, CodingKey {
        case available, returned
        case collectionURI
        case items
    }
    
    func managedObject(in context: NSManagedObjectContext, character: FavoriteCharacter, type: ComicsType) -> FavoriteComics {
        let comics = FavoriteComics(context: context)
        
        switch type {
        case .comics:
            comics.characterComics = character
        case .series:
            comics.characterSeries = character
        }
        
        _ = self.items?.map({ comics.addToItems($0.managedObject(in: context, comics: comics)) })
        return comics
    }
}

extension FavoriteComics {
    func getComics() -> Comics {
        let comics = Comics()
        comics.items = self.items?.compactMap({ ($0 as? FavoriteComicsItem)?.getComicsItem() })
        return comics
    }
}
