//
//  CharacterDetailViewModel.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 18/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation

public struct CharacterDetailViewModel {
    
    var id: Int
    var name: String
    var photoULR: String
    var isFavorite: Bool
    var description: String
    
    init(_ entity: CharacterEntity) {
        self.id = entity.id ?? -1
        self.name = entity.name ?? ""
        self.photoULR = entity.thumbnail?.getURLPath() ?? ""
        self.isFavorite = entity.isFavorited ?? false
        self.description = entity.description ?? ""
    }
}
