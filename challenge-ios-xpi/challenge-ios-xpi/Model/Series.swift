//
//  Series.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 15/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import Foundation

class Series {
    
    var collectionURI : String?
    var items : Items?
    
    init(dictionary: [String: AnyObject]) {
        
        if let value = dictionary["collectionURI"] as? String? {
            collectionURI = value
        }
        
        if let value = dictionary["items"] as? [String: AnyObject] {
            items = Items(dictionary: value)
        }
    }
}
