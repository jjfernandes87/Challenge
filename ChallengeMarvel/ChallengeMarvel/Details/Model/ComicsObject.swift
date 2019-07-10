//
//  ComicsObject.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 07/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation

struct ComicsEventsObject: Codable {
    let code: Int
    let status, copyright, attributionText, attributionHTML: String
    let etag: String
    let data: ComicsEventsObjectData
}

struct ComicsEventsObjectData: Codable {
    let offset, limit, total, count: Int
    let results: [ComicsEventsResult]
}

struct ComicsEventsResult: Codable {
    let thumbnail: Thumbnail
}
