import XCTest
import Nimble

class ChallengeUITests: XCTestCase {
    var app: XCUIApplication!

    override func setUp() {
        continueAfterFailure = false

        app = XCUIApplication()
    }

    func testListCharacters() {
        app.launch()

        app.swipeRight()

        sleep(20)

        expect { self.app.collectionViews.firstMatch.staticTexts.count } > 0
    }

    func testDescribeCharacter() {
        app.launch()

        sleep(20)

        app.swipeRight()

        let firstCharacter = app.collectionViews.staticTexts.firstMatch.value as? String
        app.collectionViews.cells.firstMatch.tap()

        expect { self.app.scrollViews.staticTexts.firstMatch.value as? String }.to(equal(firstCharacter))
    }

    func testFavoriteCharacters() {
        app.launch()

        var totalSwipes = 0

        app.swipeRight()

        sleep(20)

        while totalSwipes < 10 {
            app.collectionViews.cells.firstMatch.buttons.firstMatch.tap()
            app.collectionViews.firstMatch.swipeUp()

            totalSwipes += 1
        }

        expect { true }.to(equal(true))
    }

    func testRemoveAllFavorites() {
        app.launch()

        app.tabBars.buttons["Favorites"].tap()

        app.swipeRight()

        while app.collectionViews.cells.count != 0 {
            app.collectionViews.cells.firstMatch.buttons.firstMatch.tap()
            app.swipeDown()
        }

        expect { true }.to(equal(true))
    }

    override func tearDown() {
    }

}
