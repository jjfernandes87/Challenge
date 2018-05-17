//
//  Items.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 15/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import Foundation

class Items {
    
    var resourceURI : String?
    var name : String?
    
    init(dictionary: [String: AnyObject]) {
        
        if let value = dictionary["resourceURI"] as? String? {
            resourceURI = value
        }
        
        if let value = dictionary["name"] as? String? {
            name = value
        }
    }
}
