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
    
    override func setUp() {
        continueAfterFailure = false
        XCUIApplication().launch()
    }

    override func tearDown() {}

    func testFavorites() {
        //XCUIApplication().collectionViews.children(matching: .cell).element(boundBy: 2).children(matching: .other).element.children(matching: .other).element.children(matching: .other).element.tap()
        //let app = XCUIApplication()
        
        //app.tabBars.buttons["Favorites"].tap()
        //app.buttons["Try Again"].tap()
        
        //let app = XCUIApplication()
        //sleep(3)
        //let tabBarsQuery = app.tabBars
        XCUIApplication().searchFields["search characters..."].tap()
        
        
        //tabBarsQuery.buttons["Favorites"].tap()
        //XCTAssert(app.buttons["Try Again"].exists, "Should have empty message with try again button.")
        
        
        
        /*tabBarsQuery.buttons["Characters"].tap()
        
        let collectionViewsQuery = app.collectionViews
        collectionViewsQuery.children(matching: .cell).element(boundBy: 1).children(matching: .other).element.children(matching: .other).element.children(matching: .other).element.tap()
        
        let aBombHasNavigationBar = app.navigationBars["A-Bomb (HAS)"]
        let itemButton = aBombHasNavigationBar.buttons["Item"]
        itemButton.tap()
        aBombHasNavigationBar.buttons["Back"].tap()
        favoritesButton.tap()
        collectionViewsQuery.cells.children(matching: .other).element.children(matching: .other).element.children(matching: .other).element.tap()
        XCUIDevice.shared.orientation = .landscapeRight
        itemButton.tap()
        app.buttons["Try Again"].tap()*/
        
        
        
    }

}
