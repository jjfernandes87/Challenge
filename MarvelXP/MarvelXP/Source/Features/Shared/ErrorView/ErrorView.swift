//
//  ErrorView.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 19/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import UIKit

public enum ErrorViewType {
    case internet,
    empty,
    unknow
}

@IBDesignable public class ErrorView: UIView {
    
    @IBOutlet private weak var errorLabel: UILabel!
    @IBOutlet private weak var errorImage: UIImageView!
    @IBOutlet private weak var tryAgainButton: UIButton!
    
    public var onTryAgain: (() -> Void)?
    public var error: ErrorViewType? {
        didSet {
            guard let errorType = error else { return }
            
            switch errorType {
            case .internet:
                self.errorLabel.text = "Internet error, please check your connection."
                self.errorImage.image = UIImage(named: "error_internet")
            case .empty:
                self.errorLabel.text = "Could not find any character."
                self.errorImage.image = UIImage(named: "error_search")
            case .unknow:
                self.errorLabel.text = "Something went wrong, please try again later."
                self.errorImage.image = UIImage(named: "error_unknow")
            }
        }
    }
    
    required public init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        create()
    }
    
    override public init(frame: CGRect) {
        super.init(frame: frame)
        create()
    }
    
    private func create() {
        UIView.fromNib(String(describing: self.classForCoder), base: self).insert(to: self)
        self.tryAgainButton.setAttributedTitle("Try Again".underline(), for: .normal)
    }
    
    @IBAction func tryAgainPressed(_ sender: Any) {
        self.onTryAgain?()
    }
}
