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
}
