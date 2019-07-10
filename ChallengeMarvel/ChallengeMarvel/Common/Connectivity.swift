//
//  Connectivity.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 07/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation
import Alamofire

class Connectivity {
    class func isConnectedToInternet() -> Bool {
        return NetworkReachabilityManager()!.isReachable
    }
}
