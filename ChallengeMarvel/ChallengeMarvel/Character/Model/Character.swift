//
//  Character.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation
import CoreData

class Character: Codable {
    var id: Int
    var name, resultDescription: String
    var thumbnail: Thumbnail?
    var comics: Comics?
    var series: Comics?
    
    init() {
        self.id = 0
        self.name = ""
        self.resultDescription = ""
    }
    
    enum CodingKeys: String, CodingKey {
        case id, name
        case resultDescription = "description"
        case thumbnail, comics, series
    }
    
    func managedObject(in context: NSManagedObjectContext) -> FavoriteCharacter {
        let character = FavoriteCharacter(context: context)
        character.id = Int64(self.id)
        character.name = self.name
        character.resultDescription = self.resultDescription
        character.thumbnail = self.thumbnail?.managedObject(in: context, character: character)
        character.comics = self.comics?.managedObject(in: context, character: character, type: .comics)
        character.series = self.series?.managedObject(in: context, character: character, type: .series)
        
        return character
    }
    
}

extension FavoriteCharacter {
    func getCharacter() -> Character {
        let character = Character()
        character.id = Int(self.id)
        character.name = self.name ?? ""
        character.resultDescription = self.resultDescription ?? ""
        character.thumbnail = self.thumbnail?.getThumbnail()
        character.comics = self.comics?.getComics()
        character.series = self.series?.getComics()
        return character
    }
}
