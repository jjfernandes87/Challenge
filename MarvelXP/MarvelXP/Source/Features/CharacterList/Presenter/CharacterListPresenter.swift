//
//  CharacterListPresenter.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

class CharacterListPresenter: DKPresenter {
    fileprivate var view: CharacterListViewControllerProtocol? { return self.getAbstractView() as? CharacterListViewControllerProtocol }
    fileprivate var characterViewModels: [CharacterViewModel] = []
    fileprivate var hasMore: Bool = false
    
    override init() {
        super.init()
        FavoriteObserver.shared.characterList = self
    }
}

extension CharacterListPresenter: CharacterListPresenterProtocol {
    
    func processCharacters(_ characterList: [CharacterEntity], hasMore: Bool) {
        
        self.hasMore = hasMore
        
        for entity in characterList {
            characterViewModels.append(CharacterViewModel(entity))
        }
        
        if self.characterViewModels.isEmpty {
            sync {
                self.view?.showEmptyState()
            }
        } else {
            sync {
                self.view?.updateCharacterList(characterViewModels, hasMore: hasMore)
            }
        }
    }
    
    func processAddFavorite(_ characterID: Int) {
        FavoriteObserver.shared.notifyFavoriteChange(characterID: characterID)
    }
    
    func processRemoveFavorite(_ characterID: Int) {
        FavoriteObserver.shared.notifyFavoriteChange(characterID: characterID)
    }
    
    func refresh() {
        self.characterViewModels = []
    }
    
    func processError(_ errorType: CharacterListPresenterErrorType) {
        switch errorType {
        case .fetch:
            sync {
                self.view?.showFetchError()
            }
        case .internetConnection:
            sync {
                self.view?.showInternetError()
            }
        case .addFavorite:
            sync {
                self.view?.alertFavoriteError(adding: true)
            }
        case .removeFavorite:
            sync {
                self.view?.alertFavoriteError(adding: false)
            }
        }
    }
}

extension CharacterListPresenter: FavoriteObservable {
    func favoriteUpdated(characterID: Int) {
        if let index = self.characterViewModels.firstIndex(where: { $0.id == characterID }) {
            self.characterViewModels[index].isFavorite.toggle()
            sync {
                self.view?.updateCharacterList(self.characterViewModels, hasMore: self.hasMore)
            }
        }
    }
}
