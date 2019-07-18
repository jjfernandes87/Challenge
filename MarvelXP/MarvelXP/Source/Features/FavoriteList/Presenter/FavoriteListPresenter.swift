//
//  FavoriteListPresenter.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

class FavoriteListPresenter: DKPresenter {
    fileprivate var view: FavoriteListViewControllerProtocol? { return self.getAbstractView() as? FavoriteListViewControllerProtocol }
}

extension FavoriteListPresenter: FavoriteListPresenterProtocol {
    
    func processFavoriteList(_ favoriteList: [CharacterEntity]) {
        
    }
    
    func processRemoveFavorite(_ characterID: Int) {
        
    }
    
    func processError(_ errorType: FavoriteListPresenterErrorType) {
        
    }
}
