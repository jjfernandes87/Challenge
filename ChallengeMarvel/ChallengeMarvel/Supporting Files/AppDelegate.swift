//
//  AppDelegate.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import UIKit
import CoreData

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        if let url = launchOptions?[UIApplication.LaunchOptionsKey.url] as? URL, url.scheme == "challengemarvel" {
            
            guard let host = url.host else { return true }
            
            let id = Int(host) ?? 0
            
            let marvelObjectManager = MarvelObjectManager()
            marvelObjectManager.fetchCharacterObject(id: id) { (result) in
                switch result() {
                case .success(let marvelObject):
                    let marvelObjectViewModel = MarvelObjectViewModel(context: self.persistentContainer.viewContext,
                                                                      marvelObject: marvelObject)
                    
                    if let controller = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: DetailsViewController.storyboardIdentifier) as? DetailsViewController {
                        controller.characterViewModel = marvelObjectViewModel.characterViewModel.first
                        if let window = self.window, let rootViewController = window.rootViewController {
                            var currentController = rootViewController
                            while let presentedController = currentController.presentedViewController {
                                currentController = presentedController
                            }
                            currentController.present(controller, animated: true, completion: nil)
                        }
                    }
                    
                default:
                    break
                }
            }
        }
        
        return true
    }
    
    func applicationWillTerminate(_ application: UIApplication) {
        self.saveContext()
    }
    
    lazy var persistentContainer: NSPersistentContainer = {
        let container = NSPersistentContainer(name: "ChallengeMarvel")
        container.loadPersistentStores(completionHandler: { (storeDescription, error) in
            if let error = error as NSError? {
                fatalError("Unresolved error \(error), \(error.userInfo)")
            }
        })
        return container
    }()
    
    func saveContext () {
        let context = persistentContainer.viewContext
        if context.hasChanges {
            do {
                try context.save()
            } catch {
                let nserror = error as NSError
                fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
            }
        }
    }
    
}

