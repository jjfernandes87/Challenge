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
    private var loadPromise: XCTestExpectation?
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

    func testLoadCharacterList() {
        self.loadPromise = expectation(description: "Generates a page of CharacterEntity")
        interactor.loadNextPage(searchFilter: nil)
        self.wait(for: [self.loadPromise!], timeout: timeout)
    }
    
    func testAddFavorite() {
        let mockCharacter = CharacterEntity(id: 3, name: "Iron Man", description: nil, thumbnail: nil, favoriteComics: nil, favoriteSeries: nil)
        self.removePromise = expectation(description: "Remove a Character from favorites")
        interactor.addFavorite(character: mockCharacter)
        interactor.removeFavorite(character: mockCharacter)
        self.wait(for: [self.removePromise!], timeout: timeout)
    }
    
    func testRemoveFavorite() {
        let mockCharacter = CharacterEntity(id: 3, name: "Iron Man", description: nil, thumbnail: nil, favoriteComics: nil, favoriteSeries: nil)
        self.addPromise = expectation(description: "Add a Character to favorites")
        interactor.addFavorite(character: mockCharacter)
        self.wait(for: [self.addPromise!], timeout: timeout)
    }
}

extension CharacterListInteractorTests: CharacterListPresenterProtocol {
    func requestFailed(_ error: Error) {
        XCTFail("Request Failed. Error: \(error.localizedDescription)")
    }
    
    func processCharacters(_ characterList: BaseResponseEntity<CharacterEntity>, hasMore: Bool) {
        XCTAssertNotNil(characterList.data?.results, "Should have a character list, even if empty")
        loadPromise?.fulfill()
    }
    
    func processAddFavorite(_ character: CharacterEntity) {
        addPromise?.fulfill()
    }
    
    func processRemoveFavorite(_ character: CharacterEntity) {
        removePromise?.fulfill()
    }
    
    func processAddFavoriteError(_ character: CharacterEntity) {
        XCTFail("Add favorite Failed.")
    }
    
    func processRemoveFavoriteError(_ character: CharacterEntity) {
        XCTFail("Remove favorite Failed.")
    }
    
    func refresh() {}
    func connectionFailed() {}
    func setView(_ abstractView: DKAbstractView) {}
}
