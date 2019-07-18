//
//  FavoriteListInteractor.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

class FavoriteListInteractor: DKInteractor {
    fileprivate var presenter: FavoriteListPresenterProtocol? { return self.getAbstractPresenter() as? FavoriteListPresenterProtocol }
}

extension FavoriteListInteractor: FavoriteListInteractorProtocol {
    func fetchFavorites() {
        if let favoriteList = CoreDataManager.fetchFavorites() {
            self.presenter?.processFavoriteList(favoriteList)
        } else {
            self.presenter?.processError(.fetchFavorites)
        }
    }
    
    func removeFavorite(character: CharacterEntity) {
        if CoreDataManager.removeFavorite(character) {
            self.presenter?.processRemoveFavorite(character)
        } else {
            self.presenter?.processError(.removeFavorite)
        }
    }
}
