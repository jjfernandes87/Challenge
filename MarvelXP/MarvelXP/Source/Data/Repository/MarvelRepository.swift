//
//  MarvelRepository.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 15/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import RogueKit
import PaladinKit

public enum MarvelRepository: RKRepository {
    
    case fetchCharacters(offset: Int, limit: Int, searchName: String?),
    fetchComics(characterID: Int),
    fetchSerieses(characterID: Int)
    
    public var domain: String { return "https://gateway.marvel.com:443" }
    
    public func createRequest() throws -> RKRequest {
        switch self {

        case let .fetchCharacters(offset, limit, searchName):
            let name = searchName == nil ? "" : "name=\(searchName ?? "")&"
            return RKRequest.get("/v1/public/characters?\(name)orderBy=name&limit=\(limit)&offset=\(offset)\(generateAuthentication())")
            
        case let .fetchComics(characterID):
            return RKRequest.get("/v1/public/comics?characters=\(characterID)\(generateAuthentication())")
            
        case let .fetchSerieses(characterID):
            return RKRequest.get("/v1/public/series?characters=\(characterID)\(generateAuthentication())")

        }
    }
    
    private func generateAuthentication() -> String {
        let timestamp = "1"
        let apiPrivateKey = "3524075b57d9b4a6d9a65e6f884e317813e7547e"
        let apiPublicKey = "3c00a8fb3f3bfcfe639a5b2027b37052"
        let md5 = "\(timestamp)\(apiPrivateKey)\(apiPublicKey)".MD5() ?? ""
        return "&ts=\(timestamp)&apikey=\(apiPublicKey)&hash=\(md5)"
    }
}
