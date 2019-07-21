//
//  BaseResponseEntity.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 15/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import RogueKit

public typealias ListResult<T: Entity> = Result<BaseResponseEntity<T>, Error>

public struct BaseResponseEntity<T: Entity>: Entity {
    public var data: ContainerEntity<T>?
}
