import Marvel
import Foundation
import ChallengeCore

protocol DescribeCharacterRepositoryTypeDelegate: AnyObject {
    func `catch`(error: Error)
    func describeCharacter(didFetch series: [Series])
    func describeCharacter(didFetch comics: [Comics])
}

protocol DescribeCharacterRepositoryType {
    var character: Character { get }
    var delegate: DescribeCharacterRepositoryTypeDelegate? { get set }

    func loadSeries()
    func loadComics()
    func isFavorited(character: Character) -> Bool
}

class DescribeCharacterRepository: DescribeCharacterRepositoryType {
    var character: Character
    weak var delegate: DescribeCharacterRepositoryTypeDelegate?

    init(character: Character) {
        self.character = character
    }

    func loadSeries() {
        if let characterId = character.id {
            fetchSeries(characterId: characterId, limit: 20, offset: 0) { (result) in
                switch result {
                case .success(let series):
                    self.delegate?.describeCharacter(didFetch: series.data?.results ?? [])
                case .failure(let error):
                    self.delegate?.catch(error: error)
                }
            }
        }
    }

    func loadComics() {
        if let characterId = character.id {
            fetchComics(characterId: characterId, limit: 20, offset: 0) { (result) in
                switch result {
                case .success(let comics):
                    self.delegate?.describeCharacter(didFetch: comics.data?.results ?? [])
                case .failure(let error):
                    self.delegate?.catch(error: error)
                }
            }
        }
    }

    func isFavorited(character: Character) -> Bool {
        if let characterId = character.id {
            let storage = Storage(fileManager: FileManager.default)
            return storage.fileExists(at: "character/\(characterId)")
        }

        return false
    }
}
