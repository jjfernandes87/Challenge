//
//  CharacterDetailPresenter.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

class CharacterDetailPresenter: DKPresenter {
    fileprivate var view: CharacterDetailViewControllerProtocol? { return self.getAbstractView() as? CharacterDetailViewControllerProtocol }
    fileprivate var character: CharacterDetailViewModel?
    
    init(isFavoriteDetail: Bool) {
        super.init()
        
        if isFavoriteDetail {
            FavoriteObserver.shared.favoriteDetail = self
        } else {
            FavoriteObserver.shared.characterDetail = self
        }
    }
}

extension CharacterDetailPresenter: CharacterDetailPresenterProtocol {
    func processCharacter(_ character: CharacterEntity) {
        self.character = CharacterDetailViewModel(character)
        
        guard let character = self.character else { return }
        sync {
            self.view?.showCharacter(character)
        }
    }
    
    func processComics(_ comicList: [ComicEntity]) {
        var viewModels: [ComicViewModel] = []
        
        for entity in comicList {
            viewModels.append(ComicViewModel(entity))
        }
        
        if viewModels.isEmpty {
            sync {
                self.view?.showEmptyComics()
            }
        } else {
            sync {
                self.view?.showComics(viewModels)
            }
        }
    }
    
    func processSeries(_ serieList: [SerieEntity]) {
        var viewModels: [SerieViewModel] = []
        
        for entity in serieList {
            viewModels.append(SerieViewModel(entity))
        }
        
        if viewModels.isEmpty {
            sync {
                self.view?.showEmptySeries()
            }
        } else {
            sync {
                self.view?.showSeries(viewModels)
            }
        }
    }
    
    func processError(_ errorType: CharacterDetailPresenterErrorType) {
        switch errorType {
        case .fetchCharacter:
            sync {
                self.view?.showFetchError()
            }
        case .fetchComics:
            sync {
                self.view?.showFetchComicsError()
            }
        case .fetchSeries:
            sync {
                self.view?.showFetchSeriesError()
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
    
    func processAddFavorite(_ characterID: Int) {
        FavoriteObserver.shared.notifyFavoriteChange(characterID: characterID)
    }
    
    func processRemoveFavorite(_ characterID: Int) {
        FavoriteObserver.shared.notifyFavoriteChange(characterID: characterID)
    }
}

extension CharacterDetailPresenter: FavoriteObservable {
    func favoriteUpdated(characterID: Int) {
        if (self.character?.id ?? -1) == characterID {
            sync {
                self.view?.toogleFavorite()
            }
        }
    }
}
