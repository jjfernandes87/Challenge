//
//  WidgetRepository.swift
//  MarvelXP Widget
//
//  Created by Roger Sanoli on 21/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import RogueKit
import PaladinKit

public enum WidgetRepository: RKRepository {
    
    case fetchCharacters
    
    public var domain: String { return "https://gateway.marvel.com:443" }
    
    public func createRequest() throws -> RKRequest {
        switch self {
            
        case .fetchCharacters:
            return RKRequest.get("/v1/public/characters?orderBy=name&limit=3&offset=0&\(generateAuthentication())")
        }
    }
    
    private func generateAuthentication() -> String {
        let timestamp = "1"
        let apiPrivateKey = "3524075b57d9b4a6d9a65e6f884e317813e7547e"
        let apiPublicKey = "3c00a8fb3f3bfcfe639a5b2027b37052"
        let md5 = "\(timestamp)\(apiPrivateKey)\(apiPublicKey)".MD5() ?? ""
        return "ts=\(timestamp)&apikey=\(apiPublicKey)&hash=\(md5)"
    }
}
