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
        CoreDataManager.fetchFavorites { (optionalFavoriteList) in
            if let favoriteList = optionalFavoriteList {
                self.presenter?.processFavoriteList(favoriteList)
            } else {
                self.presenter?.processError(.fetchFavorites)
            }
        }
    }
    
    func removeFavorite(characterID: Int) {
        CoreDataManager.removeFavorite(characterID) { (success) in
            if success {
                self.presenter?.processRemoveFavorite(characterID)
            } else {
                self.presenter?.processError(.removeFavorite)
            }
        }
    }
}
