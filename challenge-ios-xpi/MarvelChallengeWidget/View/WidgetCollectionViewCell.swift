//
//  WidgetCollectionViewCell.swift
//  MarvelChallengeWidget
//
//  Created by Felipe Mac on 18/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import UIKit

class WidgetCollectionViewCell: UICollectionViewCell {
    
    @IBOutlet weak var heroImage: UIImageView!
    
    var object : ObjectModel? {
        didSet {
            
            if let image = object?.imageHero {
                heroImage.image = UIImage(named: image)
            }            
        }
    }
}
