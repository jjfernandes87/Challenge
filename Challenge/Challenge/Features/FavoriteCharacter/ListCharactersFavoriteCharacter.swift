import Marvel
import ChallengeCore

extension CharactersRepositoryType {
    func isFavorited(character: Character) -> Bool {
        if let characterId = character.id {
            let storage = Storage(fileManager: FileManager.default)
            return storage.fileExists(at: "character/\(characterId)")
        }

        return false
    }

    func favorite(character: Character) throws {
        let jsonParser = JSONParser<Character>()
        let storage = Storage(fileManager: FileManager.default)

        try storage.createDirectory(name: "character")

        if let characterId = character.id {
            try storage.store(data: try jsonParser.encode(character), at: "character/\(characterId)")
        }
    }

    func unfavorite(character: Character) throws {
        let storage = Storage(fileManager: FileManager.default)

        if let characterId = character.id {
            if storage.fileExists(at: "character/\(characterId)") {
                try storage.delete(at: "character/\(characterId)")
            }
        }
    }
}
