//
//  CharacterListViewController.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

class CharacterListViewController: DKViewController<CharacterListSceneFactory> {
    
    fileprivate var interactor: CharacterListInteractorProtocol? { return self.getAbstractInteractor() as? CharacterListInteractorProtocol }
    
    /*
        Calling super.viewDidLoad() is mandatory in order to setup the VIP cycle.
    */

    override func viewDidLoad() {
        super.viewDidLoad()

        //Your code here...
    }
    
}

extension CharacterListViewController: CharacterListViewControllerProtocol {
    
    func showEmptyState() {
        
    }
    
    func updateCharacterList(_ viewModels: [CharacterViewModel], hasMore: Bool) {
        
    }
    
    func showFetchError() {
        
    }
    
    func showInternetError() {
        
    }
    
    func alertFavoriteError(adding: Bool) {
        
    }
}
