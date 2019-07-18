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
}

extension CharacterDetailPresenter: CharacterDetailPresenterProtocol {
    func processComics(_ comicList: [ComicEntity]) {
     
    }
    
    func processSeries(_ serieList: [SerieEntity]) {
     
    }
    
    func processError(_ errorType: CharacterDetailPresenterErrorType) {
     
    }
}
