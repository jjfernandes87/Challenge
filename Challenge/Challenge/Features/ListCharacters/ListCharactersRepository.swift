import UIKit
import Marvel
import ChallengeCore

protocol CharactersRepositoryTypeDelegate: AnyObject {
    func `catch`(error: Error)
    func fetchedCharacters()
}

protocol CharactersRepositoryType {
    var isInfinite: Bool { get }
    var characters: [Character] { get set }
    var delegate: CharactersRepositoryTypeDelegate? { get set }

    func fetch(limit: Int, offset: Int)
    func search(name: String, limit: Int)
}

class RemoteCharactersRepository: CharactersRepositoryType {
    var isInfinite: Bool = true
    var characters: [Character] = []
    weak var delegate: CharactersRepositoryTypeDelegate?

    func fetch(limit: Int, offset: Int) {
        fetchCharacters(limit: limit, offset: offset) { (result) in
            switch result {
            case .success(let wrapper):
                if offset == 0 {
                    self.characters = wrapper.data?.results ?? []
                } else {
                    self.characters.append(contentsOf: wrapper.data?.results ?? [])
                }
                self.delegate?.fetchedCharacters()
            case .failure(let error):
                self.delegate?.catch(error: error)
            }
        }
    }

    func search(name: String, limit: Int) {
        searchCharacter(name: name, limit: limit, offset: 0) { (result) in
            switch result {
            case .success(let wrapper):
                self.characters = wrapper.data?.results ?? []
                self.delegate?.fetchedCharacters()
            case .failure(let error):
                self.delegate?.catch(error: error)
            }
        }
    }
}

class LocalCharactersRepository: CharactersRepositoryType {
    var isInfinite: Bool = false
    var characters: [Character] = []
    let jsonParser = JSONParser<Character>()
    var delegate: CharactersRepositoryTypeDelegate?
    let storage = Storage(fileManager: FileManager.default)

    init() {
        try? storage.createDirectory(name: "character")
    }

    func fetch(limit: Int, offset: Int) {
        do {
            characters = try storage.content(of: "character")
                .map { id in try storage.retrieve(from: "character/" + id) }
                .map { data in try self.jsonParser.decode(data) }
                .sorted(by: { (first, second) -> Bool in
                    (first.name ?? "") < (second.name ?? "")
                }) + []

            self.delegate?.fetchedCharacters()
        } catch {
            self.delegate?.catch(error: error)
        }
    }

    func search(name: String, limit: Int) {
        characters = characters.filter { character in
            character.name?.starts(with: name) ?? false
        }

        self.delegate?.fetchedCharacters()
    }
}
