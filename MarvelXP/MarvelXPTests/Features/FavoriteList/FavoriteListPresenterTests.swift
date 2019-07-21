//
//  FavoriteListPresenterTests.swift
//  MarvelXPTests
//
//  Created by Roger Sanoli on 18/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import XCTest
@testable import MarvelXP
@testable import DungeonKit

class FavoriteListPresenterTests: XCTestCase {
    
    private var presenter: FavoriteListPresenter!
    
    private let timeout = 30.0
    private var fetchPromise: XCTestExpectation?
    private var emptyPromise: XCTestExpectation?
    private var favoritePromise: XCTestExpectation?
    private var fetchErrorPromise: XCTestExpectation?
    private var removeFavoriteErrorPromise: XCTestExpectation?

    override func setUp() {
        continueAfterFailure = false
        presenter = FavoriteListPresenter()
        presenter.setView(self)
    }

    override func tearDown() {
        presenter = nil
    }

    func testFavoriteListViewModel() {
        self.fetchPromise = expectation(description: "Generates a consistent ViewModel list to the view")
        let thumbnailMock = ThumbnailEntity(path: "www.somepath.com/file", extension: "jpg")
        let characterMock = CharacterEntity(id: 7, name: "Gambit", description: "Also a cool X-men", thumbnail: thumbnailMock, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        self.presenter.processFavoriteList([characterMock])
        self.wait(for: [self.fetchPromise!], timeout: timeout)
    }
    
    func testEmptyState() {
        self.emptyPromise = expectation(description: "The view should alert an empty state")
        self.presenter.processFavoriteList([])
        self.wait(for: [self.emptyPromise!], timeout: timeout)
    }
    
    func testRemoveFavorite() {
        let characterMock = CharacterEntity(id: 7, name: "Gambit", description: "Also a cool X-men", thumbnail: nil, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        self.presenter.processFavoriteList([characterMock])
        self.favoritePromise = expectation(description: "The view should update the favorite element")
        self.presenter.processRemoveFavorite(7)
        self.wait(for: [self.favoritePromise!], timeout: timeout)
    }
    
    func testFetchError() {
        self.fetchErrorPromise = expectation(description: "The view should alert a fetch error")
        self.presenter.processError(.fetchFavorites)
        self.wait(for: [self.fetchErrorPromise!], timeout: timeout)
    }
    
    func testRemoveFavoriteError() {
        self.removeFavoriteErrorPromise = expectation(description: "The view should alert a remove favorite error")
        self.presenter.processError(.removeFavorite)
        self.wait(for: [self.removeFavoriteErrorPromise!], timeout: timeout)
    }
}

extension FavoriteListPresenterTests: FavoriteListViewControllerProtocol {
    func showEmptyState() {
        self.emptyPromise?.fulfill()
    }
    
    func showFavoriteList(_ viewModels: [FavoriteViewModel]) {
        XCTAssertNotNil(viewModels)
        XCTAssertEqual(viewModels.count, 1)
        
        guard let viewModel = viewModels.first else {
            XCTFail("Invalid ViewModel")
            return
        }
        
        XCTAssertNotNil(viewModel.id, "Should have a id, even if -1")
        XCTAssertNotNil(viewModel.name, "Should have a name, even if blank")
        XCTAssertNotNil(viewModel.photoULR, "Should have a photo, even if blank")
        XCTAssertNotNil(viewModel.isFavorite, "Should have favorite info")
        
        self.fetchPromise?.fulfill()
    }
    
    func showFetchError() {
        self.fetchErrorPromise?.fulfill()
    }
    
    func alertFavoriteError() {
        self.removeFavoriteErrorPromise?.fulfill()
    }
    
    func updateFavorites() {
        self.favoritePromise?.fulfill()
    }
    
    func setInteractor(_ abstractInteractor: DKAbstractInteractor) {}
}
