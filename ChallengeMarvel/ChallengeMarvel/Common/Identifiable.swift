//
//  Identifiable.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation
import UIKit

protocol Identifiable { }

// MARK: - Identifiable Extension on UIViewController
extension Identifiable where Self: UIViewController {
    static var storyboardIdentifier: String {
        return String(describing: self)
    }
    
    static var segueIdentifier: String {
        let identifier = String(describing: self)
        let vcString = "ViewController"
        let endIndex = identifier.index(identifier.endIndex, offsetBy: -vcString.count)
        return String(identifier[identifier.startIndex..<endIndex])
    }
}

// MARK: - Identifiable Extension on UITableViewCell
extension Identifiable where Self: UITableViewCell {
    static var reuseIdentifier: String {
        return String(describing: self)
    }
}

// MARK: - Identifiable Extension on UICollectionViewCell
extension Identifiable where Self: UICollectionViewCell {
    static var reuseIdentifier: String {
        return String(describing: self)
    }
}

// MARK: - Identifiable Extension on UICollectionReusableView
extension Identifiable where Self: UICollectionReusableView {
    static var reuseIdentifier: String {
        return String(describing: self)
    }
}
