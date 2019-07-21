//
//  FlipAnimation.swift
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

class FlipAnimation: UIViewControllerAnimationProtocol {
    
    func durationForPresenting() -> TimeInterval {
        return 0.5
    }
    
    func durationForDismissing() -> TimeInterval {
        return 0.5
    }
    
    func animatePresentationWithContext(context: UIViewControllerContextTransitioning) {
        let container: UIView = context.containerView
        let toVC: UIViewController = context.viewController(forKey: .to)!
        let fromVC: UIViewController = context.viewController(forKey: .from)!
        let screenShot: UIView = fromVC.view.snapshotView(afterScreenUpdates: true)!
        
        container.addSubview(screenShot)
        container.addSubview(toVC.view)
        
        UIView.transition(from: screenShot, to: toVC.view, duration: 0.3, options: .transitionFlipFromRight) { (finished) in
            context.completeTransition(finished)
        }
    }
    
    func animateDismissWithContext(context: UIViewControllerContextTransitioning) {
        let container: UIView = context.containerView
        let toVC: UIViewController = context.viewController(forKey: .to)!
        let fromVC: UIViewController = context.viewController(forKey: .from)!
        
        toVC.view.frame = fromVC.view.frame
        
        container.addSubview(toVC.view)
        container.addSubview(fromVC.view)
        
        UIView.transition(from: fromVC.view, to: toVC.view, duration: 0.3, options: .transitionFlipFromLeft) { (finished) in
            context.completeTransition(finished)
        }
    }
}
