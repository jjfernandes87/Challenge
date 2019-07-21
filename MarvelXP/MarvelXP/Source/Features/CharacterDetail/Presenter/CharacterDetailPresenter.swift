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
        let viewModel = CharacterDetailViewModel(character, hasError: false)
        self.character = viewModel
        sync {
            self.view?.showCharacter(viewModel)
        }
    }
    
    func processComics(_ comicList: [ComicEntity]) {
        sync {
            self.view?.showComics(ComicListViewModel(comicList, hasError: false))
        }
    }
    
    func processSeries(_ serieList: [SerieEntity]) {
        sync {
            self.view?.showSeries(SerieListViewModel(serieList, hasError: false))
        }
    }
    
    func processError(_ errorType: CharacterDetailPresenterErrorType) {
        switch errorType {
        case .fetchCharacter:
            sync {
                self.view?.showCharacter(CharacterDetailViewModel(nil, hasError: true))
            }
        case .fetchComics:
            sync {
                self.view?.showComics(ComicListViewModel(nil, hasError: true))
            }
        case .fetchSeries:
            sync {
                self.view?.showSeries(SerieListViewModel(nil, hasError: true))
            }
        case .addFavorite:
            sync {
                self.view?.alertFavoriteError(adding: true)
            }
        case .removeFavorite:
            sync {
                self.view?.alertFavoriteError(adding: false)
            }
        case .internetConnection:
            sync {
                self.view?.showInternetError()
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
