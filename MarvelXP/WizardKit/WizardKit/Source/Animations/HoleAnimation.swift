//
//  HoleAnimation.swift
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

class HoleAnimation: NSObject, UIViewControllerAnimationProtocol, CAAnimationDelegate {
    
    private weak var context: UIViewControllerContextTransitioning?
    private var x: CGFloat
    private var y: CGFloat
    private var size : CGFloat
    private var initialRect: CGRect
    private var initialCircle: UIBezierPath
    private var finalCircle: UIBezierPath
    private let duration = 0.25
    
    init(x: CGFloat, y: CGFloat, size: CGFloat) {
        self.x = x
        self.y = y
        self.size = size
        self.initialRect = CGRect(x: self.x - self.size/2, y: self.y - self.size/2, width: self.size, height: self.size)
        self.initialCircle = UIBezierPath(ovalIn: initialRect)
        
        let bounds = CGRect.windowBounds()
        let fullHeight = bounds.height
        let extremePoint = CGPoint(x: self.x, y: self.y - fullHeight * 1.2)
        let radius = sqrt((extremePoint.x * extremePoint.x) + (extremePoint.y*extremePoint.y))
        self.finalCircle = UIBezierPath(ovalIn: initialRect.insetBy(dx: -radius, dy: -radius))
    }
    
    func durationForPresenting() -> TimeInterval {
        return duration
    }
    
    func durationForDismissing() -> TimeInterval {
        return duration
    }
    
    func animatePresentationWithContext(context: UIViewControllerContextTransitioning) {
        
        self.context = context
       
        let container: UIView = context.containerView
        let toVC: UIViewController = context.viewController(forKey: .to)!
        let fromVC: UIViewController = context.viewController(forKey: .from)!
        let screenShot: UIView = fromVC.view.snapshotView(afterScreenUpdates: true)!
        
        fromVC.view.removeFromSuperview()
        container.addSubview(screenShot)
        container.addSubview(toVC.view)
        
        let maskLayer = CAShapeLayer()
        maskLayer.path = finalCircle.cgPath
        toVC.view.layer.mask = maskLayer
        
        let animation = generateAnimation(fromPath: initialCircle.cgPath, toPath: finalCircle.cgPath)
        maskLayer.add(animation, forKey: "path")
    }
    
    func animateDismissWithContext(context: UIViewControllerContextTransitioning) {
        
        self.context = context
        
        let container: UIView = context.containerView
        let toVC: UIViewController = context.viewController(forKey: .to)!
        let fromVC: UIViewController = context.viewController(forKey: .from)!
        let screenShot: UIView = fromVC.view.snapshotView(afterScreenUpdates: true)!
        
        fromVC.view.removeFromSuperview()
        
        container.addSubview(toVC.view)
        container.addSubview(screenShot)
        
        let maskLayer = CAShapeLayer()
        let minimumCircle = UIBezierPath(ovalIn: CGRect(x: initialRect.minX, y: initialRect.minY, width: 1, height: 1))
        maskLayer.path = minimumCircle.cgPath
        screenShot.layer.mask = maskLayer
        
        let animation = generateAnimation(fromPath: finalCircle.cgPath, toPath: minimumCircle.cgPath)
        maskLayer.add(animation, forKey: "path")
    }

    func generateAnimation(fromPath: CGPath, toPath: CGPath) -> CABasicAnimation {
        let animation = CABasicAnimation(keyPath: "path")
        animation.fromValue = fromPath
        animation.toValue = toPath
        animation.duration = duration
        animation.delegate = self
        return animation
    }
    
    func animationDidStop(_ anim: CAAnimation, finished flag: Bool) {
        context?.completeTransition(true)
    }
}
