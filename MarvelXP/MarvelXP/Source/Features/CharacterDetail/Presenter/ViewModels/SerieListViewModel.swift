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
    
    init(_ entities: [SerieEntity]?) {
        isLoading = entities == nil
        serieList = []
        
        guard let serieEntityList = entities else { return }
        for serieEntity in serieEntityList {
            serieList.append(SerieViewModel(serieEntity))
        }
    }
}
