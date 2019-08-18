import XCTest
import Nimble
@testable import ChallengeCore

class JSONParserTests: XCTestCase {
    struct TestClass: Codable {
        var message: String
    }

    func testEncode() {
        let expected = "{\"message\":\"Hello World!\"}".data(using: String.Encoding.utf8)!
        let given = TestClass(message: "Hello World!")
        let jsonParser = JSONParser<TestClass>()

        expect { try jsonParser.encode(given) }.to(equal(expected))
    }

    func testDecode() {
        let expected = "Hello World!"
        let given = "{\"message\":\"Hello World!\"}".data(using: String.Encoding.utf8)!
        let jsonParser = JSONParser<TestClass>()

        expect { try jsonParser.decode(given).message }.to(equal(expected))
    }

    func testDecodeFailing() {
        let given = "{\"foo\":\"Hello World!\"}".data(using: String.Encoding.utf8)!
        let jsonParser = JSONParser<TestClass>()

        expect { try jsonParser.decode(given) }.to(throwError())
    }

}
