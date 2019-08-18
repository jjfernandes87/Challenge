import XCTest
import Nimble
import Marvel
@testable import Challenge

private let character = Character(id: 0, name: "Foo", thumbnail: nil, description: nil)

class DecribeCharacterTests: XCTestCase {
    var presenter: DescribeCharacterPresenter!
    var repositoryDelegate: RepositoryDelegate!
    var interactor: DescribeCharacterRepositoryType!

    class RepositoryStub: DescribeCharacterRepositoryType {
        var character: Character
        var delegate: DescribeCharacterRepositoryTypeDelegate?

        init(character: Character) {
            self.character = character
        }

        func loadSeries() {
            delegate?.describeCharacter(didFetch: [ Series(id: nil, title: nil, thumbnail: nil), Series(id: nil, title: nil, thumbnail: nil), Series(id: nil, title: nil, thumbnail: nil) ])
        }

        func loadComics() {
            delegate?.describeCharacter(didFetch: [ Comics(id: nil, title: nil, thumbnail: nil), Comics(id: nil, title: nil, thumbnail: nil), Comics(id: nil, title: nil, thumbnail: nil) ])
        }

        func isFavorited(character: Character) -> Bool {
            return true
        }
    }

    class RepositoryDelegate: DescribeCharacterRepositoryTypeDelegate {
        var errorCatched = false
        var series: [Series] = []
        var comics: [Comics] = []

        func `catch`(error: Error) {
            errorCatched = true
        }

        func describeCharacter(didFetch series: [Series]) {
            self.series = series
        }

        func describeCharacter(didFetch comics: [Comics]) {
            self.comics = comics
        }
    }

    override func setUp() {
        presenter = DescribeCharacterPresenter()
        repositoryDelegate = RepositoryDelegate()
        interactor = RepositoryStub(character: character)

        interactor.delegate = repositoryDelegate
    }

    func testFetchComics() {
        let expected = 3

        interactor.loadComics()

        expect { self.repositoryDelegate.comics.count }.to(equal(expected))
    }

    func testFetchSeries() {
        let expected = 3

        interactor.loadSeries()

        expect { self.repositoryDelegate.series.count }.to(equal(expected))
    }

    override func tearDown() {
        presenter = nil
        repositoryDelegate = nil
        interactor = nil
    }

}
