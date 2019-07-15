
//
//  TranslateRightAnimation.swift
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

class TranslateRightAnimation: UIViewControllerAnimationProtocol {

    private var hasAlpha: Bool
    
    init(withAlpha: Bool) {
        self.hasAlpha = withAlpha
    }
    
    func durationForPresenting() -> TimeInterval {
        return 0.3
    }
    
    func durationForDismissing() -> TimeInterval {
        return 0.3
    }
    
    func animatePresentationWithContext(context: UIViewControllerContextTransitioning) {
        let container: UIView = context.containerView
        let toVC: UIViewController = context.viewController(forKey: .to)!
        let fromVC: UIViewController = context.viewController(forKey: .from)!
        let bounds: CGRect = UIScreen.main.bounds
        let screenShot: UIView = fromVC.view.snapshotView(afterScreenUpdates: true)!
        
        container.addSubview(screenShot)
        container.addSubview(toVC.view)
        
        toVC.view.frame = CGRect(x:self.hasAlpha ? 100 : bounds.size.width, y: 0, width: bounds.size.width, height: bounds.size.height)
        
        UIView.animate(withDuration: self.durationForPresenting(), animations: {
            toVC.view.frame = bounds
        }) { (finished) in
            context.completeTransition(finished)
        }
        
        if self.hasAlpha {
            toVC.view.alpha = 0
            
            UIView.animate(withDuration: 0.2, delay: 0.1, options: .curveEaseInOut, animations: {
                toVC.view.alpha = 1
            })
        }
    }
    
    func animateDismissWithContext(context: UIViewControllerContextTransitioning) {
        let container: UIView = context.containerView
        let toVC: UIViewController = context.viewController(forKey: .to)!
        let fromVC: UIViewController = context.viewController(forKey: .from)!
        let bounds: CGRect = UIScreen.main.bounds
        
        toVC.view.frame = fromVC.view.frame
        
        container.addSubview(toVC.view)
        container.addSubview(fromVC.view)
        
        UIView.animate(withDuration: self.durationForPresenting(), animations: {
            fromVC.view.frame = CGRect(x: self.hasAlpha ? 100 : bounds.size.width, y: 0, width: bounds.size.width, height: bounds.size.height)
        }) { (finished) in
            fromVC.view.removeFromSuperview()
            context.completeTransition(finished)
        }
        
        if self.hasAlpha {
            fromVC.view.alpha = 1
            
            UIView.animate(withDuration: 0.2, delay: 0.1, options: .curveEaseInOut, animations: {
                fromVC.view.alpha = 0
            })
        }
    }
}
