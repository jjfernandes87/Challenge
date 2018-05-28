//
//  ApiServiceUrl.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 14/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import Foundation
import CryptoSwift

class ApiServiceURL{
    // - String path url base para api da marvel
    static let path = "https://gateway.marvel.com/v1/public"
    // - String pathCharacters endpoint
    static let pathCharacters = "/characters?"
    // = String pathCharacters endpoint + characterID
    static let pathCharacterPlusId = "/characters/"
    // - String privateKey api marvel
    static private let privateKey = "3de0888f7116e214e4702517b25a5a6d92972534"
    // - String publicKey api marvel
    static private let publicKey = "4cb73a5f44f93bfb931662413dc03e65"
    // - Int limit limite de requisições por página
    static var limit = 20
        
    /**
     Metodo que retorna uma query string de Dicionário construido para atender da api da marvel
     - Returns: -> String query
     -> string de Dicionário
     */
    static func getCredencial() -> String{
        let ts = Date().timeIntervalSince1970.description
        let hash = "\(ts)\(privateKey)\(publicKey)".md5()
        let authParams = ["ts": ts, "apikey": publicKey, "hash": hash]
        return authParams.queryString!
    }
}
extension Dictionary {
    ///Variável verifica e retorna Dictionary em formato de query string
    var queryString: String? {
        return self.reduce("") { "\($0!)\($1.0)=\($1.1)&" }
    }
}
