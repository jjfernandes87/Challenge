//
//  FavoriteListSceneFactory.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

class FavoriteListSceneFactory: DKAbstractSceneFactory {
    
    required init() {}
    
    func generateInteractor() -> DKAbstractInteractor {
        return FavoriteListInteractor()
    }
    
    func generatePresenter() -> DKAbstractPresenter {
        return FavoriteListPresenter()
    }
}
