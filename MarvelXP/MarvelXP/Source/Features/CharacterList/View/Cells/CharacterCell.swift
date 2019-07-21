//
//  CharacterCell.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 19/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import UIKit

class CharacterCell: UICollectionViewCell {
    
    @IBOutlet weak private var characterImage: UIImageView!
    @IBOutlet weak private var characterNameLabel: UILabel!
    @IBOutlet weak private var favoriteButton: UIButton!
    
    private(set) var characterID: Int = 0
    public var onFavorite: ((Int, Bool) -> Void)?
    
    public func setup(_ viewModel: CharacterViewModel) {
        self.characterID = viewModel.id
        self.characterImage.download(viewModel.photoULR, errorImage: "download_error")
        self.characterNameLabel.text = viewModel.name
        self.favoriteButton.isSelected = viewModel.isFavorite
    }
    
    @IBAction func favoriteTouched(_ sender: Any) {
        self.onFavorite?(self.characterID, !self.favoriteButton.isSelected)
    }
}
