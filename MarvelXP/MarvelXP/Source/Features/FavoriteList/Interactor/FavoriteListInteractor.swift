//
//  FavoriteListInteractor.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

class FavoriteListInteractor: DKInteractor {
    fileprivate var presenter: FavoriteListPresenterProtocol? { return self.getAbstractPresenter() as? FavoriteListPresenterProtocol }
}

extension FavoriteListInteractor: FavoriteListInteractorProtocol {

}
