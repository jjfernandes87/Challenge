import UIKit
import Marvel
import SDWebImage
import ChallengeCore

private let limit = 20

class ListCharactersPresenter: Presenter<ListCharacterView, CharactersRepositoryType> {
    weak var router: ListCharactersRouter?

    var isLoading = false
    private var dataSource: CharactersDataSource?

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)

        navigationController?.isNavigationBarHidden = false
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        dataSource = CharactersDataSource(items: [], isInfinite: repository?.isInfinite ?? false)

        viewType.delegate = self
        dataSource?.delegate = self
        repository?.delegate = self

        viewType.charactersList.dataSource = dataSource

        fetch()
    }
}

extension ListCharactersPresenter: CharactersRepositoryTypeDelegate {
    func `catch`(error: Error) {
        DispatchQueue.main.async {
            self.router?.present(error: error)
        }
    }

    func fetchedCharacters() {
        isLoading = false
        let items = repository?.characters.map { character in CharacterViewModel(character: character, isFavorited: self.repository?.isFavorited(character: character) ?? false) } ?? []

        DispatchQueue.main.async {
            self.dataSource?.items = items
            self.viewType.charactersList.reloadData()
        }
    }
}

extension ListCharactersPresenter: ListCharacterViewDelegate {
    func refresh() {
        dataSource?.items.removeAll()
        viewType.charactersList.reloadData()

        repository?.fetch(limit: limit, offset: 0)
    }

    func fetch() {
        if isLoading || !viewType.searchCharacter.text!.isEmpty {
            return
        }

        isLoading = true

        repository?.fetch(limit: limit, offset: repository?.characters.count ?? 0)
    }

    func search(character name: String) {
        repository?.search(name: name, limit: limit)
    }

    func describe(at index: Int) {
        if let character = repository?.characters[index] {
            router?.routeToDescribe(character: character)
        } else {
            router?.present(error: NSError(domain: "application", code: 1, userInfo: ["message" : "Character not found."]))
        }
    }
}

extension ListCharactersPresenter: CharactersDataSourceDelegate {
    func star(index: Int) {
        if let character = repository?.characters[index] {
            do {
                if !(repository?.isFavorited(character: character) ?? false) {
                    try repository?.favorite(character: character)
                } else {
                    try repository?.unfavorite(character: character)
                }
            } catch {
                self.catch(error: error)
            }
        }
    }
}
