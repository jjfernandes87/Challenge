//
//  CharacterDetailPresenterTests.swift
//  MarvelXPTests
//
//  Created by Roger Sanoli on 18/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import XCTest
@testable import MarvelXP
@testable import DungeonKit

class CharacterDetailPresenterTests: XCTestCase {
    
    private var presenter: CharacterDetailPresenter!
    
    private var fetchPromise: XCTestExpectation?
    private var fetchComicsPromise: XCTestExpectation?
    private var fetchSeriesPromise: XCTestExpectation?
    private var emptyComicsPromise: XCTestExpectation?
    private var emptySeriesPromise: XCTestExpectation?
    private var addFavoritePromise: XCTestExpectation?
    private var removeFavoritePromise: XCTestExpectation?
    private var fetchErrorPromise: XCTestExpectation?
    private var fetchComicsErrorPromise: XCTestExpectation?
    private var fetchSeriesErrorPromise: XCTestExpectation?
    private var addFavoriteErrorPromise: XCTestExpectation?
    private var removeFavoriteErrorPromise: XCTestExpectation?

    override func setUp() {
        continueAfterFailure = false
        presenter = CharacterDetailPresenter(isFavoriteDetail: false)
        presenter.setView(self)
    }

    override func tearDown() {
        presenter = nil
    }

    func testCharacterViewModel() {
        self.fetchPromise = expectation(description: "Generates a consistent ViewModel")
        let thumbnailMock = ThumbnailEntity(path: "www.somepath.com/file", extension: "jpg")
        let characterMock = CharacterEntity(id: 6, name: "Storm", description: "Coolest X-men", thumbnail: thumbnailMock, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        self.presenter.processCharacter(characterMock)
        self.wait(for: [self.fetchPromise!], timeout: 5)
    }
    
    func testComicListViewModel() {
        self.fetchComicsPromise = expectation(description: "Generates a consistent ComicViewModel list to the view")
        let thumbnailMock = ThumbnailEntity(path: "www.somepath.com/file", extension: "jpg")
        let comicMock = ComicEntity(title: "Superb Adventure", thumbnail: thumbnailMock)
        self.presenter.processComics([comicMock])
        self.wait(for: [self.fetchComicsPromise!], timeout: 5)
    }
    
    func testSerieListViewModel() {
        self.fetchSeriesPromise = expectation(description: "Generates a consistent SerieViewModel list to the view")
        let thumbnailMock = ThumbnailEntity(path: "www.somepath.com/file", extension: "jpg")
        let serieMock = SerieEntity(title: "Superb Adventure", thumbnail: thumbnailMock)
        self.presenter.processSeries([serieMock])
        self.wait(for: [self.fetchSeriesPromise!], timeout: 5)
    }
    
    func testEmptyComics() {
        self.emptyComicsPromise = expectation(description: "The view should alert an empty comic list")
        self.presenter.processComics([])
        self.wait(for: [self.emptyComicsPromise!], timeout: 5)
    }
    
    func testEmptySeries() {
        self.emptySeriesPromise = expectation(description: "The view should alert an empty serie list")
        self.presenter.processSeries([])
        self.wait(for: [self.emptySeriesPromise!], timeout: 5)
    }
    
    func testAddFavorite() {
        self.addFavoritePromise = expectation(description: "The view should update the favorite element")
        let characterMock = CharacterEntity(id: 6, name: "Storm", description: "Coolest X-men", thumbnail: nil, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        self.presenter.processCharacter(characterMock)
        self.presenter.processAddFavorite(6)
        self.wait(for: [self.addFavoritePromise!], timeout: 5)
    }
    
    func testRemoveFavorite() {
        let characterMock = CharacterEntity(id: 6, name: "Storm", description: "Coolest X-men", thumbnail: nil, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        self.presenter.processCharacter(characterMock)
        self.removeFavoritePromise = expectation(description: "The view should update the favorite element")
        self.presenter.processRemoveFavorite(6)
        self.wait(for: [self.removeFavoritePromise!], timeout: 5)
    }
    
    func testFetchError() {
        self.fetchErrorPromise = expectation(description: "The view should alert a fetch error")
        self.presenter.processError(.fetchCharacter)
        self.wait(for: [self.fetchErrorPromise!], timeout: 5)
    }
    
    func testFetchComicError() {
        self.fetchComicsErrorPromise = expectation(description: "The view should alert an error fetching comics")
        self.presenter.processError(.fetchComics)
        self.wait(for: [self.fetchComicsErrorPromise!], timeout: 5)
    }
    
    func testFetchSerieError() {
        self.fetchSeriesErrorPromise = expectation(description: "The view should alert an error fetching series")
        self.presenter.processError(.fetchSeries)
        self.wait(for: [self.fetchSeriesErrorPromise!], timeout: 5)
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

extension CharacterDetailPresenterTests: CharacterDetailViewControllerProtocol {
    func showCharacter(_ viewModel: CharacterDetailViewModel) {
        XCTAssertNotNil(viewModel)
        XCTAssertNotNil(viewModel.id, "Should have a id, even if -1")
        XCTAssertNotNil(viewModel.name, "Should have a name, even if blank")
        XCTAssertNotNil(viewModel.photoULR, "Should have a photo, even if blank")
        XCTAssertNotNil(viewModel.isFavorite, "Should have favorite info")
        self.fetchPromise?.fulfill()
    }
    
    func showFetchError() {
        self.fetchErrorPromise?.fulfill()
    }
    
    func showComics(_ viewModels: [ComicViewModel]) {
        XCTAssertNotNil(viewModels)
        XCTAssertEqual(viewModels.count, 1)
        
        guard let viewModel = viewModels.first else {
            XCTFail("Invalid ViewModel")
            return
        }
        
        XCTAssertNotNil(viewModel.title, "Should have a title, even if blank")
        XCTAssertNotNil(viewModel.photoULR, "Should have a photo, even if blank")
        self.fetchComicsPromise?.fulfill()
    }
    
    func showEmptyComics() {
        self.emptyComicsPromise?.fulfill()
    }
    
    func showFetchComicsError() {
        self.fetchComicsErrorPromise?.fulfill()
    }
    
    func showSeries(_ viewModels: [SerieViewModel]) {
        XCTAssertNotNil(viewModels)
        XCTAssertEqual(viewModels.count, 1)
        
        guard let viewModel = viewModels.first else {
            XCTFail("Invalid ViewModel")
            return
        }
        
        XCTAssertNotNil(viewModel.title, "Should have a title, even if blank")
        XCTAssertNotNil(viewModel.photoULR, "Should have a photo, even if blank")
        self.fetchSeriesPromise?.fulfill()
    }
    
    func showEmptySeries() {
        self.emptySeriesPromise?.fulfill()
    }
    
    func showFetchSeriesError() {
        self.fetchSeriesErrorPromise?.fulfill()
    }
    
    func alertFavoriteError(adding: Bool) {
        self.addFavoriteErrorPromise?.fulfill()
        self.removeFavoriteErrorPromise?.fulfill()
    }
    
    func toogleFavorite() {
        self.addFavoritePromise?.fulfill()
        self.removeFavoritePromise?.fulfill()
    }
    
    func setInteractor(_ abstractInteractor: DKAbstractInteractor) {}
}
