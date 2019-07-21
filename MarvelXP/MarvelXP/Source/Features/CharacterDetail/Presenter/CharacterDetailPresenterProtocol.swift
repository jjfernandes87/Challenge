//
//  CharacterDetailPresenterProtocol.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

public enum CharacterDetailPresenterErrorType {
    case fetchCharacter,
    fetchComics,
    fetchSeries,
    addFavorite,
    removeFavorite,
    internetConnection
}

protocol CharacterDetailPresenterProtocol: DKAbstractPresenter {
    func processCharacter(_ character: CharacterEntity)
    func processComics(_ comicList: [ComicEntity])
    func processSeries(_ serieList: [SerieEntity])
    func processAddFavorite(_ characterID: Int)
    func processRemoveFavorite(_ characterID: Int)
    func processError(_ errorType: CharacterDetailPresenterErrorType)
}
