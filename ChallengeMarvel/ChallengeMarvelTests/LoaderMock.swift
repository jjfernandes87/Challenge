//
//  LoaderMock.swift
//  ChallengeMarvelTests
//
//  Created by Dynara Rico Oliveira on 08/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation

@testable import ChallengeMarvel

class LoaderMock {
    let data: Data
    
    init?(file: String, withExtension fileExtension: String = "json", in bundle: Bundle = Bundle.main) {
        guard let path = bundle.path(forResource: file, ofType: fileExtension) else {
            return nil
        }
        
        let pathURL = URL(fileURLWithPath: path)
        do {
            data = try Data(contentsOf: pathURL, options: .dataReadingMapped)
        } catch{
            return nil
        }
    }
}

extension LoaderMock {
    func map<T: Codable>(to type: T.Type) -> T? {
        return try? JSONDecoder().decode(T.self, from: data)
    }
}
