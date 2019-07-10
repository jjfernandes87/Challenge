//
//  ChallengeMarvelUITests.swift
//  ChallengeMarvelUITests
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import XCTest

class ChallengeMarvelUITests: XCTestCase {

    var app: XCUIApplication!
    
    override func setUp() {
        continueAfterFailure = false
        app = XCUIApplication()
        app.launch()
    }

    func testScroll() {
        let characterList = app.collectionViews["CharacterList"]
        XCTAssertTrue(characterList.exists)
        characterList.swipeUp()
        characterList.swipeDown()
    }

    func testSelectedCharacter() {
        let characterList = app.collectionViews["CharacterList"]
        let cell = characterList.cells.firstMatch
        cell.tap()
    }
    
    func testSelectedFavoriteCharacter() {
        let characterList = app.collectionViews["CharacterList"]
        let cell = characterList.cells.firstMatch
        let buttonFavorite = cell.buttons.firstMatch
        buttonFavorite.tap()
    }
    
    
}
