//
//  MarvelObjectBusiness.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation

typealias MarvelObjectCallback = (@escaping () -> Result<MarvelObject>) -> Void
typealias ComicsEventsObjectCallback = (@escaping () -> Result<ComicsEventsObject>) -> Void

class MarvelObjectBusiness {
    
    private var marvelObject: MarvelObject?
    
    public func fetchMarvelObject(offset: Int, nameStartsWith: String, limit: Int, completion: @escaping MarvelObjectCallback) {
        guard let url = URL(string: ApiProvider.marvelObjectUrl(offset: offset, nameStartsWith: nameStartsWith, limit: limit)) else {
            return
        }
        
        MarvelObjectApiProvider.fetchMarvelObject(url: url) { (result) in
            completion { result() }
        }
    }
    
    public func fetchComicsEventsObject(urlString: String, completion: @escaping ComicsEventsObjectCallback) {
        guard let url = URL(string: ApiProvider.comicsEventsUrl(url: urlString)) else {
            return
        }
        
        MarvelObjectApiProvider.fetchDetails(url: url) { (result) in
            completion { result() }
        }
    }
    
    public func fetchCharacterObject(id: Int, completion: @escaping MarvelObjectCallback) {
        guard let url = URL(string: ApiProvider.characterObjectUrl(id: id)) else {
            return
        }
        
        MarvelObjectApiProvider.fetchCharacterObject(url: url) { (result) in
            completion { result() }
        }
    }
    
}
