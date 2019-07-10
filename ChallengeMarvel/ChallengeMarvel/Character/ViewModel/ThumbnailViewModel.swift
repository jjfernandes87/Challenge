//
//  ThumbnailViewModel.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 02/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation
import UIKit

class ThumbnailViewModel {

    private let thumbnail: Thumbnail
    let pathFull: String
    var image: UIImage?
    
    init(thumbnail: Thumbnail) {
        self.thumbnail = thumbnail
        self.pathFull = "\(thumbnail.path ?? "").\(thumbnail.thumbnailExtension ?? "")"
        self.image = thumbnail.image
    }
}
