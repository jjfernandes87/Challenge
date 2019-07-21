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
    func showComics(_ viewModel: ComicListViewModel)
    func showSeries(_ viewModel: SerieListViewModel)
    func alertFavoriteError(adding: Bool)
    func toogleFavorite()
    func showInternetError()
}
