//
//  CGRectAnimationExtensions.swift
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

import UIKit

public extension CGRect {
    static func windowBounds(fullscreen:Bool?=false) -> CGRect {
        
        let screenbounds = UIScreen.main.bounds
        var safeAreaHeight : CGFloat = 0
        
        if #available(iOS 11.0, *) {
            safeAreaHeight = (UIApplication.shared.keyWindow?.safeAreaInsets.top)! + (UIApplication.shared.keyWindow?.safeAreaInsets.bottom)!
        }
        
        if safeAreaHeight == 0 {
            safeAreaHeight = 20
        }
        
        if fullscreen == true {
            safeAreaHeight = 0
        }
        
        return CGRect(x: screenbounds.origin.x, y: screenbounds.origin.y, width: screenbounds.size.width, height: screenbounds.size.height - safeAreaHeight)
    }
} 
