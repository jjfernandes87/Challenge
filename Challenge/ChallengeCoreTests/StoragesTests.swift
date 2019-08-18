import XCTest
import Nimble
@testable import ChallengeCore

fileprivate struct TestData: Encodable {
    let message: String
}

fileprivate let fileManagerStub = FileManager()

class StoragesTests: XCTestCase {
    var storage: Storage!

    override func setUp() {
        storage = Storage(fileManager: fileManagerStub)
    }

    func testCreateDirectory() {
        expect { try self.storage.createDirectory(name: "test") }.notTo(throwError())
    }

    func testNotExistFile() {
        expect { self.storage.fileExists(at: "not_exists") }.to(equal(false))
    }

    func testStoreAndExistFile() {
        expect { try self.storage.store(data: "0".data(using: String.Encoding.utf8)!, at: "exists") }.notTo(throwError())
        expect { self.storage.fileExists(at: "exists") }.to(equal(true))
    }

    func testSuccessRetrieve() {
        expect { try self.storage.retrieve(from: "exists") }.notTo(throwError())
    }

    func testFailsRetrieve() {
        expect { try self.storage.retrieve(from: "not-retrieved") }.to(throwError())
    }

    func testStoreAndDeleteFile() {
        expect { try self.storage.store(data: "0".data(using: String.Encoding.utf8)!, at: "exists") }.notTo(throwError())
        expect { try self.storage.delete(at: "exists") }.notTo(throwError())
    }

    func testDeleteNotExistsFile() {
        expect { try self.storage.delete(at: "not-exists") }.to(throwError())
    }

    override func tearDown() {
        storage = nil
    }

}
