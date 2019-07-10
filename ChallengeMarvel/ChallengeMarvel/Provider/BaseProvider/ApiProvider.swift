//
//  ApiProvider.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation
import CryptoSwift

struct ApiProvider {
    static private let privateKey = "80f9f79f5cb7f63f835933d9ab20b4346b9e4829"
    static private let publicKey = "ec371b767bf553d7a6e1c19dfc528f2d"
    
    static func getCredencial(offset: Int, nameStartsWith: String, limit: Int) -> String {
        let timestamp = Date().timeIntervalSince1970.description
        let hash = "\(timestamp)\(privateKey)\(publicKey)".md5()
        
        var params = ["ts": timestamp,
                      "apikey": publicKey,
                      "hash": hash,
                      "orderBy": "name",
                      "limit": String(limit),
                      "offset": String(offset)]
        
        if nameStartsWith.count > 0 { params["nameStartsWith"] = nameStartsWith }
        
        return params.queryString ?? ""
    }
    
    static func getCredencial() -> String {
        let timestamp = Date().timeIntervalSince1970.description
        let hash = "\(timestamp)\(privateKey)\(publicKey)".md5()
        let authParams = ["ts": timestamp,
                          "apikey": publicKey,
                          "hash": hash]
        
        return authParams.queryString ?? ""
    }
    
    static func marvelObjectUrl(offset: Int, nameStartsWith: String, limit: Int) -> String {
        return "https://gateway.marvel.com/v1/public/characters?" + getCredencial(offset: offset, nameStartsWith: nameStartsWith, limit: limit)
    }
    
    static func comicsEventsUrl(url: String) -> String {
        return "\(url)?" + getCredencial()
    }
    
    static func characterObjectUrl(id: Int) -> String {
        return "https://gateway.marvel.com/v1/public/characters/\(id)?" + getCredencial()
    }
    
}
