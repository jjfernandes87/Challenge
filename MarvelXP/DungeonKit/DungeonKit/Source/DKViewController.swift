//
//  DKViewController.swift
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

open class DKViewController<T: DKAbstractSceneFactory>: UIViewController, DKAbstractView {

    internal var interactor: DKAbstractInteractor?
    
    public var presenterArgs: Any? = nil
    public var interactorArgs: Any? = nil
    
    override open func viewDidLoad() {
        super.viewDidLoad()
        
        let factory: DKAbstractSceneFactory = T.init()
        let presenter = factory.generatePresenter(presenterArgs)
        let interactor = factory.generateInteractor(interactorArgs)
 
        interactor.setPresenter(presenter)
        presenter.setView(self)
        self.setInteractor(interactor)
    }
    
    public func setInteractor(_ interactor: DKAbstractInteractor) {
        self.interactor = interactor
    }
    
    public func getAbstractInteractor() -> DKAbstractInteractor? {
        precondition(!Thread.isMainThread, "You cannot access the interactor from the main thread.")
        return interactor
    }
    
    public func async(execute: @escaping () -> Void) {
        if Thread.isMainThread {
            DispatchQueue.global(qos: .userInitiated).async(execute: execute)
        } else {
            execute()
        }
    }
}
