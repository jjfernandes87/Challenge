//
//  CharacterList.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation

class CharacterList: Codable {
    let offset: Int
    let limit, total, count: Int
    let data: [Character]
    
    enum CodingKeys: String, CodingKey {
        case offset, limit, total, count
        case data = "results"
    }
}
