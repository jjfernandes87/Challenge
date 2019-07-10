//
//  DetailsViewController.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import UIKit

class DetailsViewController: UIViewController, Identifiable {

    @IBOutlet weak var detailsImageView: UIImageView?
    @IBOutlet weak var descriptionLabel: UILabel?
    @IBOutlet weak var comicsCollectionView: UICollectionView?
    @IBOutlet weak var seriesCollectionView: UICollectionView?
    
    var characterViewModel: CharacterViewModel? {
        didSet {
            refreshUI()
        }
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        startLoading()
        comicsCollectionView?.delegate = self
        comicsCollectionView?.dataSource = self
        seriesCollectionView?.delegate = self
        seriesCollectionView?.dataSource = self
    }
    
    private func refreshUI() {
        loadViewIfNeeded()
        
        descriptionLabel?.text = ""
        detailsImageView?.image = UIImage()
        
        self.title = characterViewModel?.name
        descriptionLabel?.text = characterViewModel?.description
        
        if characterViewModel?.image != nil {
            detailsImageView?.image = characterViewModel?.image
        } else {
            detailsImageView?.loadImage(withURL: characterViewModel?.thumbnailViewModel?.pathFull ?? "")
        }
        
        comicsCollectionView?.reloadData()
        seriesCollectionView?.reloadData()
        setNavigationItem()
        stopLoading()
    }
    
    private func setNavigationItem() {
        let favoriteButton = UIButton(type: .system)
        favoriteButton.frame = CGRect(x: 0, y: 0, width: 45, height: 45)
        favoriteButton.setImage(UIImage(named: "favorite"), for: .normal)
        favoriteButton.tintColor = characterViewModel?.favorite ?? false ? .white : .gray
        favoriteButton.addTarget(self, action: #selector(setFavorite), for: .touchUpInside)
        let buttonItem = UIBarButtonItem(customView: favoriteButton)
        self.navigationItem.rightBarButtonItem = buttonItem
    }
    
    @objc
    func setFavorite() {
        characterViewModel?.save {
            self.setNavigationItem()
        }
    }
}

extension DetailsViewController: CharacterSelectionDelegate {
    func characterSelected(_ character: CharacterViewModel) {
        characterViewModel = character
    }
}

extension DetailsViewController: UICollectionViewDelegate, UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if collectionView == self.comicsCollectionView {
            return characterViewModel?.comics?.count ?? 0
        }
        if collectionView == self.seriesCollectionView {
            return characterViewModel?.series?.count ?? 0
        }
        return 0
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell: DetailsCollectionViewCell = collectionView.dequeueReusableCell(for: indexPath)
        
        if collectionView == self.comicsCollectionView {
            cell.comic = self.characterViewModel?.comics?[indexPath.row]
        }
        
        if collectionView == self.seriesCollectionView {
            cell.comic = self.characterViewModel?.series?[indexPath.row]
        }
        
        return cell
    }

}
