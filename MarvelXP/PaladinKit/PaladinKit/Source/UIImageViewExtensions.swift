//
//  UIImageViewExtensions.swift
//  PaladinKit
//
//  Created by Roger Sanoli on 09/07/19.
//  Copyright © 2019 Lollipepper. All rights reserved.
//

import Foundation

let imageCache = NSCache<NSString, UIImage>()

public extension UIImageView {
    
    func download(_ imageURL: String, errorImage: String? = nil, showLoading: Bool = false) {
        
        self.image = nil
        
        guard let url = URL(string: imageURL) else {
            DispatchQueue.main.async { self.image = UIImage(named: errorImage ?? "") }
            return
        }
        
        if let cachedImage = imageCache.object(forKey: imageURL as NSString)  {
            self.image = cachedImage
            return
        }
        
        let activityIndicator: UIActivityIndicatorView = UIActivityIndicatorView.init(style: .gray)
        if showLoading {
            addSubview(activityIndicator)
            activityIndicator.startAnimating()
            activityIndicator.center = self.center
        }
        
        URLSession.shared.dataTask(with: url, completionHandler: { (data, response, error) in
            if error != nil {
                DispatchQueue.main.async { self.image = UIImage(named: errorImage ?? "") }
                return
            }
            
            DispatchQueue.main.async {
                if let image = UIImage(data: data!) {
                    imageCache.setObject(image, forKey: imageURL as NSString)
                    self.image = image
                    if showLoading {
                        activityIndicator.removeFromSuperview()
                    }
                }
            }
            
        }).resume()
    }
}
