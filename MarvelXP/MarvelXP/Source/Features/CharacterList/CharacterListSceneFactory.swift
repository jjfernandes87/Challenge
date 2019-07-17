//
//  CharacterListSceneFactory.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

class CharacterListSceneFactory: DKAbstractSceneFactory {
    
    required init() {}
    
    func generateInteractor() -> DKAbstractInteractor {
        return CharacterListInteractor()
    }
    
    func generatePresenter() -> DKAbstractPresenter {
        return CharacterListPresenter()
    }
}
