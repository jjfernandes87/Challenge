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
                
                comicsImage.layer.cornerRadius = 10
                comicsImage.image = #imageLiteral(resourceName: "comicsAndSeries")
                
                if let name = data.name {
                    comicsName.text = name
                }
            }
        }
    }
}


