//
//  ComicCell.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 19/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import UIKit

class ComicCell: UICollectionViewCell {
    
    @IBOutlet weak private var comicImage: UIImageView!
    @IBOutlet weak private var comicTitleLabel: UILabel!
    
    public func setup(_ viewModel: ComicViewModel) {
        self.comicImage.download(viewModel.photoULR, errorImage: "download_error")
        self.comicTitleLabel.text = viewModel.title
    }
}
