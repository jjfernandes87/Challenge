//
//  NetworkService.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 17/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import Foundation

class NetworkService {
    
    lazy var configuration : URLSessionConfiguration = URLSessionConfiguration.default
    
    lazy var session : URLSession = URLSession(configuration: self.configuration)
    
    let url: NSURL
    
    init(url: NSURL) {
        self.url = url
    }
    
    func downloadImage(completion: @escaping ((NSData) -> Void)) {
        
        let request = NSURLRequest(url: self.url as URL)
        
        let dataTask = session.dataTask(with: request as URLRequest) {data,response,error in
            
            if error == nil {
                if let _ = response as? HTTPURLResponse {
                    if let data = data {
                        completion(data as NSData)
                    }
                }
            }else{
                
            }
        }
        dataTask.resume()
    }
}

extension NetworkService {
    
    static func parseJsonFromData(jsonData: NSData?) -> [String:AnyObject]? {
        
        if let data = jsonData {
            do {
                let jsonDictionary = try JSONSerialization.jsonObject(with: data as Data, options: .mutableContainers) as? [String:AnyObject]
                
                return jsonDictionary
            }catch let error as NSError {
             print(error)
            }
        }
        return nil
    }
}
