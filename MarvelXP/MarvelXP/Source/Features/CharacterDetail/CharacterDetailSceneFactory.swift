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
    
    func generateInteractor() -> DKAbstractInteractor {
        return CharacterDetailInteractor()
    }
    
    func generatePresenter() -> DKAbstractPresenter {
        return CharacterDetailPresenter()
    }
}
