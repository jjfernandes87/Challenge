//
//  ComicListCell.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 19/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import UIKit

class ComicListCell: UITableViewCell {
    
    @IBOutlet weak private var collectionView: UICollectionView!
    @IBOutlet weak private var loading: UIActivityIndicatorView!
    @IBOutlet weak private var errorLabel: UILabel!
    
    private var comics: [ComicViewModel]?
    private var initialized = false
    
    public func setup(_ viewModel: ComicListViewModel) {
        
        self.collectionView.isHidden = viewModel.isLoading && !viewModel.hasError && !viewModel.isEmpty
        self.loading.isHidden = !viewModel.isLoading
        
        guard !viewModel.isLoading else { return }

        self.errorLabel.text = viewModel.hasError ? "Error fetching comic data, please try again." : ""
        self.errorLabel.text = viewModel.isEmpty ? "This character has no comics." : ""
        self.comics = viewModel.comicList
        self.setupCollectionView()
    }
    
    private func setupCollectionView() {
        if !initialized {
            self.initialized = true
            self.collectionView.register(UINib(nibName: "ComicCell", bundle: nil), forCellWithReuseIdentifier: "ComicCell")
            self.collectionView.delegate = self
            self.collectionView.dataSource = self
            
            let cellSize = CGSize(width: 162, height: 270)
            let layout = UICollectionViewFlowLayout()
            layout.scrollDirection = .horizontal
            layout.itemSize = cellSize
            layout.sectionInset = UIEdgeInsets(top: 1, left: 1, bottom: 1, right: 1)
            layout.minimumLineSpacing = 1.0
            layout.minimumInteritemSpacing = 1.0
            collectionView.setCollectionViewLayout(layout, animated: false)
        }
        
        self.collectionView.reloadData()
        self.collectionView.setContentOffset(.zero, animated: false)
    }
}

extension ComicListCell: UICollectionViewDelegate, UICollectionViewDataSource {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.comics?.count ?? 0
    }
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        if let cell: ComicCell = collectionView.dequeueReusableCell(withReuseIdentifier: "ComicCell", for: indexPath) as? ComicCell,
            let viewModel = self.comics?[indexPath.row] {
            cell.setup(viewModel)
            return cell
        }
        
        assertionFailure("Invalid cell type.")
        return UICollectionViewCell(frame: .zero)
    }
}
