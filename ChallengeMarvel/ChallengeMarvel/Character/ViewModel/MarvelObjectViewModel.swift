//
//  MarvelObjectViewModel.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 02/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation
import CoreData

class MarvelObjectViewModel {
    private let marvelObject: MarvelObject
    
    var context: NSManagedObjectContext?
    
    var characterViewModel = [CharacterViewModel]()
    
    init(context: NSManagedObjectContext?, marvelObject: MarvelObject) {
        self.context = context
        self.marvelObject = marvelObject
        
        if let characters = marvelObject.data?.data {
            characterViewModel.append(contentsOf: characters.map({return CharacterViewModel(context: context,
                                                                                            character: $0,
                                                                                            managedObject: nil)}))
        }
    }
    
}
