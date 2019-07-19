//
//  FavoriteListViewControllerProtocol.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

protocol FavoriteListViewControllerProtocol: DKAbstractView {
    func showEmptyState()
    func showFavoriteList(_ viewModels: [FavoriteViewModel])
    func showFetchError()
    func alertFavoriteError()
}
