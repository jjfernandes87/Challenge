//
//  FavoriteListInteractorTests.swift
//  MarvelXPTests
//
//  Created by Roger Sanoli on 17/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import XCTest
@testable import MarvelXP
@testable import DungeonKit

class FavoriteListInteractorTests: XCTestCase {
    
    private let timeout = 60.0
    private var interactor: FavoriteListInteractor!
    private var fetchPromise: XCTestExpectation?
    private var removePromise: XCTestExpectation?

    override func setUp() {
        CoreDataManager.removeAllFavorites()
        
        interactor = FavoriteListInteractor()
        interactor.setPresenter(self)
    }

    override func tearDown() {
        interactor = nil
    }

    func testFetchCharacterList() {
        self.fetchPromise = expectation(description: "Generates a list of favorites.")
        interactor.fetchFavorites()
        self.wait(for: [self.fetchPromise!], timeout: timeout)
    }
    
    func testRemoveFavorite() {
        let mockCharacter = CharacterEntity(id: 4, name: "Captain America", description: nil, thumbnail: nil, favoriteComics: nil, favoriteSeries: nil)
        self.removePromise = expectation(description: "Remove a Character from favorites")
        CoreDataManager.addFavorite(mockCharacter)
        interactor.removeFavorite(character: mockCharacter)
        self.wait(for: [self.removePromise!], timeout: timeout)
    }
}

extension FavoriteListInteractorTests: FavoriteListPresenterProtocol {
    
    func processFavoriteList(_ favoriteList: [CharacterEntity]) {
        XCTAssertNotNil(favoriteList, "Should have a character list, even if is empty")
        fetchPromise?.fulfill()
    }
    
    func processRemoveFavorite(_ character: CharacterEntity) {
        removePromise?.fulfill()
    }
    
    func setView(_ abstractView: DKAbstractView) {}
    
    func processError(_ errorType: FavoriteListPresenterErrorType) {
        switch errorType {
        case .fetchFavorites:
            XCTFail("Fetch favorites Failed.")
        case .removeFavorite:
            XCTFail("Remove favorite Failed.")
        }
    }
}
