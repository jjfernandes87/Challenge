//
//  CharacterListPresenterProtocol.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

public enum CharacterListPresenterErrorType {
    case fetch,
    addFavorite,
    removeFavorite,
    internetConnection
}

protocol CharacterListPresenterProtocol: DKAbstractPresenter {
    func processCharacters(_ characterList: [CharacterEntity], hasMore: Bool)
    func processAddFavorite(_ character: CharacterEntity)
    func processRemoveFavorite(_ character: CharacterEntity)
    func refresh()
    func processError(_ errorType: CharacterListPresenterErrorType)
}
