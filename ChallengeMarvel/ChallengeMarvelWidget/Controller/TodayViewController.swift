//
//  TodayViewController.swift
//  ChallengeMarvelWidget
//
//  Created by Dynara Rico Oliveira on 07/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import UIKit
import NotificationCenter

class TodayViewController: UIViewController, NCWidgetProviding {
        
    @IBOutlet weak var widgetCollectionView: UICollectionView!
    
    var characterViewModel = [CharacterViewModel]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.widgetCollectionView.delegate = self
        self.widgetCollectionView.dataSource = self
        extensionContext?.widgetLargestAvailableDisplayMode = .expanded
        fetchData()
    }
        
    func widgetPerformUpdate(completionHandler: (@escaping (NCUpdateResult) -> Void)) {
        completionHandler(NCUpdateResult.newData)
    }
    
    func widgetActiveDisplayModeDidChange(_ activeDisplayMode: NCWidgetDisplayMode, withMaximumSize maxSize: CGSize) {
        let expanded = activeDisplayMode == .expanded
        preferredContentSize = expanded ? CGSize(width: maxSize.width, height: 200) : maxSize
    }
    
    func fetchData() {
        
        let marvelObjectManager = MarvelObjectManager()
        
        marvelObjectManager.fetchMarvelObject(offset: 0, nameStartsWith: "", limit: 3) { (result) in
            switch result() {
            case .success(let marvelObject):
                let marvelObjectViewModel = MarvelObjectViewModel(context: nil, marvelObject: marvelObject)
                self.characterViewModel = marvelObjectViewModel.characterViewModel
                self.widgetCollectionView?.reloadData()
            case .failure:
                print("load data fail")
            }
        }

    }
    
}

extension TodayViewController: UICollectionViewDelegate, UICollectionViewDataSource {
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return characterViewModel.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let characterViewModel = self.characterViewModel[indexPath.row]
        
        if let cell = collectionView.dequeueReusableCell(withReuseIdentifier: WidgetCollectionViewCell.reuseIdentifier, for: indexPath) as? WidgetCollectionViewCell {
            cell.characterViewModel = characterViewModel
            return cell
        }
        
        return UICollectionViewCell()
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let characterViewModel = self.characterViewModel[indexPath.row]
        if let url = URL(string: "challengemarvel://\(characterViewModel.id)") {
            self.extensionContext?.open(url, completionHandler: nil)
        }
    }
}
