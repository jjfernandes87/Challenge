//
//  CharacterDetailInteractor.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

class CharacterDetailInteractor: DKInteractor {
    fileprivate var presenter: CharacterDetailPresenterProtocol? { return self.getAbstractPresenter() as? CharacterDetailPresenterProtocol }
}

extension CharacterDetailInteractor: CharacterDetailInteractorProtocol {

}
