//
//  ObjectModel.swift
//  MarvelChallengeWidget
//
//  Created by Felipe Mac on 18/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import Foundation

class ObjectModel {
    
    var imageHero : String?
    
    init(dictionary: [String: AnyObject]) {
        
        if let value = dictionary["imageHero"] as? String? {
            imageHero = value
        }
    }
}
