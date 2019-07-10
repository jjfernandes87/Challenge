//
//  UIView+Extension.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 06/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import UIKit

extension UIView {
    func round() {
        self.layer.cornerRadius = 10
        self.layer.masksToBounds = true
    }
    
    func shadow() {
        self.layer.cornerRadius = 10
        self.layer.shadowColor = UIColor.gray.cgColor
        self.layer.shadowOffset = CGSize(width: 3.0, height: 3.0)
        self.layer.shadowOpacity = 0.5
        self.layer.shadowRadius = 2
    }
}
