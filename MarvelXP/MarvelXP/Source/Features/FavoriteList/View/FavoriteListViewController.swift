//
//  FavoriteListViewController.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

class FavoriteListViewController: DKViewController<FavoriteListSceneFactory> {
    
    fileprivate var interactor: FavoriteListInteractorProtocol? { return self.getAbstractInteractor() as? FavoriteListInteractorProtocol }
    
    /*
        Calling super.viewDidLoad() is mandatory in order to setup the VIP cycle.
    */

    override func viewDidLoad() {
        super.viewDidLoad()

        //Your code here...
    }
    
}

extension FavoriteListViewController: FavoriteListViewControllerProtocol {

}
