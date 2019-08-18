import XCTest
import Nimble
@testable import Marvel

class MarvelTests: XCTestCase {

    func testMD5Generator() {
        let expected = "8b1a9953c4611296a827abf8c47804d7"
        let given = "Hello"
        let when = md5(string: given)

        expect { expected }.to(equal(when))
    }

    func testMarvelHash() {
        let expected = "9206ecea43571e3f675602cb2e0e9bfa"
        let given = "156583147081d4a002dc4ee76a435a30d761f6f9dbc9903347b84a90ac720fe75f2493ccb28f4e7051"
        let when = md5(string: given)

        expect { expected }.to(equal(when))
    }

}
