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

    private let timeout = 60.0
    private var interactor: CharacterDetailInteractor!
    private var fetchComicsPromise: XCTestExpectation?
    private var fetchSeriesPromise: XCTestExpectation?
    
    override func setUp() {
        interactor = CharacterDetailInteractor()
        interactor.setPresenter(self)
    }

    override func tearDown() {
        interactor = nil
    }

    func testFetchComics() {
        self.fetchComicsPromise = expectation(description: "Generates a list of ComicEntity")
        interactor.fetchComics(characterID: 1009351)
        self.wait(for: [self.fetchComicsPromise!], timeout: timeout)
    }

    func testFetchSeries() {
        self.fetchSeriesPromise = expectation(description: "Generates a list of SerieEntity")
        interactor.fetchSeries(characterID: 1009351)
        self.wait(for: [self.fetchSeriesPromise!], timeout: timeout)
    }
}

extension CharacterDetailInteractorTests: CharacterDetailPresenterProtocol {
    func processComics(_ comicList: [ComicEntity]) {
        XCTAssertNotNil(comicList, "Should have a comic list, even if is empty")
        fetchComicsPromise?.fulfill()
    }
    
    func processSeries(_ serieList: [SerieEntity]) {
        XCTAssertNotNil(serieList, "Should have a serie list, even if is empty")
        fetchSeriesPromise?.fulfill()
    }
    
    func processError(_ errorType: CharacterDetailPresenterErrorType) {
        switch errorType {
        case .fetchComics:
            XCTFail("Error fetching comics.")
        case .fetchSeries:
            XCTFail("Error fetching series.")
        }
    }
    
    func setView(_ abstractView: DKAbstractView) {}
}
