//
//  UIViewExtensions.swift
//
//  Copyright (c) 2018 Roger dos Santos Oliveira
//
//  Permission is hereby granted, free of charge, to any person obtaining a copy
//  of this software and associated documentation files (the "Software"), to deal
//  in the Software without restriction, including without limitation the rights
//  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//  copies of the Software, and to permit persons to whom the Software is
//  furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//  THE SOFTWARE.
//

import Foundation
import UIKit

@IBDesignable public extension UIView {
    
    @IBInspectable var cornerRadius: CGFloat {
        get { return self.layer.cornerRadius }
        set { self.layer.cornerRadius = (newValue < 1) ? (self.bounds.height * newValue) : (newValue) }
    }
    
    @IBInspectable var borderWidth: CGFloat {
        get { return self.layer.borderWidth }
        set { self.layer.borderWidth = newValue }
    }
    
    @IBInspectable var borderColor: UIColor? {
        get { return self.layer.borderColor != nil ? UIColor(cgColor: self.layer.borderColor!) : nil }
        set { self.layer.borderColor = newValue?.cgColor }
    }
    
    @IBInspectable var shadowColor: UIColor? {
        get { return self.layer.shadowColor != nil ? UIColor(cgColor: self.layer.shadowColor!) : nil }
        set { self.layer.shadowColor = newValue?.cgColor }
    }
    
    @IBInspectable var shadowRadius: CGFloat {
        get { return self.layer.shadowRadius }
        set { self.layer.shadowRadius = newValue }
    }
    
    @IBInspectable var shadowOpacity: Float {
        get { return self.layer.shadowOpacity }
        set { self.layer.shadowOpacity = newValue }
    }
    
    @IBInspectable var shadowOffset: CGSize {
        get { return self.layer.shadowOffset }
        set { self.layer.shadowOffset = newValue }
    }
    
    func removeSubviews() {
        for view in self.subviews {
            view.removeFromSuperview()
        }
    }
    
    class func fromNib<T: UIView>(_ name: String, base view: UIView) -> T {
        return UINib(nibName: name, bundle: Bundle(for: type(of: view))).instantiate(withOwner: view, options: nil)[0] as! T
    }
    
    func insert(to view: UIView, top: Int = 0, left: Int = 0, right: Int = 0, bottom: Int = 0) {
        view.insertSubview(self, at: 0)
        
        translatesAutoresizingMaskIntoConstraints = false
        
        topAnchor.constraint(equalTo: view.topAnchor, constant: CGFloat(top)).isActive = true
        leftAnchor.constraint(equalTo: view.leftAnchor, constant: CGFloat(left)).isActive = true
        rightAnchor.constraint(equalTo: view.rightAnchor, constant: CGFloat(right)).isActive = true
        bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: CGFloat(bottom)).isActive = true
    }

    func asImage() -> UIImage? {
        if #available(iOS 10.0, *) {
            let renderer = UIGraphicsImageRenderer(bounds: bounds)
            return renderer.image { rendererContext in
                layer.render(in: rendererContext.cgContext)
            }
        } else {
            return nil
        }
    }
}
