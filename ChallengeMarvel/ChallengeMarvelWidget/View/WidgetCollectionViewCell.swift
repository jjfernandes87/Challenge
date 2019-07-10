//
//  WidgetCollectionViewCell.swift
//  ChallengeMarvelWidget
//
//  Created by Dynara Rico Oliveira on 07/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import UIKit

class WidgetCollectionViewCell: UICollectionViewCell, Identifiable {
    
    @IBOutlet weak var widgetImageView: UIImageView?
    
    var characterViewModel: CharacterViewModel? {
        didSet {
            if widgetImageView?.image != nil {
                widgetImageView?.image = characterViewModel?.image
            } else {
                widgetImageView?.loadImage(withURL: characterViewModel?.thumbnailViewModel?.pathFull ?? "")
            }
        }
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        widgetImageView?.image = UIImage()
    }
}
