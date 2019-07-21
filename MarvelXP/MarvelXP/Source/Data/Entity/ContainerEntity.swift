//
//  ContainerEntity.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 15/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import RogueKit

public struct ContainerEntity<T: Entity>: Entity {
    public var results: [T]?
    public var total: Int?
}
