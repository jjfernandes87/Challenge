import Marvel
import Nimble
import XCTest
@testable import Challenge

let forcedError: Error = NSError(domain: "test", code: -1, userInfo: nil)

class ListCharactersTests: XCTestCase {
    var interactor: RepositoryStub!
    var presenter: ListCharactersPresenter!

    class ListCharactersPresenter: CharactersRepositoryTypeDelegate {
        var errorCatched = false
        var fetchedCharactersCalled = false

        func `catch`(error: Error) {
            errorCatched = true
        }

        func fetchedCharacters() {
            fetchedCharactersCalled = true
        }
    }

    class RepositoryStub: CharactersRepositoryType {
        var isInfinite: Bool = false

        var characters: [Character] = []

        var favorited: Bool?
        var delegate: CharactersRepositoryTypeDelegate?

        func fetch(limit: Int, offset: Int) {
            if limit < 0 {
                delegate?.catch(error: forcedError)
                return
            }

            for _ in 0..<limit {
                characters.append(Character(id: 0, name: "A", thumbnail: nil, description: nil))
            }

            delegate?.fetchedCharacters()
        }

        func search(name: String, limit: Int) {
            for _ in 0..<limit {
                characters.append(Character(id: 0, name: name, thumbnail: nil, description: nil))
            }

            delegate?.fetchedCharacters()
        }

        func star(character: Character) throws {
            favorited = true
        }

        func unstar(id: Int) throws {
            favorited = false
        }
    }

    override func setUp() {
        super.setUp()

        interactor = RepositoryStub()
        presenter = ListCharactersPresenter()

        interactor.delegate = presenter
    }

    func testFetch() {
        let expected = 20
        interactor.fetch(limit: 20, offset: 0)
        let given = interactor.characters.count

        expect { given }.to(equal(expected))
        expect { self.presenter.fetchedCharactersCalled }.to(equal(true))
    }

    func testEmptyResult() {
        let expected = interactor.characters.count
        interactor.fetch(limit: 0, offset: 0)
        let given = interactor.characters.count

        expect { given }.to(equal(expected))
        expect { self.presenter.fetchedCharactersCalled }.to(equal(true))
    }

    func testFetch3times() {
        let expected = 60

        interactor.fetch(limit: 20, offset: 0)

        interactor.fetch(limit: 20, offset: 0)

        interactor.fetch(limit: 20, offset: 0)

        let given = interactor.characters.count

        expect { given }.to(equal(expected))
        expect { self.presenter.fetchedCharactersCalled }.to(equal(true))
    }

    func testFetchError() {
        let expected = true

        interactor.fetch(limit: -1, offset: 0)

        expect { self.presenter.errorCatched }.to(equal(expected))
    }

    func testFavorite() {
        let expected = true

        interactor.fetch(limit: 20, offset: 0)
        try? interactor.star(character: interactor.characters[0])

        expect { self.interactor.favorited }.to(equal(expected))
    }

    func testUnfavorite() {
        let expected = false

        interactor.fetch(limit: 20, offset: 0)
        try? interactor.unstar(id: 0)

        expect { self.interactor.favorited }.to(equal(expected))
    }

    override func tearDown() {
        super.tearDown()

        interactor = nil
    }
}
