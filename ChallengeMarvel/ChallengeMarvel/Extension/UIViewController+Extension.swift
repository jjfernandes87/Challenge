//
//  UIViewController+Extension.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 01/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import UIKit

let activityIndicator = UIActivityIndicatorView()
var messageContainer: UIView?

extension UIViewController {
    
    func startLoading() {
        activityIndicator.center = CGPoint(x: view.center.x, y: view.center.y)
        activityIndicator.color = UIColor.red
        view.addSubview(activityIndicator)
        view.bringSubviewToFront(activityIndicator)
        activityIndicator.startAnimating()
    }
    
    func stopLoading() {
        activityIndicator.stopAnimating()
        activityIndicator.removeFromSuperview()
    }
    
    func alertMessage(_ message: String, textColor: UIColor? = .red) {
        messageContainer = UIView(frame: CGRect(x: 0, y: 0, width: self.view.frame.width, height: self.view.frame.height))
        
        if let messageContainer = messageContainer {
            let messageLabel = UILabel()
            messageLabel.frame = CGRect(x: 0, y: 0, width: messageContainer.frame.width, height: messageContainer.frame.height)
            messageLabel.text = message
            messageLabel.center = CGPoint(x: messageContainer.center.x, y: messageContainer.center.y)
            messageLabel.textAlignment = .center
            messageLabel.textColor = textColor
            messageLabel.numberOfLines = 0
            messageLabel.font = UIFont.boldSystemFont(ofSize: 14)
        
            messageContainer.backgroundColor = UIColor.lightGray.withAlphaComponent(0.5)
            messageContainer.addSubview(messageLabel)
            self.view.addSubview(messageContainer)
        }
    }
    
    func clearMessage() {
        messageContainer?.removeFromSuperview()
    }
    
    func setupNavBar() {
        let frame = CGRect(x: 0, y: 0, width: 280, height: 30)
        let logoContainer = UIView(frame: frame)
        
        let logoImage = UIImageView(frame: frame)
        logoImage.contentMode = .scaleAspectFit
        
        let image = UIImage(named: "logoNavbar")
        logoImage.image = image
        
        logoContainer.addSubview(logoImage)
        
        self.navigationItem.titleView = logoContainer
        self.navigationController?.navigationBar.layer.shadowColor = UIColor.black.cgColor
        self.navigationController?.navigationBar.layer.shadowOffset = CGSize(width: 0.0, height: 2.0)
        self.navigationController?.navigationBar.layer.shadowRadius = 2.0
        self.navigationController?.navigationBar.layer.shadowOpacity = 1.0
        self.navigationController?.navigationBar.layer.masksToBounds = false
        self.navigationController?.navigationBar.tintColor = .white
        self.navigationController?.navigationBar.isTranslucent = false
        
    }
}
