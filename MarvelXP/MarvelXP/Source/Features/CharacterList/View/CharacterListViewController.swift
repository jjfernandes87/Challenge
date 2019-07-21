//
//  CharacterListViewController.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

private enum CharacterListViewControllerState {
    case list,
    error,
    loading
}

class CharacterListViewController: DKViewController<CharacterListSceneFactory> {
    
    fileprivate var interactor: CharacterListInteractorProtocol? { return self.getAbstractInteractor() as? CharacterListInteractorProtocol }

    @IBOutlet weak private var searchBar: UISearchBar!
    @IBOutlet weak private var collectionView: UICollectionView!
    @IBOutlet weak private var errorView: ErrorView!
    @IBOutlet weak private var loading: UIActivityIndicatorView!
    private let refreshControl = UIRefreshControl()
    
    private var characterViewModels: [CharacterViewModel] = []
    private var hasMore: Bool = true
    private var isLoading: Bool = false
    private var searchTypingTimer: Timer?
    private var searchText: String? = nil
    
    private var state: CharacterListViewControllerState? {
        didSet {
            guard let currentState = state else { return }
            self.collectionView.isHidden = !(currentState == .list)
            self.loading.isHidden = !(currentState == .loading)
            self.errorView.isHidden = !(currentState == .error)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.setupNavigationController()
        self.setupSearchBar()
        self.setupCollectionView()
        self.setupRefreshControl()
        self.setupErrorView()
        self.refresh()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        self.collectionView.setCollectionViewLayout(CollectionViewLayout.layoutFor(orientation: UIApplication.shared.statusBarOrientation), animated: false)
    }
    
    override func didRotate(from fromInterfaceOrientation: UIInterfaceOrientation) {
        self.collectionView.setCollectionViewLayout(CollectionViewLayout.layoutFor(orientation: UIApplication.shared.statusBarOrientation), animated: false)
    }
    
    private func setupNavigationController() {
        let imageView = UIImageView(image:UIImage(named: "logo_navbar"))
        imageView.frame = CGRect(x: imageView.frame.origin.x, y: imageView.frame.origin.y, width: 145, height: 30)
        self.navigationItem.titleView = imageView
        
        self.navigationController?.navigationBar.setBackgroundImage(UIImage(), for: .default)
        self.navigationController?.navigationBar.shadowImage = UIImage()
    }
    
    private func setupSearchBar() {
        self.searchBar.backgroundImage = UIImage()
        self.searchBar.barTintColor = UIColor(hex: 0xDA3734)
        self.searchBar.delegate = self
    }
    
    private func setupCollectionView() {
        //self.collectionView.isAccessibilityElement = true
        self.collectionView.register(UINib(nibName: "CharacterCell", bundle: nil), forCellWithReuseIdentifier: "CharacterCell")
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
        self.collectionView.keyboardDismissMode = .onDrag

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
                self.state = .loading
                self.isLoading = true
                self.async {
                    self.interactor?.fetchNextPage(searchFilter: self.searchText)
                }
            }
        }
    }
    
    @objc private func refresh() {
        
        if !self.isLoading {
            
            self.isLoading = true
            self.hasMore = true
            self.state = .loading
            
            self.characterViewModels = []
            self.collectionView.reloadData()

            async {
                self.interactor?.refresh(searchFilter: self.searchText)
            }
        }
    }
    
    private func getDetailViewControler() -> UINavigationController? {
        if (self.splitViewController?.viewControllers.count ?? 0) > 1 {
            return self.splitViewController?.viewControllers[1] as? UINavigationController
        } else {
            let storyboard = UIStoryboard(name: "MarvelXP", bundle: nil)
            return storyboard.instantiateViewController(withIdentifier: "CharacterDetailNavigation") as? UINavigationController
        }
    }
}

extension CharacterListViewController: CharacterListViewControllerProtocol {
    
    func showEmptyState() {
        self.isLoading = false
        self.errorView.error = .empty
        self.state = .error
    }
    
    func updateCharacterList(_ viewModels: [CharacterViewModel], hasMore: Bool) {
        self.refreshControl.endRefreshing()
        self.state = .list
        self.isLoading = false
        self.hasMore = hasMore
        self.characterViewModels = viewModels
        self.collectionView.reloadData()
    }
    
    func showFetchError() {
        self.isLoading = false
        self.errorView.error = .unknow
        self.state = .error
    }
    
    func showInternetError() {
        self.isLoading = false
        self.errorView.error = .internet
        self.state = .error
    }
    
    func alertFavoriteError(adding: Bool) {
        self.alert("Error \(adding ? "adding" : "removing") favorite. Please try again.")
    }
}

extension CharacterListViewController: UISearchBarDelegate {
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        searchTypingTimer?.invalidate()
        searchTypingTimer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(searchTextDidChange(timer:)), userInfo: searchText, repeats: false)
    }
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        self.searchBar.resignFirstResponder()
    }
    
    @objc private func searchTextDidChange(timer: Timer) {
        
        var text = timer.userInfo as? String
        
        if text?.trim().isEmpty ?? true {
            text = nil
        }
        
        if text != searchText {
            searchText = text
            self.state = .loading
            refresh()
        }
    }
}

extension CharacterListViewController: UICollectionViewDelegate, UICollectionViewDataSource {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.characterViewModels.count
    }
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        if let cell: CharacterCell = collectionView.dequeueReusableCell(withReuseIdentifier: "CharacterCell", for: indexPath) as? CharacterCell {
            cell.setup(self.characterViewModels[indexPath.row])
            
            cell.onFavorite = { [unowned self] (characterID, isAdding) in
                self.async {
                    if isAdding {
                        self.interactor?.addFavorite(characterID: characterID)
                    } else {
                        self.interactor?.removeFavorite(characterID: characterID)
                    }
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
        detailViewController.characterID = self.characterViewModels[indexPath.row].id
        detailViewController.isFavoriteDetail = false
        self.splitViewController?.showDetailViewController(navigationVC, sender: nil)
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        
        guard !self.isLoading, self.hasMore else { return }
        
        let height = scrollView.frame.size.height * 2
        let contentoffsetY = scrollView.contentOffset.y
        let distanceFromBottom = scrollView.contentSize.height - contentoffsetY
        
        if distanceFromBottom < height {
            self.isLoading = true
            
            async {
                self.interactor?.fetchNextPage(searchFilter: self.searchText)
            }
        }
    }
}
