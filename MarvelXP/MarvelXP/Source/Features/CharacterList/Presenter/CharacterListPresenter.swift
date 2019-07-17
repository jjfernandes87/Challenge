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
}

extension CharacterListPresenter: CharacterListPresenterProtocol {
    func requestFailed(_ error: Error) {
        
    }
    
    func processCharacters(_ characterList: BaseResponseEntity<CharacterEntity>, hasMore: Bool) {
        
    }
    
    func processAddFavorite(_ character: CharacterEntity) {
        
    }
    
    func processRemoveFavorite(_ character: CharacterEntity) {
        
    }
    
    func processAddFavoriteError(_ character: CharacterEntity) {
        
    }
    
    func processRemoveFavoriteError(_ character: CharacterEntity) {
        
    }
    
    func refresh() {
        
    }
    
    func connectionFailed() {
        
    }
}
