//
//  FavoriteObserver.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 18/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation

public protocol FavoriteObservable: class {
    func favoriteUpdated(characterID: Int)
}

public class FavoriteObserver {
    
    public static var shared = FavoriteObserver()
    
    public weak var characterList: FavoriteObservable?
    public weak var characterDetail: FavoriteObservable?
    public weak var favoriteList: FavoriteObservable?
    public weak var favoriteDetail: FavoriteObservable?
    
    public func notifyFavoriteChange(characterID: Int) {
        self.characterList?.favoriteUpdated(characterID: characterID)
        self.characterDetail?.favoriteUpdated(characterID: characterID)
        self.favoriteList?.favoriteUpdated(characterID: characterID)
        self.favoriteDetail?.favoriteUpdated(characterID: characterID)
    }
}
