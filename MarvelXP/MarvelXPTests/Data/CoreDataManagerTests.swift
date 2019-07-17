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

    override func setUp() {
        continueAfterFailure = false
        CoreDataManager.removeAllFavorites()
    }

    override func tearDown() {}

    func testAddAndFetchFavorites() {
        let mockThumbnail = ThumbnailEntity(path: "www.somepath.com/file", extension: "png")
        let mockComic1 = ComicEntity(title: "Comic 1", thumbnail: nil)
        let mockComic2 = ComicEntity(title: "Comic 2", thumbnail: nil)
        let mockSeries1 = SeriesEntity(title: "Series 1", thumbnail: nil)
        let mockSeries2 = SeriesEntity(title: "Series 2", thumbnail: nil)
        let mockCharacter = CharacterEntity(id: 1, name: "Spider Man", description: "Best hero ever.", thumbnail: mockThumbnail, comics: [mockComic1, mockComic2], serieses: [mockSeries1, mockSeries2])
        let mockCharacter2 = CharacterEntity(id: 2, name: "Thor", description: nil, thumbnail: nil, comics: nil, serieses: nil)
        
        CoreDataManager.addFavorite(mockCharacter)
        CoreDataManager.addFavorite(mockCharacter2)
        
        let characterList = CoreDataManager.fetchFavorites()
        
        XCTAssertNotEqual(characterList?.first?.name, "Thor", "Order error.")
        
        guard let spiderMan = characterList?.first else {
            XCTFail("Should have spiderman.")
            return
        }
        XCTAssertEqual(spiderMan.id, 1, "Data inconsistency.")
        XCTAssertEqual(spiderMan.name, "Spider Man", "Data inconsistency.")
        XCTAssertEqual(spiderMan.description, "Best hero ever.", "Data inconsistency.")
        XCTAssertEqual(spiderMan.thumbnail?.getURLPath(), "www.somepath.com/file.png", "Data inconsistency.")
        XCTAssertEqual(spiderMan.comics?.count ?? -1, 2, "Should have 2 comics")
        XCTAssertEqual(spiderMan.serieses?.count ?? -1, 2, "Should have 2 serieses")
        
        guard let comic1 = spiderMan.comics?[0],
        let comic2 = spiderMan.comics?[1],
        let series1 = spiderMan.serieses?[0],
        let series2 = spiderMan.serieses?[1]
        else {
            XCTFail("Data inconsistency.")
            return
        }
        XCTAssertEqual(comic1.title, "Comic 1", "Data inconsistency.")
        XCTAssertEqual(comic2.title, "Comic 2", "Data inconsistency.")
        XCTAssertEqual(series1.title, "Series 1", "Data inconsistency.")
        XCTAssertEqual(series2.title, "Series 2", "Data inconsistency.")
    }
}
