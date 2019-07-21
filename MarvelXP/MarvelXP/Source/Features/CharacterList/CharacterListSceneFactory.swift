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
    
    func generateInteractor(_ args: Any?) -> DKAbstractInteractor {
        return CharacterListInteractor()
    }
    
    func generatePresenter(_ args: Any?) -> DKAbstractPresenter {
        return CharacterListPresenter()
    }
}
