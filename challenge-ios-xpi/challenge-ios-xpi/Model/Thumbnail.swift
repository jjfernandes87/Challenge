//
//  Thumbnail.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 15/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import Foundation

class Thumbnail {
    
    var path : String?
    var extensionThumb : String?
    
    init(dictionary: [String: AnyObject]) {
        
        if let value = dictionary["path"] as? String {
            path = value
        }
        
        if let value = dictionary["extension"] as? String {
            extensionThumb = value
        } 
    }
}
