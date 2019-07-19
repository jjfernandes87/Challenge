//
//  CharacterDetailViewControllerProtocol.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

protocol CharacterDetailViewControllerProtocol: DKAbstractView {
    func showCharacter(_ viewModel: CharacterDetailViewModel)
    func showFetchError()
    func showComics(_ viewModels: [ComicViewModel])
    func showEmptyComics()
    func showFetchComicsError()
    func showSeries(_ viewModels: [SerieViewModel])
    func showEmptySeries()
    func showFetchSeriesError()
    func alertFavoriteError(adding: Bool)
    func toogleFavorite()
}
