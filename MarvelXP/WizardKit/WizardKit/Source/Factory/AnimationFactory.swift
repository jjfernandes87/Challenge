//
//  AnimationFactory.swift
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

public class AnimationFactory {
    public enum TransitionAnimation {
        case alpha,
        hole(x: CGFloat, y: CGFloat, size: CGFloat),
        flip,
        right,
        rightAlpha,
        left,
        leftAlpha,
        top,
        topAlpha,
        bottom,
        bottomAlpha,
        none
    }
    static var animationManager: AnimationManager?
    static func transitionForViewController(_ viewController: UIViewController, anim: TransitionAnimation) -> UIViewControllerTransitioningDelegate? {
        
        var animation: UIViewControllerAnimationProtocol
        
        switch anim {
        case .alpha:
            animation = AlphaAnimation()
            
        case .flip:
            animation = FlipAnimation()
            
        case let .hole(x, y, size):
            animation = HoleAnimation(x: x, y: y, size: size)
            
        case .right:
            animation = TranslateRightAnimation(withAlpha: false)
            
        case .rightAlpha:
            animation = TranslateRightAnimation(withAlpha: true)
            
        case .left:
            animation = TranslateLeftAnimation(withAlpha: false)
            
        case .leftAlpha:
            animation = TranslateLeftAnimation(withAlpha: true)
            
        case .top:
            animation = TranslateTopAnimation(withAlpha: false)
            
        case .topAlpha:
            animation = TranslateTopAnimation(withAlpha: true)
            
        case .bottom:
            animation = TranslateBottomAnimation(withAlpha: false)
            
        case .bottomAlpha:
            animation = TranslateBottomAnimation(withAlpha: true)
            
        case .none:
            animation = NoneAnimation()
        }

        AnimationFactory.animationManager = AnimationManager(animation: animation)
        return AnimationFactory.animationManager
    }
}
