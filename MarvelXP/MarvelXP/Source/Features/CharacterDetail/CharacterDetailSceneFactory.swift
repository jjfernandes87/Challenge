//
//  CharacterDetailSceneFactory.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

class CharacterDetailSceneFactory: DKAbstractSceneFactory {
    
    required init() {}
    
    func generateInteractor(_ args: Any?) -> DKAbstractInteractor {
        return CharacterDetailInteractor()
    }
    
    func generatePresenter(_ args: Any?) -> DKAbstractPresenter {
        return CharacterDetailPresenter()
    }
}
