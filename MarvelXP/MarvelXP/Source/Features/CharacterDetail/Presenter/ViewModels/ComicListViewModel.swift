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
    
    init(_ entities: [ComicEntity]?) {
        isLoading = entities == nil
        comicList = []
        
        guard let comicEntityList = entities else { return }
        for comicEntity in comicEntityList {
            comicList.append(ComicViewModel(comicEntity))
        }
    }
}
