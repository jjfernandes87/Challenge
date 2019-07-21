//
//  FavoriteListViewController.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

private enum FavoriteListViewControllerState {
    case list,
    error,
    loading
}

class FavoriteListViewController: DKViewController<FavoriteListSceneFactory> {
    
    fileprivate var interactor: FavoriteListInteractorProtocol? { return self.getAbstractInteractor() as? FavoriteListInteractorProtocol }
    
    @IBOutlet weak private var collectionView: UICollectionView!
    @IBOutlet weak private var errorView: ErrorView!
    @IBOutlet weak private var loading: UIActivityIndicatorView!
    private let refreshControl = UIRefreshControl()
    
    private var favoriteViewModels: [FavoriteViewModel] = []
    private var isLoading: Bool = false

    private var state: FavoriteListViewControllerState? {
        didSet {
            guard let currentState = state else { return }
            self.collectionView.isHidden = !(currentState == .list)
            self.loading.isHidden = !(currentState == .loading)
            self.errorView.isHidden = !(currentState == .error)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.setupCollectionView()
        self.setupRefreshControl()
        self.setupErrorView()
        self.refresh()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        self.refresh()
    }
    
    private func setupCollectionView() {
        //self.collectionView.isAccessibilityElement = true
        self.collectionView.register(UINib(nibName: "FavoriteCell", bundle: nil), forCellWithReuseIdentifier: "FavoriteCell")
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
        
        let layout = CollectionViewLayout.layoutFor(orientation: UIApplication.shared.statusBarOrientation)
        collectionView.setCollectionViewLayout(layout, animated: false)
    }
    
    private func setupRefreshControl() {
        if #available(iOS 10.0, *) {
            self.collectionView.refreshControl = refreshControl
        } else {
            self.collectionView.addSubview(refreshControl)
        }
        
        self.refreshControl.tintColor = UIColor(hex: 0xDA3734)
        self.refreshControl.addTarget(self, action: #selector(refresh), for: .valueChanged)
    }
    
    private func setupErrorView() {
        self.errorView.onTryAgain = {
            if !self.isLoading {
                self.isLoading = true
                self.state = .loading
                self.async {
                    self.interactor?.fetchFavorites()
                }
            }
        }
    }
    
    @objc private func refresh() {
        
        if !self.isLoading {
            self.isLoading = true
            self.state = .loading
            
            self.favoriteViewModels = []
            self.collectionView.reloadData()
            
            async {
                self.interactor?.fetchFavorites()
            }
        }
    }
    
    private func getDetailViewControler() -> UINavigationController? {
        if (self.splitViewController?.viewControllers.count ?? 0) > 1 {
            return self.splitViewController?.viewControllers[1] as? UINavigationController
        } else {
            let storyboard = UIStoryboard(name: "MarvelXP", bundle: nil)
            return storyboard.instantiateViewController(withIdentifier: "FavoriteDetailNavigation") as? UINavigationController
        }
    }
    
    override func didRotate(from fromInterfaceOrientation: UIInterfaceOrientation) {
        collectionView.setCollectionViewLayout(CollectionViewLayout.layoutFor(orientation: UIApplication.shared.statusBarOrientation), animated: false)
    }
}

extension FavoriteListViewController: FavoriteListViewControllerProtocol {
    func showEmptyState() {
        self.isLoading = false
        self.errorView.error = .empty
        self.state = .error
    }
    
    func showFavoriteList(_ viewModels: [FavoriteViewModel]) {
        self.refreshControl.endRefreshing()
        self.state = .list
        self.isLoading = false
        self.favoriteViewModels = viewModels
        self.collectionView.reloadData()
    }
    
    func showFetchError() {
        self.isLoading = false
        self.errorView.error = .unknow
        self.state = .error
    }
    
    func alertFavoriteError() {
        self.alert("Error removing favorite. Please try again.")
    }
    
    func updateFavorites() {
        self.refresh()
    }
}

extension FavoriteListViewController: UICollectionViewDelegate, UICollectionViewDataSource {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.favoriteViewModels.count
    }
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        if let cell: FavoriteCell = collectionView.dequeueReusableCell(withReuseIdentifier: "FavoriteCell", for: indexPath) as? FavoriteCell {
            cell.setup(self.favoriteViewModels[indexPath.row])
            
            cell.onFavorite = { [unowned self] (characterID, _) in
                self.async {
                    self.interactor?.removeFavorite(characterID: characterID)
                }
            }
            
            return cell
        }
        
        assertionFailure("Invalid cell type.")
        return UICollectionViewCell(frame: .zero)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        
        guard let navigationVC = getDetailViewControler(),
            let detailViewController = navigationVC.viewControllers.first as? CharacterDetailViewController
            else { return }
        detailViewController.characterID = self.favoriteViewModels[indexPath.row].id
        self.splitViewController?.showDetailViewController(navigationVC, sender: nil)
    }
}
