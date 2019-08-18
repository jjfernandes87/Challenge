import Marvel

struct CharacterViewModel {
    let id: Int
    let name: String
    let isFavorited: Bool
    let thumbnail: String
    let description: String
}

extension CharacterViewModel {
    init(character: Character, isFavorited: Bool) {
        id = character.id ?? 0
        name = character.name ?? ""
        self.isFavorited = isFavorited
        description = character.description ?? ""
        thumbnail = (character.thumbnail?.path ?? "") + "." + (character.thumbnail?.extension ?? "")
    }
}
