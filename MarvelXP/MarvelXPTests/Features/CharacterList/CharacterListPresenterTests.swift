//
//  CharacterListPresenterTests.swift
//  MarvelXPTests
//
//  Created by Roger Sanoli on 18/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import XCTest
@testable import MarvelXP
@testable import DungeonKit

class CharacterListPresenterTests: XCTestCase {
    
    private var presenter: CharacterListPresenter!
    
    private var fetchPromise: XCTestExpectation?
    private var emptyPromise: XCTestExpectation?
    private var addFavoritePromise: XCTestExpectation?
    private var removeFavoritePromise: XCTestExpectation?
    private var fetchErrorPromise: XCTestExpectation?
    private var internetErrorPromise: XCTestExpectation?
    private var addFavoriteErrorPromise: XCTestExpectation?
    private var removeFavoriteErrorPromise: XCTestExpectation?
    
    override func setUp() {
        continueAfterFailure = false
        presenter = CharacterListPresenter()
        presenter.setView(self)
    }
    
    override func tearDown() {
        presenter = nil
    }
    
    func testCharacterListViewModel() {
        self.fetchPromise = expectation(description: "Generates a consistent ViewModel list to the view")
        let thumbnailMock = ThumbnailEntity(path: "www.somepath.com/file", extension: "jpg")
        let characterMock = CharacterEntity(id: 6, name: "Storm", description: "Coolest X-men", thumbnail: thumbnailMock, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        self.presenter.processCharacters([characterMock], hasMore: false)
        self.wait(for: [self.fetchPromise!], timeout: 5)
    }
    
    func testEmptyState() {
        self.emptyPromise = expectation(description: "The view should alert an empty state")
        self.presenter.processCharacters([], hasMore: false)
        self.wait(for: [self.emptyPromise!], timeout: 5)
    }
    
    func testAddFavorite() {
        let characterMock = CharacterEntity(id: 6, name: "Storm", description: "Coolest X-men", thumbnail: nil, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        self.presenter.processCharacters([characterMock], hasMore: false)
        self.addFavoritePromise = expectation(description: "The view should update the favorite element")
        self.presenter.processAddFavorite(6)
        self.wait(for: [self.addFavoritePromise!], timeout: 5)
    }
    
    func testRemoveFavorite() {
        let characterMock = CharacterEntity(id: 6, name: "Storm", description: "Coolest X-men", thumbnail: nil, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        self.presenter.processCharacters([characterMock], hasMore: false)
        self.removeFavoritePromise = expectation(description: "The view should update the favorite element")
        self.presenter.processAddFavorite(6)
        self.wait(for: [self.removeFavoritePromise!], timeout: 5)
    }
    
    func testInternetError() {
        self.internetErrorPromise = expectation(description: "The view should alert an internet error")
        self.presenter.processError(.internetConnection)
        self.wait(for: [self.internetErrorPromise!], timeout: 5)
    }
    
    func testFetchError() {
        self.fetchErrorPromise = expectation(description: "The view should alert a fetch error")
        self.presenter.processError(.fetch)
        self.wait(for: [self.fetchErrorPromise!], timeout: 5)
    }
    
    func testAddFavoriteError() {
        self.addFavoriteErrorPromise = expectation(description: "The view should alert an add favorite error")
        self.presenter.processError(.addFavorite)
        self.wait(for: [self.addFavoriteErrorPromise!], timeout: 5)
    }
    
    func testRemoveFavoriteError() {
        self.removeFavoriteErrorPromise = expectation(description: "The view should alert an remove favorite error")
        self.presenter.processError(.removeFavorite)
        self.wait(for: [self.removeFavoriteErrorPromise!], timeout: 5)
    }
}

extension CharacterListPresenterTests: CharacterListViewControllerProtocol {
    
    func showEmptyState() {
        self.emptyPromise?.fulfill()
    }
    
    func updateCharacterList(_ viewModels: [CharacterViewModel], hasMore: Bool) {
        XCTAssertNotNil(viewModels)
        XCTAssertNotNil(hasMore)
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
        self.addFavoritePromise?.fulfill()
        self.removeFavoritePromise?.fulfill()
    }
    
    func showFetchError() {
        self.fetchErrorPromise?.fulfill()
    }
    
    func showInternetError() {
        self.internetErrorPromise?.fulfill()
    }
    
    func alertFavoriteError(adding: Bool) {
        self.addFavoriteErrorPromise?.fulfill()
        self.removeFavoriteErrorPromise?.fulfill()
    }
    
    func setInteractor(_ abstractInteractor: DKAbstractInteractor) {}
}
