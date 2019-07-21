//
//  MarvelRepositoryTests.swift
//  MarvelXPTests
//
//  Created by Roger Sanoli on 15/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import XCTest
@testable import MarvelXP
@testable import RogueKit

class MarvelRepositoryTests: XCTestCase {
    
    private let timeout = 60.0

    override func setUp() {
        continueAfterFailure = false
    }

    override func tearDown() {}

    func testCharacterList() {
        
        let promise = expectation(description: "Generates a CharacterEntity array.")
        
        RogueKit.request(MarvelRepository.fetchCharacters(offset: 0, limit: 20, searchName: nil)) { (result: ListResult<CharacterEntity>) in
            switch result {
            case let .success(responseEntity):
                XCTAssertNotNil(responseEntity.data?.results, "Invalid data.")
                guard let characters = responseEntity.data?.results else { return }
                XCTAssertNotNil(characters.first, "Something went wrong, Marvel API should have some character.")
                guard let firstCharacter = characters.first else { return }
                XCTAssertNotNil(firstCharacter.id, "The character should have an id.")
                XCTAssertNotNil(firstCharacter.name, "The character should have a name.")
                XCTAssertNotNil(firstCharacter.description, "The character should have a description.")
                XCTAssertNotEqual(firstCharacter.thumbnail?.getURLPath() ?? "", "", "The character should have a thumbnail.")
                promise.fulfill()
                
            case let .failure(error):
                XCTFail("Request Failed. Error: \(error.localizedDescription)")
            }
        }
        
        self.wait(for: [promise], timeout: timeout)
    }
    
    func testHulkSearch() {
        
        let promise = expectation(description: "To find Hulk.")
        
        RogueKit.request(MarvelRepository.fetchCharacters(offset: 0, limit: 20, searchName: "Hul")) { (result: ListResult<CharacterEntity>) in
            switch result {
            case let .success(responseEntity):
                XCTAssertNotNil(responseEntity.data?.results, "Invalid data.")
                guard let characters = responseEntity.data?.results else { return }
                XCTAssertNotNil(characters.first, "Something went wrong, at least Hulk should exist, probably also Shehulk.")
                guard let firstCharacter = characters.first else { return }
                XCTAssertEqual(firstCharacter.name, "Hulk", "first match should be Hulk.")
                promise.fulfill()
                
            case let .failure(error):
                XCTFail("Request Failed. Error: \(error.localizedDescription)")
            }
        }
        
        self.wait(for: [promise], timeout: timeout)
    }
    
    func testWolverineFetch() {
        let promise = expectation(description: "Fetch Wolverine from id.")
        
        RogueKit.request(MarvelRepository.fetchCharacter(characterID: 1009718)) { (result: ListResult<CharacterEntity>) in
            switch result {
                case let .success(responseEntity):
                    XCTAssertNotNil(responseEntity.data?.results, "Invalid data.")
                    guard let characters = responseEntity.data?.results else { return }
                    XCTAssertNotNil(characters.first, "Something went wrong, Wolverine should exist.")
                    guard let firstCharacter = characters.first else { return }
                    XCTAssertEqual(firstCharacter.name, "Wolverine", "first match should be Wolverine.")
                    promise.fulfill()
                case let .failure(error):
                    XCTFail("Request Failed. Error: \(error.localizedDescription)")
            }
        }
        
        self.wait(for: [promise], timeout: timeout)
    }
    
    func testComicList() {
        
        let promise = expectation(description: "Generates a ComicEntity array.")
        
        RogueKit.request(MarvelRepository.fetchComics(characterID: 1009351)) { (result: ListResult<ComicEntity>) in
            switch result {
            case let .success(responseEntity):
                XCTAssertNotNil(responseEntity.data?.results, "Invalid data.")
                guard let comics = responseEntity.data?.results else { return }
                XCTAssertNotNil(comics.first, "Something went wrong, Hulk has a lot of comics.")
                guard let firstComic = comics.first else { return }
                XCTAssertNotNil(firstComic.title, "The comic should have a title.")
                XCTAssertNotEqual(firstComic.thumbnail?.getURLPath() ?? "", "", "The comic should have a thumbnail.")
                promise.fulfill()
                
            case let .failure(error):
                XCTFail("Request Failed. Error: \(error.localizedDescription)")
            }
        }
        
        self.wait(for: [promise], timeout: timeout)
    }
    
    func testSeriesList() {
        
        let promise = expectation(description: "Generates a SeriesEntity array.")
        
        RogueKit.request(MarvelRepository.fetchSeries(characterID: 1009351)) { (result: ListResult<SerieEntity>) in
            switch result {
            case let .success(responseEntity):
                XCTAssertNotNil(responseEntity.data?.results, "Invalid data.")
                guard let series = responseEntity.data?.results else { return }
                XCTAssertNotNil(series.first, "Something went wrong, Hulk has a lot of series.")
                guard let firstSeries = series.first else { return }
                XCTAssertNotNil(firstSeries.title, "The series should have a title.")
                XCTAssertNotEqual(firstSeries.thumbnail?.getURLPath() ?? "", "", "The series should have a thumbnail.")
                promise.fulfill()
                
            case let .failure(error):
                XCTFail("Request Failed. Error: \(error.localizedDescription)")
            }
        }
        
        self.wait(for: [promise], timeout: timeout)
    }
}
