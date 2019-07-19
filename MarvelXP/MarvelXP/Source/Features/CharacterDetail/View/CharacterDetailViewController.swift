//
//  CharacterDetailViewController.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

class CharacterDetailViewController: DKViewController<CharacterDetailSceneFactory> {
    
    fileprivate var interactor: CharacterDetailInteractorProtocol? { return self.getAbstractInteractor() as? CharacterDetailInteractorProtocol }
    
    /*
        Calling super.viewDidLoad() is mandatory in order to setup the VIP cycle.
    */

    override func viewDidLoad() {
        super.viewDidLoad()

        //Your code here...
    }
    
}

extension CharacterDetailViewController: CharacterDetailViewControllerProtocol {
    func showCharacter(_ viewModel: CharacterDetailViewModel) {
        
    }
    
    func showFetchError() {
        
    }
    
    func showComics(_ viewModels: [ComicViewModel]) {
        
    }
    
    func showEmptyComics() {
        
    }
    
    func showFetchComicsError() {
        
    }
    
    func showSeries(_ viewModels: [SerieViewModel]) {
        
    }
    
    func showEmptySeries() {
        
    }
    
    func showFetchSeriesError() {
        
    }
    
    func alertFavoriteError(adding: Bool) {
        
    }
    
    func toogleFavorite() {
        
    }
}
