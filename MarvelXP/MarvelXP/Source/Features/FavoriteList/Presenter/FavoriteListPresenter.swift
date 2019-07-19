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
    fileprivate var favoriteViewModels: [FavoriteViewModel] = []
    
    override init() {
        super.init()
        FavoriteObserver.shared.favoriteList = self
    }
}

extension FavoriteListPresenter: FavoriteListPresenterProtocol {
    
    func processFavoriteList(_ favoriteList: [CharacterEntity]) {
        
        self.favoriteViewModels = []
        
        for entity in favoriteList {
            self.favoriteViewModels.append(FavoriteViewModel(entity))
        }
        
        if self.favoriteViewModels.isEmpty {
            sync {
                self.view?.showEmptyState()
            }
        } else {
            sync {
                self.view?.showFavoriteList(self.favoriteViewModels)
            }
        }
    }
    
    func processRemoveFavorite(_ characterID: Int) {
        FavoriteObserver.shared.notifyFavoriteChange(characterID: characterID)
    }
    
    func processError(_ errorType: FavoriteListPresenterErrorType) {
        switch errorType {
        case .fetchFavorites:
            sync {
                self.view?.showFetchError()
            }
        case .removeFavorite:
            sync {
                self.view?.alertFavoriteError()
            }
        }
    }
}

extension FavoriteListPresenter: FavoriteObservable {
    func favoriteUpdated(characterID: Int) {
        if let index = self.favoriteViewModels.firstIndex(where: { $0.id == characterID }) {
            self.favoriteViewModels[index].isFavorite.toggle()
            sync {
                self.view?.showFavoriteList(self.favoriteViewModels)
            }
        }
    }
}
