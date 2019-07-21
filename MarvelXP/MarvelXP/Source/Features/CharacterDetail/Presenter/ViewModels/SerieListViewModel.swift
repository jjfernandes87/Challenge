//
//  SerieListViewModel.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 18/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation

public struct SerieListViewModel {
    
    var isLoading: Bool
    var serieList: [SerieViewModel]
    var hasError: Bool
    var isEmpty: Bool
    
    init() {
        self.init(nil, hasError: false)
    }
    
    init(_ entities: [SerieEntity]?, hasError: Bool) {
        self.isLoading = entities == nil
        self.serieList = []
        self.hasError = hasError
        self.isEmpty = entities?.isEmpty ?? false
        
        guard let serieEntityList = entities else { return }
        for serieEntity in serieEntityList {
            self.serieList.append(SerieViewModel(serieEntity))
        }
    }
}
