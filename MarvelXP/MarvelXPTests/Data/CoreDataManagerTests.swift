//
//  CoreDataManagerTests.swift
//  MarvelXPTests
//
//  Created by Roger Sanoli on 16/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import XCTest
@testable import MarvelXP

class CoreDataManagerTests: XCTestCase {
    
    private var removeAllPromise: XCTestExpectation?
    
    override func setUp() {
        continueAfterFailure = false
        removeAllPromise = expectation(description: "Remove all favorites")
        
        CoreDataManager.removeAllFavorites() {
            self.removeAllPromise?.fulfill()
        }
        
        wait(for: [removeAllPromise!], timeout: 5)
    }

    override func tearDown() {}

    func testAddAndFetchFavorites() {
        let mockThumbnail = ThumbnailEntity(path: "www.somepath.com/file", extension: "png")
        let mockComic1 = ComicEntity(title: "Comic 1", thumbnail: nil)
        let mockComic2 = ComicEntity(title: "Comic 2", thumbnail: nil)
        let mockSerie1 = SerieEntity(title: "Serie 1", thumbnail: nil)
        let mockSerie2 = SerieEntity(title: "Serie 2", thumbnail: nil)
        let mockCharacter = CharacterEntity(id: 1, name: "Spider Man", description: "Best hero ever.", thumbnail: mockThumbnail, isFavorited: false, favoriteComics: [mockComic1, mockComic2], favoriteSeries: [mockSerie1, mockSerie2])
        let mockCharacter2 = CharacterEntity(id: 2, name: "Thor", description: nil, thumbnail: nil, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        
        let promise = expectation(description: "Retrieve favorited data with consistency.")
        
        CoreDataManager.addFavorite(mockCharacter) { _ in
            CoreDataManager.addFavorite(mockCharacter2) { _ in
                CoreDataManager.fetchFavorites { (characterList) in
                    XCTAssertNotEqual(characterList?.first?.name, "Thor", "Order error.")
                    
                    guard let spiderMan = characterList?.first else {
                        XCTFail("Should have spiderman.")
                        return
                    }
                    XCTAssertEqual(spiderMan.id, 1, "Data inconsistency.")
                    XCTAssertEqual(spiderMan.name, "Spider Man", "Data inconsistency.")
                    XCTAssertEqual(spiderMan.description, "Best hero ever.", "Data inconsistency.")
                    XCTAssertEqual(spiderMan.thumbnail?.getURLPath(), "www.somepath.com/file.png", "Data inconsistency.")
                    XCTAssertEqual(spiderMan.favoriteComics?.count ?? -1, 2, "Should have 2 comics")
                    XCTAssertEqual(spiderMan.favoriteSeries?.count ?? -1, 2, "Should have 2 series")
                    
                    guard let comic1 = spiderMan.favoriteComics?[0],
                        let comic2 = spiderMan.favoriteComics?[1],
                        let serie1 = spiderMan.favoriteSeries?[0],
                        let serie2 = spiderMan.favoriteSeries?[1]
                        else {
                            XCTFail("Data inconsistency.")
                            return
                    }
                    XCTAssertEqual(comic1.title, "Comic 1", "Data inconsistency.")
                    XCTAssertEqual(comic2.title, "Comic 2", "Data inconsistency.")
                    XCTAssertEqual(serie1.title, "Serie 1", "Data inconsistency.")
                    XCTAssertEqual(serie2.title, "Serie 2", "Data inconsistency.")
                    
                    promise.fulfill()
                }
            }
        }
        
        self.wait(for: [promise], timeout: 5)
    }
    
    
    func testRemoveFavorite() {
        let promise = expectation(description: "Remove a character from favorites.")
        
        let mockCharacter = CharacterEntity(id: 1, name: "Spider Man", description: "Best hero ever.", thumbnail: nil, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        
        CoreDataManager.addFavorite(mockCharacter) { _ in
            
            CoreDataManager.fetchFavorites { (characterList) in

                XCTAssertEqual(characterList?.first?.name, "Spider Man", "Sould have Spider Man.")
                
                CoreDataManager.removeFavorite(1) { _ in
                    CoreDataManager.fetchFavorites { (updatedList) in
                        XCTAssertNil(updatedList?.first, "Villains win.")
                        promise.fulfill()
                    }
                }
            }
        }
        
        self.wait(for: [promise], timeout: 5)
    }
    
    func testFetchByID() {
        let promise = expectation(description: "Fetch a character from favorites.")
        
        let mockCharacter = CharacterEntity(id: 15, name: "Magneto", description: "Best villain ever.", thumbnail: nil, isFavorited: false, favoriteComics: nil, favoriteSeries: nil)
        
        CoreDataManager.addFavorite(mockCharacter) { _ in
            CoreDataManager.fetchFavorite(15, completion: { (character) in
                XCTAssertEqual(character?.name, "Magneto", "Sould have Magneto.")
                promise.fulfill()
            })
        }
        
        self.wait(for: [promise], timeout: 5)
    }
}
