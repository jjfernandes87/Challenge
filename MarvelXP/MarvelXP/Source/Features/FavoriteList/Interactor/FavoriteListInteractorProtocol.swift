//
//  FavoriteListInteractorProtocol.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

protocol FavoriteListInteractorProtocol: DKAbstractInteractor {
    func fetchFavorites()
    func removeFavorite(characterID: Int)
}
