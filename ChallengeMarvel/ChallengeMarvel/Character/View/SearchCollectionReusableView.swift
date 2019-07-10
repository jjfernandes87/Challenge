//
//  SearchCollectionReusableView.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 06/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import UIKit

class SearchCollectionReusableView: UICollectionReusableView, Identifiable {
    
    @IBOutlet weak var searchBar: UISearchBar?

}

extension CharactersCollectionViewController: UISearchBarDelegate {
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        if let text = searchBar.text {
            fetchData(offset: 0, presentLoadingView: true, searchName: text)
        }
    }
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        if searchText.isEmpty {
            fetchData()
        }
    }
}
