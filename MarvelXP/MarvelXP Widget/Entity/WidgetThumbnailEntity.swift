//
//  WidgetThumbnailEntity.swift
//  MarvelXP Widget
//
//  Created by Roger Sanoli on 21/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import RogueKit

public struct WidgetThumbnailEntity: Entity {
    var path: String?
    var `extension`: String?
    
    public func getURLPath() -> String {
        return "\(path ?? "").\(`extension` ?? "")"
    }
}
