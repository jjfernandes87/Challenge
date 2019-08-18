import UIKit
import Marvel

struct CharacterViewModel {
    let name: String
    let thumbnail: String
}

extension CharacterViewModel {
    init(character: Character) {
        self.name = character.name ?? ""
        self.thumbnail = (character.thumbnail?.path ?? "") + "." + (character.thumbnail?.extension ?? "")
    }
}

class SimpleListCharactersDataSource: NSObject, UITableViewDataSource {
    var items: [CharacterViewModel]

    init(items: [CharacterViewModel]) {
        self.items = items
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return items.count
    }

    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 40.0
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let model = items[indexPath.row]
        let cell = tableView.dequeueReusableCell(withIdentifier: "character-cell", for: indexPath) as! CharacterTableViewCell

        cell.name.text = model.name

        return cell
    }
}
