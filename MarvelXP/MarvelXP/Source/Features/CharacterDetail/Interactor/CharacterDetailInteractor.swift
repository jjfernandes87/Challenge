//
//  CharacterDetailInteractor.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit
import RogueKit
import PaladinKit

class CharacterDetailInteractor: DKInteractor {
    fileprivate var presenter: CharacterDetailPresenterProtocol? { return self.getAbstractPresenter() as? CharacterDetailPresenterProtocol }
}

extension CharacterDetailInteractor: CharacterDetailInteractorProtocol {
    
    func fetchCharacter(characterID: Int) {
        CoreDataManager.fetchFavorite(characterID) { [unowned self] (optionalCharacter) in
            if let character = optionalCharacter {
                self.presenter?.processCharacter(character)
                //TODO: arrumar aqui
                /*if let comics = character.favoriteComics {
                    self.presenter?.processComics(comics)
                }
                
                if let series = character.favoriteSeries {
                    self.presenter?.processSeries(series)
                }*/
            } else {
                
                guard Reachability.isConnectedToNetwork() else {
                    self.presenter?.processError(.internetConnection)
                    return
                }
                
                self.requestFetchCharacter(characterID: characterID, errorType: .fetchCharacter) { [unowned self] (character: CharacterEntity) in
                     self.presenter?.processCharacter(character)
                }
            }
        }
    }
    
    func fetchComics(characterID: Int) {
        
        CoreDataManager.fetchFavorite(characterID) { [unowned self] (optionalCharacter) in
            guard let character = optionalCharacter,
                let comics = character.favoriteComics else {
                    self.requestFetchComics(characterID: characterID, errorType: .fetchComics) { [unowned self] (comics) in
                        self.presenter?.processComics(comics)
                    }
                    return
            }
            
            self.presenter?.processComics(comics)
        }
    }
    
    func fetchSeries(characterID: Int) {
        
        CoreDataManager.fetchFavorite(characterID) { [unowned self] (optionalCharacter) in
            guard let character = optionalCharacter,
                let series = character.favoriteSeries else {
                    self.requestFetchSeries(characterID: characterID, errorType: .fetchSeries) { [unowned self] (series) in
                        self.presenter?.processSeries(series)
                    }
                    return
            }
            
            self.presenter?.processSeries(series)
        }
    }
    
    func addFavorite(characterID: Int) {
        
        guard Reachability.isConnectedToNetwork() else {
            self.presenter?.processError(.internetConnection)
            return
        }
        
        var favorite: CharacterEntity = CharacterEntity(id: 0, name: nil, description: nil, thumbnail: nil, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        
        self.requestFetchCharacter(characterID: characterID, errorType: .addFavorite) { [unowned self] (character) in
            favorite = character
            self.requestFetchComics(characterID: characterID, errorType: .addFavorite) { [unowned self] (comics) in
                favorite.favoriteComics = comics
                self.requestFetchSeries(characterID: characterID, errorType: .addFavorite) { [unowned self] (series) in
                    favorite.favoriteSeries = series
                    
                    CoreDataManager.addFavorite(favorite) { [unowned self] (success) in
                        if success {
                            self.presenter?.processAddFavorite(characterID)
                        } else {
                            self.presenter?.processError(.addFavorite)
                        }
                    }
                }
            }
        }
    }
    
    func removeFavorite(characterID: Int) {
        CoreDataManager.removeFavorite(characterID) { [unowned self] (success) in
            if success {
                self.presenter?.processRemoveFavorite(characterID)
            } else {
                self.presenter?.processError(.removeFavorite)
            }
        }
    }
    
    private func requestFetchCharacter(characterID: Int, errorType: CharacterDetailPresenterErrorType, completion: @escaping (CharacterEntity) -> Void) {
        RogueKit.request(MarvelRepository.fetchCharacter(characterID: characterID)) { [unowned self] (result: ListResult<CharacterEntity>) in
            switch result {
            case let .success(characterList):
                guard let character = characterList.data?.results?.first else {
                    self.presenter?.processError(errorType)
                    return
                }
                
                completion(character)
            case .failure(_):
                self.presenter?.processError(errorType)
            }
        }
    }
    
    private func requestFetchComics(characterID: Int, errorType: CharacterDetailPresenterErrorType, completion: @escaping ([ComicEntity]) -> Void) {
        RogueKit.request(MarvelRepository.fetchComics(characterID: characterID)) { [unowned self] (result: ListResult<ComicEntity>) in
            switch result {
            case let .success(comicList):
                completion(comicList.data?.results ?? [])
            case .failure(_):
                self.presenter?.processError(errorType)
            }
        }
    }
    
    private func requestFetchSeries(characterID: Int, errorType: CharacterDetailPresenterErrorType, completion: @escaping ([SerieEntity]) -> Void) {
        RogueKit.request(MarvelRepository.fetchSeries(characterID: characterID)) { [unowned self] (result: ListResult<SerieEntity>) in
            switch result {
            case let .success(serieList):
                completion(serieList.data?.results ?? [])
            case .failure(_):
                self.presenter?.processError(errorType)
            }
        }
    }
}
