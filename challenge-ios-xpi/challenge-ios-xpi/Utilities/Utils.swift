//
//  Utils.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 14/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import UIKit

class Utils: NSObject {

    static let imageCache = NSCache<AnyObject, AnyObject>()
    static let loadingView = UIView()
    static let spinner = UIActivityIndicatorView()
    
    static func formatNavigationTitleFontWithDefaultStyle(view: UIViewController, description: String) {
        view.navigationController?.navigationBar.setBackgroundImage(UIImage(), for: .default)
        view.navigationController?.navigationBar.shadowImage = UIImage()
        view.navigationController?.navigationBar.isTranslucent = true
        view.navigationController?.view.backgroundColor = .clear
        view.navigationController?.navigationBar.tintColor = UIColor.white
        view.navigationItem.title = description
        let titleDict: NSDictionary = [NSAttributedStringKey.foregroundColor: UIColor.white, NSAttributedStringKey.font: UIFont(name: "Pacifico-Regular", size: 20)!]
        view.navigationController?.navigationBar.titleTextAttributes = titleDict as? [NSAttributedStringKey : Any]
    }
    
    static func setLoadingScreen(view: UIView) {
        loadingView.isHidden = false
        
        loadingView.isHidden = false
        loadingView.frame = CGRect(x: 0, y: 0, width: view.frame.width, height: view.frame.height)
        loadingView.backgroundColor = UIColor.gray.withAlphaComponent(0.3)
        
        // Sets spinner
        spinner.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        spinner.frame = CGRect(x: 0, y: 0, width: view.frame.maxX, height: view.frame.maxY)
        spinner.startAnimating()
        
        view.addSubview(loadingView)
        loadingView.addSubview(self.spinner)
    }
    
    static func removeLoadingScreen(view: UIView) {
        self.spinner.stopAnimating()
        self.loadingView.isHidden = true
        self.loadingView.removeFromSuperview()
    }
}
