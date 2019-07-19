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

class CharacterDetailInteractor: DKInteractor {
    fileprivate var presenter: CharacterDetailPresenterProtocol? { return self.getAbstractPresenter() as? CharacterDetailPresenterProtocol }
}

extension CharacterDetailInteractor: CharacterDetailInteractorProtocol {
    
    func fetchCharacter(characterID: Int) {
        
        CoreDataManager.fetchFavorite(characterID) { [unowned self] (optionalCharacter) in
            if let character = optionalCharacter {
                self.presenter?.processCharacter(character)
                
                if let comics = character.favoriteComics {
                    self.presenter?.processComics(comics)
                }
                
                if let series = character.favoriteSeries {
                    self.presenter?.processSeries(series)
                }
            } else {
                RogueKit.request(MarvelRepository.fetchCharacter(characterID: characterID)) { [unowned self] (result: ListResult<CharacterEntity>) in
                    switch result {
                    case let .success(characterList):
                        guard let character = characterList.data?.results?.first else {
                            self.presenter?.processError(.fetchCharacter)
                            return
                        }
                        
                        self.presenter?.processCharacter(character)
                    case .failure(_):
                        self.presenter?.processError(.fetchCharacter)
                    }
                }
            }
        }
    }
    
    func fetchComics(characterID: Int) {
        RogueKit.request(MarvelRepository.fetchComics(characterID: characterID)) { [unowned self] (result: ListResult<ComicEntity>) in
            switch result {
            case let .success(comicList):
                self.presenter?.processComics(comicList.data?.results ?? [])
            case .failure(_):
                self.presenter?.processError(.fetchComics)
            }
        }
    }
    
    func fetchSeries(characterID: Int) {
        RogueKit.request(MarvelRepository.fetchSeries(characterID: characterID)) { [unowned self] (result: ListResult<SerieEntity>) in
            switch result {
            case let .success(serieList):
                self.presenter?.processSeries(serieList.data?.results ?? [])
            case .failure(_):
                self.presenter?.processError(.fetchSeries)
            }
        }
    }
    
    func addFavorite(characterID: Int) {
        
        //TODO: Fetch comics and series before favoriting
        
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
    
    /*private func fetchCharacterByID(characterID: Int, completion: @escaping (CharacterEntity) -> Void) {
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
    }*/
    
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
