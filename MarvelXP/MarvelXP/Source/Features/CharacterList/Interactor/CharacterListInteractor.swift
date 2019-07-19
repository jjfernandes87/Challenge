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
        
        RogueKit.request(MarvelRepository.fetchCharacter(characterID: characterID)) { [unowned self] (result: ListResult<CharacterEntity>) in
            switch result {
            case let .success(characterList):
                guard let character = characterList.data?.results?.first else {
                    self.presenter?.processError(.addFavorite)
                    return
                }
                
                CoreDataManager.addFavorite(character) { [unowned self] (success) in
                    if success {
                        self.presenter?.processAddFavorite(characterID)
                    } else {
                        self.presenter?.processError(.addFavorite)
                    }
                }
            case .failure(_):
                self.presenter?.processError(.addFavorite)
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
}
