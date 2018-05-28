//
//  DataObject.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 15/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import Foundation

class DataObject {
    
    var id : Int?
    var name : String?
    var description : String?
    var resourceURI : String?
    var thumbnail : Thumbnail?
    var comics = [Comics]()
    var series = [Series]()
    
    init(dictionary: [String: AnyObject]) {
        
        if let value = dictionary["id"] as? Int? {
            id = value
        }
        
        if let value = dictionary["name"] as? String? {
            name = value
        }
        
        if let value = dictionary["description"] as? String? {
            description = value
        }
        
        if let value = dictionary["resourceURI"] as? String? {
            resourceURI = value
        }
        
        if let value = dictionary["thumbnail"] as? [String:AnyObject] {
            thumbnail = Thumbnail(dictionary: value)
        }
        
        if let item = dictionary["comics"] as? [[String:AnyObject]] {
            
            for value in item {
                
                comics.append(Comics(dictionary: value))
            }
        }
        
        if let item = dictionary["series"] as? [[String:AnyObject]] {
            
            for value in item {
                
                series.append(Series(dictionary: value))
            }
        }
    }
}

