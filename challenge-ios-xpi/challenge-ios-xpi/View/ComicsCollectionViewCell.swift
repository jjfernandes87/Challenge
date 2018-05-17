//
//  ComicsCollectionViewCell.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 16/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import UIKit

class ComicsCollectionViewCell: UICollectionViewCell {
    
    @IBOutlet weak var comicsImage: UIImageView!
    @IBOutlet weak var comicsName: UILabel!
    
    var object : Items? {
        didSet {
            if let data = object {
                
                if let name = data.name {
                    comicsName.text = name
                }
                
                if let imageUrl = data.resourceURI {
                    
                }
                
            }
        }
    }
}


