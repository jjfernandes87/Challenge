//
//  CharacterDetailInteractorProtocol.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

protocol CharacterDetailInteractorProtocol: DKAbstractInteractor {
    func fetchCharacter(characterID: Int)
    func fetchComics(characterID: Int)
    func fetchSeries(characterID: Int)
    func addFavorite(characterID: Int)
    func removeFavorite(characterID: Int)
    func addFavoriteObserver(_ isFavoriteDetail: Bool)
}
