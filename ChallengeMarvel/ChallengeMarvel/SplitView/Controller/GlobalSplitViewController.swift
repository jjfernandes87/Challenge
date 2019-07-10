//
//  GlobalSplitViewController.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import UIKit

class GlobalSplitViewController: UISplitViewController, UISplitViewControllerDelegate {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.delegate = self
        self.preferredDisplayMode = .allVisible
        
        guard let letfNavController = self.viewControllers.first as? UINavigationController,
            let charactersCollectionViewController = letfNavController.topViewController as? CharactersCollectionViewController,
            let rightNavController = self.viewControllers.last as? UINavigationController,
            let detailsViewController = rightNavController.topViewController as? DetailsViewController
            else { fatalError() }
        
        charactersCollectionViewController.characterSelectionDelegate = detailsViewController
        
        detailsViewController.navigationItem.leftItemsSupplementBackButton = true
        detailsViewController.navigationItem.leftBarButtonItem = self.displayModeButtonItem
    }
    
    func splitViewController(_ splitViewController: UISplitViewController, collapseSecondary secondaryViewController: UIViewController, onto primaryViewController: UIViewController) -> Bool {
        return true
    }
}
