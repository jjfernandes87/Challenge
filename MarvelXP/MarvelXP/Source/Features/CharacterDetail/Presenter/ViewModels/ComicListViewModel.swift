//
//  ComicListViewModel.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 18/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation

public struct ComicListViewModel {
    
    var isLoading: Bool
    var comicList: [ComicViewModel]
    var hasError: Bool
    var isEmpty: Bool
    
    init() {
        self.init(nil, hasError: false)
    }
    
    init(_ entities: [ComicEntity]?, hasError: Bool) {
        self.isLoading = entities == nil
        self.comicList = []
        self.hasError = hasError
        self.isEmpty = entities?.isEmpty ?? false
        
        guard let comicEntityList = entities else { return }
        for comicEntity in comicEntityList {
            self.comicList.append(ComicViewModel(comicEntity))
        }
    }
}
