//
//  BaseManager.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation

class BaseManager: OperationQueue {
    
    /**
     Initialize an BaseManager subclass.
     - parameter maxConcurrentOperationCount: maximun number of concurrent operations.
     - returns: an instance of BaseManager subclass.
     */
    convenience init(maxConcurrentOperationCount: Int) {
        self.init()
        self.maxConcurrentOperationCount = maxConcurrentOperationCount
    }
    
    // MARK: Deinitalizers
    deinit {
        cancelAllOperations()
    }
}
