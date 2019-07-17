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
    func loadNextPage(searchFilter: String?) {
        
        guard Reachability.isConnectedToNetwork() else {
            self.presenter?.connectionFailed()
            return
        }
        
        RogueKit.request(MarvelRepository.fetchCharacters(offset: self.offset, limit: self.offset + self.pageSize, searchName: searchFilter)) { [unowned self] (result: ListResult<CharacterEntity>) in
            switch result {
            case let .success(characterList):
                self.offset += self.pageSize
                let hasMore = self.offset < (characterList.data?.total ?? 0) && (characterList.data?.results?.count ?? 0) == self.pageSize
                self.presenter?.processCharacters(characterList, hasMore: hasMore)
            case let .failure(error):
                self.presenter?.requestFailed(error)
            }
        }
    }
    
    func refresh(searchFilter: String?) {
        self.offset = 0
        self.presenter?.refresh()
        self.loadNextPage(searchFilter: searchFilter)
    }
    
    func addFavorite(character: CharacterEntity) {
        if CoreDataManager.addFavorite(character) {
            self.presenter?.processAddFavorite(character)
        } else {
            self.presenter?.processAddFavoriteError(character)
        }
    }
    
    func removeFavorite(character: CharacterEntity) {
        if CoreDataManager.removeFavorite(character) {
            self.presenter?.processRemoveFavorite(character)
        } else {
            self.presenter?.processRemoveFavoriteError(character)
        }
    }
}
