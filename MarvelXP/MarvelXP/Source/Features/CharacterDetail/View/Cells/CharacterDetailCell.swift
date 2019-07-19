//
//  CharacterDetailCell.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 19/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import UIKit

class CharacterDetailCell: UITableViewCell {
    
    @IBOutlet weak private var characterImage: UIImageView!
    @IBOutlet weak private var characterDescriptionLabel: UILabel!
    
    public func setup(_ viewModel: CharacterDetailViewModel) {
        self.characterImage.download(viewModel.photoULR, errorImage: "download_error")
        self.characterDescriptionLabel.text = viewModel.description
    }
}
