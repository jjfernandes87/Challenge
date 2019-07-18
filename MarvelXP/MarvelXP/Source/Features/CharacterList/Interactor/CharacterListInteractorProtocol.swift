//
//  CharacterListInteractorProtocol.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

protocol CharacterListInteractorProtocol: DKAbstractInteractor {
    func fetchNextPage(searchFilter: String?)
    func refresh(searchFilter: String?)
    func addFavorite(character: CharacterEntity)
    func removeFavorite(character: CharacterEntity)
}
