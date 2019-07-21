//
//  CharacterDetailViewController.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import DungeonKit

private enum CharacterDetailViewControllerState {
    case character,
    error,
    empty
}

class CharacterDetailViewController: DKViewController<CharacterDetailSceneFactory> {
    
    fileprivate var interactor: CharacterDetailInteractorProtocol? { return self.getAbstractInteractor() as? CharacterDetailInteractorProtocol }
    
    public var characterID: Int? {
        didSet {
            if !(self.state == nil) && !(self.state == .empty) {
                self.tableView.setContentOffset(.zero, animated: false)
            }
            self.refresh()
        }
    }
    
    @IBOutlet weak private var tableView: UITableView!
    @IBOutlet weak private var errorView: ErrorView!
    @IBOutlet weak private var favoriteButton: UIBarButtonItem!
    private let refreshControl = UIRefreshControl()
    
    private var characterViewModel: CharacterDetailViewModel = CharacterDetailViewModel()
    private var comicListViewModel: ComicListViewModel = ComicListViewModel()
    private var serieListViewModel: SerieListViewModel = SerieListViewModel()
    private var isLoading: Bool = true
    private var isFavorite: Bool = false
    
    private var state: CharacterDetailViewControllerState? {
        didSet {
            guard let currentState = state else { return }
            self.tableView.isHidden = !(currentState == .character)
            self.errorView.isHidden = !(currentState == .error)
        }
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        self.setupTableView()
        self.setupRefreshControl()
        self.setupErrorView()
        self.isLoading = false
        self.refresh()
    }
    
    private func setupTableView() {
        //self.tableView.isAccessibilityElement = true
        self.tableView.rowHeight = UITableView.automaticDimension
        
        self.tableView.register(UINib(nibName: "CharacterDetailCell", bundle: nil), forCellReuseIdentifier: "CharacterDetailCell")
        self.tableView.register(UINib(nibName: "ComicListCell", bundle: nil), forCellReuseIdentifier: "ComicListCell")
        self.tableView.register(UINib(nibName: "SerieListCell", bundle: nil), forCellReuseIdentifier: "SerieListCell")
        
        self.tableView.dataSource = self
        self.tableView.delegate = self
    }
    
    private func setupRefreshControl() {
        if #available(iOS 10.0, *) {
            self.tableView.refreshControl = refreshControl
        } else {
            self.tableView.addSubview(refreshControl)
        }
        
        self.refreshControl.tintColor = UIColor(hex: 0xDA3734)
        self.refreshControl.addTarget(self, action: #selector(refresh), for: .valueChanged)
    }
    
    private func setupErrorView() {
        self.errorView.onTryAgain = {
            self.state = .character
            self.refresh()
        }
        self.state = .empty
    }
    
    @objc private func refresh() {
        
        guard let characterID = self.characterID else { return }
        
        if !self.isLoading {
            self.state = .character
            self.isLoading = true
            
            self.characterViewModel = CharacterDetailViewModel()
            self.comicListViewModel = ComicListViewModel()
            self.serieListViewModel = SerieListViewModel()
            
            self.tableView.reloadData()
            
            async {
                self.interactor?.fetchCharacter(characterID: characterID)
                self.interactor?.fetchComics(characterID: characterID)
                self.interactor?.fetchSeries(characterID: characterID)
            }
        }
    }
    
    @IBAction func favoritePressed(_ sender: Any) {
        guard let characterID = self.characterID else { return }
        
        async {
            if !self.isFavorite {
                self.interactor?.addFavorite(characterID: characterID)
            } else {
                self.interactor?.removeFavorite(characterID: characterID)
            }
        }
    }
    
    private func setFavorite(_ isFavorite: Bool) {
        self.isFavorite = isFavorite
        self.favoriteButton.image = UIImage(named: self.isFavorite ? "favorite_selected" : "favorite_regular")
    }
}

extension CharacterDetailViewController: CharacterDetailViewControllerProtocol {
    func showCharacter(_ viewModel: CharacterDetailViewModel) {
        self.refreshControl.endRefreshing()
        self.isLoading = false
        self.navigationItem.title = viewModel.name
        self.setFavorite(viewModel.isFavorite)
        self.characterViewModel = viewModel
        self.tableView.reloadData()
    }
    
    func showComics(_ viewModel: ComicListViewModel) {
        self.comicListViewModel = viewModel
        self.tableView.reloadData()
    }
    
    func showSeries(_ viewModel: SerieListViewModel) {
        self.serieListViewModel = viewModel
        self.tableView.reloadData()
    }
    
    func alertFavoriteError(adding: Bool) {
        self.alert("Error \(adding ? "adding" : "removing") favorite. Please try again.")
    }
    
    func toogleFavorite() {
        self.setFavorite(!self.isFavorite)
    }
    
    func showInternetError() {
        self.errorView.error = .internet
        self.state = .error
    }
}

extension CharacterDetailViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 3
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.row == 0,
            let characterCell = tableView.dequeueReusableCell(withIdentifier: "CharacterDetailCell", for: indexPath) as? CharacterDetailCell {
            characterCell.setup(self.characterViewModel)
            return characterCell
        }
        
        if indexPath.row == 1,
            let comicListCell = tableView.dequeueReusableCell(withIdentifier: "ComicListCell", for: indexPath) as? ComicListCell {
            comicListCell.setup(self.comicListViewModel)
            return comicListCell
        }
        
        if indexPath.row == 2,
            let serieListCell = tableView.dequeueReusableCell(withIdentifier: "SerieListCell", for: indexPath) as? SerieListCell {
            serieListCell.setup(self.serieListViewModel)
            return serieListCell
        }
        
        assertionFailure("Invalid cell type.")
        return UITableViewCell(frame: .zero)
    }
}
