//
//  CharacterDetailInteractorTests.swift
//  MarvelXPTests
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import XCTest
@testable import MarvelXP
@testable import DungeonKit

class CharacterDetailInteractorTests: XCTestCase {

    private var interactor: CharacterDetailInteractor!
    
    private let timeout = 60.0
    private var fetchCharacterPromise: XCTestExpectation?
    private var fetchComicsPromise: XCTestExpectation?
    private var fetchSeriesPromise: XCTestExpectation?
    private var addFavoritePromise: XCTestExpectation?
    private var removeFavoritePromise: XCTestExpectation?
    
    private var removeAllPromise: XCTestExpectation?
    
    override func setUp() {
        continueAfterFailure = false
        removeAllPromise = expectation(description: "Remove all favorites")
        
        CoreDataManager.removeAllFavorites() {
            self.removeAllPromise?.fulfill()
        }
        
        wait(for: [removeAllPromise!], timeout: timeout)
        
        interactor = CharacterDetailInteractor()
        interactor.setPresenter(self)
    }

    override func tearDown() {
        interactor = nil
    }
    
    func testFetchCaptainAmerica() {
        self.fetchCharacterPromise = expectation(description: "Fetch Captain America data")
        interactor.fetchCharacter(characterID: 1009220)
        self.wait(for: [self.fetchCharacterPromise!], timeout: timeout)
    }

    func testFetchComics() {
        self.fetchComicsPromise = expectation(description: "Generates a list of ComicEntity")
        interactor.fetchComics(characterID: 1009220)
        self.wait(for: [self.fetchComicsPromise!], timeout: timeout)
    }

    func testFetchSeries() {
        self.fetchSeriesPromise = expectation(description: "Generates a list of SerieEntity")
        interactor.fetchSeries(characterID: 1009220)
        self.wait(for: [self.fetchSeriesPromise!], timeout: timeout)
    }
    
    func testAddIronManToFavorites() {
        self.addFavoritePromise = expectation(description: "Add Iron Man to favorites")
        interactor.addFavorite(characterID: 1009368)
        self.wait(for: [self.addFavoritePromise!], timeout: timeout)
    }
    
    func testRemoveFavorite() {
        self.removeFavoritePromise = expectation(description: "Remove a Character from favorites")
        let mockCharacter = CharacterEntity(id: 1009368, name: nil, description: nil, thumbnail: nil, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        CoreDataManager.addFavorite(mockCharacter) { _ in
            self.interactor.removeFavorite(characterID: 1009368)
        }
        self.wait(for: [self.removeFavoritePromise!], timeout: timeout)
    }
}

extension CharacterDetailInteractorTests: CharacterDetailPresenterProtocol {
    
    func processCharacter(_ character: CharacterEntity) {
        XCTAssertNotNil(character, "Captain exists!")
        XCTAssertEqual(character.name, "Captain America", "Data inconsistency.")
        fetchCharacterPromise?.fulfill()
    }
    
    func processComics(_ comicList: [ComicEntity]) {
        XCTAssertNotNil(comicList, "Should have a comic list, even if is empty")
        fetchComicsPromise?.fulfill()
    }
    
    func processSeries(_ serieList: [SerieEntity]) {
        XCTAssertNotNil(serieList, "Should have a serie list, even if is empty")
        fetchSeriesPromise?.fulfill()
    }
    
    func processAddFavorite(_ characterID: Int) {
        addFavoritePromise?.fulfill()
    }
    
    func processRemoveFavorite(_ characterID: Int) {
        removeFavoritePromise?.fulfill()
    }
    
    func processError(_ errorType: CharacterDetailPresenterErrorType) {
        switch errorType {
        case .fetchCharacter:
            XCTFail("Error fetching character.")
        case .fetchComics:
            XCTFail("Error fetching comics.")
        case .fetchSeries:
            XCTFail("Error fetching series.")
        case .addFavorite:
            XCTFail("Error adding favorite.")
        case .removeFavorite:
            XCTFail("Error removing favorite.")
        case .internetConnection:
            XCTFail("Internet error.")
        }
    }
    
    func setView(_ abstractView: DKAbstractView) {}
    func addFavoriteObserver(_ isFavoriteDetail: Bool) {}
}
