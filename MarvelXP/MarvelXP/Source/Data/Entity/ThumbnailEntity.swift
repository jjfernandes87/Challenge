//
//  ThumbnailEntity.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 15/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import RogueKit

public struct ThumbnailEntity: Entity {
    var path: String?
    var `extension`: String?
    
    public func getURLPath() -> String {
        return "\(path ?? "").\(`extension` ?? "")"
    }
}
