//
//  MarvelObjectApiProvider.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation
import Alamofire

enum Result<Value> {
    case success(Value)
    case failure
}

class MarvelObjectApiProvider {
    static func fetchMarvelObject(url: URL, completion: @escaping MarvelObjectCallback) {
        Alamofire.request(url).validate().responseData { (response) in
            guard let data = response.data else {
                completion { .failure }
                return
            }
            
            do {
                let marvelObject = try JSONDecoder().decode(MarvelObject.self, from: data)
                completion { .success(marvelObject) }
            } catch {
                completion { .failure }
            }
        }
    }
    
    static func fetchDetails(url: URL, completion: @escaping ComicsEventsObjectCallback) {
        Alamofire.request(url).validate().responseData { (response) in
            guard let data = response.data else {
                completion { .failure }
                return
            }
            
            do {
                let comicsEventsObject = try JSONDecoder().decode(ComicsEventsObject.self, from: data)
                completion { .success(comicsEventsObject) }
            } catch {
                completion { .failure }
            }
        }
    }
    
    static func fetchCharacterObject(url: URL, completion: @escaping MarvelObjectCallback) {
        Alamofire.request(url).validate().responseData { (response) in
            guard let data = response.data else {
                completion { .failure }
                return
            }
            
            do {
                let marvelObject = try JSONDecoder().decode(MarvelObject.self, from: data)
                completion { .success(marvelObject) }
            } catch {
                completion { .failure }
            }
        }
    }
}
