//
//  SeriesCollectionViewCell.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 16/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import UIKit

class SeriesCollectionViewCell: UICollectionViewCell {
    
    @IBOutlet weak var seriesImage: UIImageView!
    @IBOutlet weak var seriesName: UILabel!
    
    var object : Items? {
        didSet {
            if let data = object {
                
                seriesImage.layer.cornerRadius = 10
                seriesImage.image = #imageLiteral(resourceName: "comicsAndSeries")
                
                if let name = data.name {
                    seriesName.text = name
                }
            }
        }
    }
}
