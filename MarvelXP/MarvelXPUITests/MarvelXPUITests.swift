//
//  MarvelXPUITests.swift
//  MarvelXPUITests
//
//  Created by Roger Sanoli on 14/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import XCTest
@testable import MarvelXP

class MarvelXPUITests: XCTestCase {
    
    private let timeout = 30.0
    private var removeAllPromise: XCTestExpectation?
    
    override func setUp() {
        continueAfterFailure = false
        XCUIApplication().launch()
    }
    
    override func tearDown() {}

    func testSearchBlackPanther() {
        let app = XCUIApplication()
        sleep(5)
        let search = app.searchFields["search characters..."]
        search.tap()
        search.typeText("Black")
        sleep(5)
        let collectionViewsQuery = app.collectionViews
        let blackCatStaticText = collectionViewsQuery/*@START_MENU_TOKEN@*/.staticTexts["Black Cat"]/*[[".cells.staticTexts[\"Black Cat\"]",".staticTexts[\"Black Cat\"]"],[[[-1,1],[-1,0]]],[0]]@END_MENU_TOKEN@*/
        blackCatStaticText.swipeUp()
        let blackPantherStaticText = collectionViewsQuery.staticTexts["Black Panther"]
        blackPantherStaticText.tap()
        sleep(5)
        XCTAssert(app.navigationBars["Black Panther"].otherElements["Black Panther"].exists, "Should load Black Panther")
    }
    
    func testFetchSecondPage() {
        let app = XCUIApplication()
        sleep(5)
        let collectionViewsQuery = app.collectionViews
        collectionViewsQuery.staticTexts["A.I.M."].swipeUp()
        collectionViewsQuery.staticTexts["Absorbing Man"].swipeUp()
        collectionViewsQuery.staticTexts["Adam Warlock"].swipeUp()
        collectionViewsQuery.staticTexts["Agent Zero"].swipeUp()
        sleep(5)
        XCTAssert(collectionViewsQuery.staticTexts["Albert Cleary"].exists, "Should have loaded Albert Cleary")
    }

}
