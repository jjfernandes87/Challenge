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
    case fetchComics,
    fetchSeries
}

protocol CharacterDetailPresenterProtocol: DKAbstractPresenter {
    func processComics(_ comicList: [ComicEntity])
    func processSeries(_ serieList: [SerieEntity])
    func processError(_ errorType: CharacterDetailPresenterErrorType)
}
