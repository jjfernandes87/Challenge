//
//  CharacterListInteractor.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit
import RogueKit
import PaladinKit

class CharacterListInteractor: DKInteractor {
    fileprivate var presenter: CharacterListPresenterProtocol? { return self.getAbstractPresenter() as? CharacterListPresenterProtocol }
    
    fileprivate var offset = 0
    fileprivate let pageSize = 20
}

extension CharacterListInteractor: CharacterListInteractorProtocol {
    func fetchNextPage(searchFilter: String?) {
        
        guard Reachability.isConnectedToNetwork() else {
            self.presenter?.processError(.internetConnection)
            return
        }
        
        CoreDataManager.fetchFavorites { (optionalFavoriteList) in
            
            let favoriteList = optionalFavoriteList ?? []
            
            RogueKit.request(MarvelRepository.fetchCharacters(offset: self.offset, limit: self.offset + self.pageSize, searchName: searchFilter)) { [unowned self] (result: ListResult<CharacterEntity>) in
                switch result {
                case let .success(characterList):
                    self.offset += self.pageSize
                    let hasMore = self.offset < (characterList.data?.total ?? 0) && (characterList.data?.results?.count ?? 0) == self.pageSize
                    var entityList = characterList.data?.results ?? []
                    for (index, entity) in entityList.enumerated() {
                        let favoriteCharacter = favoriteList.first(where: { $0.id == entity.id })
                        entityList[index].isFavorited = favoriteCharacter != nil
                    }
                    self.presenter?.processCharacters(entityList, hasMore: hasMore)
                case .failure(_):
                    self.presenter?.processError(.fetch)
                }
            }
        }
    }
    
    func refresh(searchFilter: String?) {
        self.offset = 0
        self.presenter?.refresh()
        self.fetchNextPage(searchFilter: searchFilter)
    }
    
    func addFavorite(characterID: Int) {
        
        guard Reachability.isConnectedToNetwork() else {
            self.presenter?.processError(.internetConnection)
            return
        }
        
        var favorite: CharacterEntity = CharacterEntity(id: 0, name: nil, description: nil, thumbnail: nil, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        
        self.requestFetchCharacter(characterID: characterID) { [unowned self] (character) in
            favorite = character
            self.requestFetchComics(characterID: characterID) { [unowned self] (comics) in
                favorite.favoriteComics = comics
                self.requestFetchSeries(characterID: characterID) { [unowned self] (series) in
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
    
    private func requestFetchCharacter(characterID: Int, completion: @escaping (CharacterEntity) -> Void) {
        RogueKit.request(MarvelRepository.fetchCharacter(characterID: characterID)) { [unowned self] (result: ListResult<CharacterEntity>) in
            switch result {
            case let .success(characterList):
                guard let character = characterList.data?.results?.first else {
                    self.presenter?.processError(.addFavorite)
                    return
                }
                
                completion(character)
            case .failure(_):
                self.presenter?.processError(.addFavorite)
            }
        }
    }
    
    private func requestFetchComics(characterID: Int, completion: @escaping ([ComicEntity]) -> Void) {
        RogueKit.request(MarvelRepository.fetchComics(characterID: characterID)) { [unowned self] (result: ListResult<ComicEntity>) in
            switch result {
            case let .success(comicList):
                completion(comicList.data?.results ?? [])
            case .failure(_):
                self.presenter?.processError(.addFavorite)
            }
        }
    }
    
    private func requestFetchSeries(characterID: Int, completion: @escaping ([SerieEntity]) -> Void) {
        RogueKit.request(MarvelRepository.fetchSeries(characterID: characterID)) { [unowned self] (result: ListResult<SerieEntity>) in
            switch result {
            case let .success(serieList):
                completion(serieList.data?.results ?? [])
            case .failure(_):
                self.presenter?.processError(.addFavorite)
            }
        }
    }
}
