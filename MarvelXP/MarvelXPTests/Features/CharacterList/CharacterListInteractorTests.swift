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
    
    private let timeout = 60.0
    private var interactor: CharacterListInteractor!
    private var fetchPromise: XCTestExpectation?
    private var addPromise: XCTestExpectation?
    private var removePromise: XCTestExpectation?

    override func setUp() {
        CoreDataManager.removeAllFavorites()
        
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
    
    func testAddFavorite() {
        let mockCharacter = CharacterEntity(id: 3, name: "Iron Man", description: nil, thumbnail: nil, favoriteComics: nil, favoriteSeries: nil)
        self.addPromise = expectation(description: "Add a Character to favorites")
        interactor.addFavorite(character: mockCharacter)
        self.wait(for: [self.addPromise!], timeout: timeout)
    }
    
    func testRemoveFavorite() {
        let mockCharacter = CharacterEntity(id: 3, name: "Iron Man", description: nil, thumbnail: nil, favoriteComics: nil, favoriteSeries: nil)
        self.removePromise = expectation(description: "Remove a Character from favorites")
        interactor.addFavorite(character: mockCharacter)
        interactor.removeFavorite(character: mockCharacter)
        self.wait(for: [self.removePromise!], timeout: timeout)
    }
}

extension CharacterListInteractorTests: CharacterListPresenterProtocol {
    func processCharacters(_ characterList: [CharacterEntity], hasMore: Bool) {
        XCTAssertNotNil(characterList, "Should have a character list, even if is empty")
        fetchPromise?.fulfill()
    }
    
    func processAddFavorite(_ character: CharacterEntity) {
        addPromise?.fulfill()
    }
    
    func processRemoveFavorite(_ character: CharacterEntity) {
        removePromise?.fulfill()
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
