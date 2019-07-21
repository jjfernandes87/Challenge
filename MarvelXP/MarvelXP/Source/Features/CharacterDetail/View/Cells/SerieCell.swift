//
//  SerieCell.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 19/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import UIKit

class SerieCell: UICollectionViewCell {
    
    @IBOutlet weak private var serieImage: UIImageView!
    @IBOutlet weak private var serieTitleLabel: UILabel!
    
    public func setup(_ viewModel: SerieViewModel) {
        self.serieImage.download(viewModel.photoULR, errorImage: "download_error")
        self.serieTitleLabel.text = viewModel.title
    }
}
