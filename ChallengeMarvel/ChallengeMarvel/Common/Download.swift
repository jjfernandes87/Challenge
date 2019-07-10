//
//  Download.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 07/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation
import UIKit

class Download {
    static func downloadImage(withURL: String, completion: @escaping (UIImage?) -> Void) {
        guard let withURL = URL(string: withURL) else { return }
        
        getData(from: withURL) { data, response, error in
            guard let data = data else { return }
            completion(UIImage(data: data))
        }
    }
    
    static func getData(from url: URL, completion: @escaping (Data?, URLResponse?, Error?) -> ()) {
        URLSession.shared.dataTask(with: url, completionHandler: completion).resume()
    }
}
