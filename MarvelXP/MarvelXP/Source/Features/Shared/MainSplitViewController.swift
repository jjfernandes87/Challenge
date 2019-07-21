//
//  MainSplitViewController.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 19/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import UIKit

class MainSplitViewController: UISplitViewController, UISplitViewControllerDelegate {
    override var preferredStatusBarStyle: UIStatusBarStyle { return .lightContent }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.preferredDisplayMode = .allVisible
        self.preferredPrimaryColumnWidthFraction = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiom.pad ? 0.3 : 0.4
        self.delegate = self
    }
    
    func splitViewController(_ splitViewController: UISplitViewController, collapseSecondary secondaryViewController: UIViewController, onto primaryViewController: UIViewController) -> Bool {
        return true
    }
}
