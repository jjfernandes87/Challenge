//
//  CharacterListInteractorTests.swift
//  MarvelXPTests
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import XCTest
@testable import MarvelXP
@testable import DungeonKit

class CharacterListInteractorTests: XCTestCase {
    
    private var interactor: CharacterListInteractor!
    
    private let timeout = 60.0
    private var fetchPromise: XCTestExpectation?
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
        
        interactor = CharacterListInteractor()
        interactor.setPresenter(self)
    }

    override func tearDown() {
        interactor = nil
    }

    func testFetchCharacterList() {
        self.fetchPromise = expectation(description: "Generates a page of CharacterEntity")
        interactor.fetchNextPage(searchFilter: nil)
        self.wait(for: [self.fetchPromise!], timeout: timeout)
    }
    
    func testAddIronManToFavorites() {
        self.addFavoritePromise = expectation(description: "Add Iron Man to favorites")
        interactor.addFavorite(characterID: 1009368)
        self.wait(for: [self.addFavoritePromise!], timeout: timeout)
    }
    
    func testRemoveFavorite() {
        self.removeFavoritePromise = expectation(description: "Remove a Character from favorites")
        let mockCharacter = CharacterEntity(id: 1009368, name: nil, description: nil, thumbnail: nil, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        CoreDataManager.addFavorite(mockCharacter) { [weak self] _ in
            self?.interactor.removeFavorite(characterID: 1009368)
        }
        self.wait(for: [self.removeFavoritePromise!], timeout: timeout)
    }
}

extension CharacterListInteractorTests: CharacterListPresenterProtocol {
    func processAddFavorite(_ characterID: Int) {
        addFavoritePromise?.fulfill()
    }
    
    func processRemoveFavorite(_ characterID: Int) {
        removeFavoritePromise?.fulfill()
    }
    
    func processCharacters(_ characterList: [CharacterEntity], hasMore: Bool) {
        XCTAssertNotNil(characterList, "Should have a character list, even if is empty")
        fetchPromise?.fulfill()
    }
    
    func refresh() {}
    
    func processError(_ errorType: CharacterListPresenterErrorType) {
        switch errorType {
        case .fetch:
            XCTFail("Request Failed.")
        case .addFavorite:
            XCTFail("Add favorite Failed.")
        case .removeFavorite:
            XCTFail("Remove favorite Failed.")
        case .internetConnection:
            XCTFail("No internet connection")
        }
    }
    
    func setView(_ abstractView: DKAbstractView) {}
}
