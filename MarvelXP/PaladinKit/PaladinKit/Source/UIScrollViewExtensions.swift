//
//  UIScrollViewExtensions.swift
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

public extension UIScrollView {
    
    func registerForKeyboardNotifications() {
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillShow), name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillHide), name: UIResponder.keyboardWillHideNotification, object: nil)
        
        let tap : UITapGestureRecognizer = UITapGestureRecognizer.init(target: self, action: #selector(dismissKeyboard))
        self.addGestureRecognizer(tap)
    }
    
    func deregisterFromKeyboardNotifications() {
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillHideNotification, object: nil)
        
        dismissKeyboard()
    }
    
    @objc func keyboardWillShow(notification: NSNotification){
        
        guard let keyboardFrame = notification.userInfo![UIResponder.keyboardFrameEndUserInfoKey] as? NSValue else { return }
        
        var cInset:UIEdgeInsets = self.contentInset
        cInset.bottom = keyboardFrame.cgRectValue.height
        self.contentInset = cInset
        
        let firstResponder = self.getFirstResponderFromView(view: self)
        if let unwrapedfirstResponder = firstResponder {
            let elementPosition = (unwrapedfirstResponder.convert(unwrapedfirstResponder.frame.origin, to:self))
            let elementRect = CGRect(x:elementPosition.x, y:elementPosition.y+10, width:firstResponder!.frame.size.width, height:firstResponder!.frame.size.height)
            self.scrollRectToVisible(elementRect, animated: true)
        }
        self.layoutIfNeeded()
    }
    
    @objc func keyboardWillHide(notification: NSNotification){
        self.contentInset.bottom = 0
        self.layoutIfNeeded()
    }
    
    @objc func dismissKeyboard() {
        self.endEditing(true)
    }
    
    func getFirstResponderFromView(view:UIView) -> UIView? {
        for childView in view.subviews {
            if childView.responds(to: #selector(getter: isFirstResponder)) && childView.isFirstResponder {
                return childView
            }
            
            let subview = self.getFirstResponderFromView(view: childView)
            if subview != nil {
                return subview
            }
        }
        
        return nil
    }
    
    var currentPage: Int {
        return Int((self.contentOffset.x + (0.5 * self.frame.size.width)) / self.frame.width)
    }
}
