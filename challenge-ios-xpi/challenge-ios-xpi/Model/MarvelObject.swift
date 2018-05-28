//
//  MarvelObject.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 15/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import Foundation

class MarvelObject {
    
    var code : String?
    var status : String?
    var data : DataObject?
    
    init(dictionary: [String: AnyObject]) {
        
        if let value = dictionary["data"] as? [String: AnyObject] {
            data = DataObject(dictionary: value)
        }
    }
}
