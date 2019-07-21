//
//  ComicViewModel.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 18/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation

public struct ComicViewModel {
    
    var title: String
    var photoULR: String
    
    init(_ entity: ComicEntity) {
        self.title = entity.title ?? ""
        self.photoULR = entity.thumbnail?.getURLPath() ?? ""
    }
}
