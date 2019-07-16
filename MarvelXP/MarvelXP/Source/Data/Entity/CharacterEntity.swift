//
//  CharacterEntity.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 15/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import RogueKit

public struct CharacterEntity: Entity {
    var id: Int?
    var name: String?
    var description: String?
    var thumbnail: ThumbnailEntity?
}
