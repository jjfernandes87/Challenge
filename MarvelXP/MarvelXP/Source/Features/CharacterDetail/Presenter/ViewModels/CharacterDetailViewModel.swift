//
//  CharacterDetailViewModel.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 18/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation

public struct CharacterDetailViewModel {
    
    var isLoading: Bool
    var id: Int
    var name: String
    var photoULR: String
    var isFavorite: Bool
    var description: String
    var hasError: Bool
    
    init() {
        self.init(nil, hasError: false)
    }
    
    init(_ entity: CharacterEntity?, hasError: Bool) {
        self.hasError = hasError
        self.isLoading = entity == nil
        self.id = entity?.id ?? -1
        self.name = entity?.name ?? ""
        self.photoULR = entity?.thumbnail?.getURLPath() ?? ""
        self.isFavorite = entity?.isFavorited ?? false
        self.description = entity?.description ?? ""
    }
}
