//
//  CharactersCollectionViewController.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//


import UIKit

protocol CharacterSelectionDelegate: class {
    func characterSelected(_ character: CharacterViewModel)
}

class CharactersCollectionViewController: UICollectionViewController, Identifiable {
    enum TabBarItemSelected: String {
        case characters = "characters"
        case favorites = "favorites"
    }
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    var message: UILabel?
    var characterViewModel = [CharacterViewModel]()
    var marvelObjectManager: MarvelObjectCalls = MarvelObjectManager()
    var searchView: SearchCollectionReusableView?
    
    weak var characterSelectionDelegate: CharacterSelectionDelegate?
    
    var tabBarItemSelected: TabBarItemSelected {
        return TabBarItemSelected(rawValue: self.parent?.navigationController?.restorationIdentifier ?? "") ?? .characters
    }
    
    var offset: Int = 0
    
    var refreshControl: UIRefreshControl {
        let refreshControl = UIRefreshControl()
        refreshControl.addTarget(self, action: #selector(self.fetchData), for: .valueChanged)
        return refreshControl
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupNavBar()
        collectionView?.refreshControl = refreshControl
        collectionView.accessibilityIdentifier = "CharacterList"
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        clearMessage()
        fetchData()
        tabBarController?.tabBar.isHidden = false
    }
    
    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return characterViewModel.count
    }
    
    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let characterViewModel = self.characterViewModel[indexPath.row]
        
        let cell: CharacterCollectionViewCell = collectionView.dequeueReusableCell(for: indexPath)
        cell.characterViewModel = characterViewModel
        cell.delegate = self
        
        return cell
    }
    
    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        didSelectCharacter(at: indexPath)
    }
    
    override func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        if (scrollView.contentOffset.y + scrollView.frame.size.height) >= scrollView.contentSize.height &&
            tabBarItemSelected == .characters {
            offset += 20
            fetchData(offset: offset, presentLoadingView: false)
        }
    }
    
    override func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String,
                                 at indexPath: IndexPath) -> UICollectionReusableView {
        return searchCollectionReusableView(ofKind: kind, at: indexPath)
    }
    
    func searchCollectionReusableView(ofKind: String, at indexPath: IndexPath) -> UICollectionReusableView {
        if (ofKind == UICollectionView.elementKindSectionHeader) {
            let searchView = collectionView.dequeueReusableSupplementaryView(ofKind: ofKind, withReuseIdentifier: SearchCollectionReusableView.reuseIdentifier, for: indexPath) as? SearchCollectionReusableView
            
            self.searchView = searchView
            
            return searchView ?? UICollectionReusableView()
        }
        
        return UICollectionReusableView()
    }
    
    func didSelectCharacter(at indexPath: IndexPath) {
        let characterViewModel = self.characterViewModel[indexPath.row]
        characterSelectionDelegate?.characterSelected(characterViewModel)
        if let detailsViewController = characterSelectionDelegate as? DetailsViewController,
            let detailsNavigationController = detailsViewController.navigationController {
            
            let orientation = UIDevice.current.orientation.isValidInterfaceOrientation
                ? UIDevice.current.orientation.isPortrait
                : UIApplication.shared.statusBarOrientation.isPortrait
            
            if UIDevice.current.userInterfaceIdiom == .phone && orientation {
                tabBarController?.tabBar.isHidden = true
            }
            
            splitViewController?.showDetailViewController(detailsNavigationController, sender: nil)
        }
    }
}

extension CharactersCollectionViewController {
    fileprivate func setCharacterViewModelList(_ characterViewModel: [CharacterViewModel]) {
        self.characterViewModel = characterViewModel
        
        if let character = self.characterViewModel.first {
            self.characterSelectionDelegate?.characterSelected(character)
        }
        
        self.collectionView?.reloadData()
    }
    
    func clearList() {
        characterViewModel.removeAll()
        collectionView?.reloadData()
        self.stopLoading()
        collectionView?.refreshControl?.endRefreshing()
    }
    
    @objc
    func fetchData(offset: Int = 0, presentLoadingView: Bool = true, searchName: String = "") {
        let context = appDelegate.persistentContainer.viewContext
        
        if presentLoadingView { startLoading() }
        
        switch tabBarItemSelected {
        case .characters:
            if !Connectivity.isConnectedToInternet() {
                clearList()
                self.alertMessage("Verifique sua conexão com a internet", textColor: .blue)
            } else {
                marvelObjectManager.fetchMarvelObject(offset: offset, nameStartsWith: searchName, limit: 20) { (result) in
                    switch result() {
                    case .success(let marvelObject):
                        let marvelObjectViewModel = MarvelObjectViewModel(context: context, marvelObject: marvelObject)
                        self.setCharacterViewModelList(marvelObjectViewModel.characterViewModel)
                    case .failure:
                        self.clearList()
                        self.alertMessage("Não foi possível carregar seus dados")
                    }
                    if presentLoadingView { self.stopLoading() }
                    self.collectionView?.refreshControl?.endRefreshing()
                }
            }
        case .favorites:
            marvelObjectManager.fetchMarvelObjectDatabase(context: context, nameStartsWith: searchName) { (result) in
                switch result {
                case .success(let marvelObject):
                    if marvelObject.count == 0 {
                        self.clearList()
                        self.alertMessage("Lista de favoritos vazia", textColor: .black)
                    } else {
                        self.setCharacterViewModelList(marvelObject.compactMap({ CharacterViewModel(context: context,
                                                                                                    character: nil,
                                                                                                    managedObject: $0) }))
                    }
                case .failure:
                    self.clearList()
                    self.alertMessage("Não foi possível carregar seus dados")
                }
                if presentLoadingView { self.stopLoading() }
                self.collectionView?.refreshControl?.endRefreshing()
            }
        }
    }
}

extension CharactersCollectionViewController: CharacterCollectionViewCellDelegate {
    func reloadData() {
        if tabBarItemSelected == .favorites {
            characterViewModel = characterViewModel.filter({ (character) -> Bool in
                character.favorite == true
            })
        }
        self.collectionView?.reloadData()
    }
}

extension CharactersCollectionViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let width = ((self.view.frame.size.width) / 2) - 10
        return CGSize(width: width > 200 ? 200 : width, height: 225)
    }
}
