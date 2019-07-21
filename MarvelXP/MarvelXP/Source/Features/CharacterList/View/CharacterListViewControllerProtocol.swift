//
//  CharacterListViewControllerProtocol.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

protocol CharacterListViewControllerProtocol: DKAbstractView {
    func showEmptyState()
    func updateCharacterList(_ viewModels: [CharacterViewModel], hasMore: Bool)
    func showFetchError()
    func showInternetError()
    func alertFavoriteError(adding: Bool)
}
