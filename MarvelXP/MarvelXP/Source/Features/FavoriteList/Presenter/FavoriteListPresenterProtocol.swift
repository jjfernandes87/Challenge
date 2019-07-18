//
//  FavoriteListPresenterProtocol.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

public enum FavoriteListPresenterErrorType {
    case fetchFavorites,
    removeFavorite
}

protocol FavoriteListPresenterProtocol: DKAbstractPresenter {
    func processFavoriteList(_ favoriteList: [CharacterEntity])
    func processRemoveFavorite(_ characterID: Int)
    func processError(_ errorType: FavoriteListPresenterErrorType)
}

