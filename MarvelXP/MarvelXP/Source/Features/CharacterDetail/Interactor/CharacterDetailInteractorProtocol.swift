//
//  CharacterDetailInteractorProtocol.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

protocol CharacterDetailInteractorProtocol: DKAbstractInteractor {
    func fetchComics(characterID: Int)
    func fetchSeries(characterID: Int)
}
