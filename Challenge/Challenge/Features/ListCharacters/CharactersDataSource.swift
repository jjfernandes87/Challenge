import UIKit
import SDWebImage

protocol CharactersDataSourceDelegate: AnyObject {
    func star(index: Int)
}

class AccessoryCollectionReusableView: UICollectionReusableView {
    @IBOutlet weak var message: UILabel!
}

class CharactersDataSource: NSObject, UICollectionViewDataSource {
    var isInfinite: Bool
    var items: [CharacterViewModel]

    weak var delegate: CharactersDataSourceDelegate?

    init(items: [CharacterViewModel], isInfinite: Bool) {
        self.items = items
        self.isInfinite = isInfinite
    }

    func append(items: [CharacterViewModel]) {
        self.items.append(contentsOf: items)
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return items.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        var model = items[indexPath.row]
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "character-cell", for: indexPath) as! CharacterCollectionViewCell

        cell.name.text = model.name
        cell.name.isAccessibilityElement = true
        cell.name.accessibilityLabel = model.name
        cell.thumbnail.sd_setImage(with: URL(string: model.thumbnail), placeholderImage: nil, options: SDWebImageOptions.continueInBackground, context: nil)

        cell.starAction = {
            model.isFavorited = !model.isFavorited
            self.delegate?.star(index: indexPath.row)
            cell.star.setImage(UIImage(named: model.isFavorited ? "pixel-starred" : "pixel-star"), for: UIControl.State.normal)
        }

        if model.isFavorited {
            cell.star.isAccessibilityElement = true
            cell.star.accessibilityLabel = "Desfavoritar \(model.name)"
            cell.star.setImage(UIImage(named: "pixel-starred"), for: UIControl.State.normal)
        } else {
            cell.star.accessibilityLabel = "Favoritar \(model.name)"
            cell.star.setImage(UIImage(named: "pixel-star"), for: UIControl.State.normal)
        }

        return cell
    }

    func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView {
        let accessory = collectionView.dequeueReusableSupplementaryView(ofKind: UICollectionView.elementKindSectionFooter, withReuseIdentifier: "accessory", for: indexPath) as! AccessoryCollectionReusableView

        if isInfinite {
            accessory.message.text = "Carregando..."
        }

        if isInfinite && items.isEmpty {
            accessory.message.text = "Carregando..."
        }

        if items.isEmpty && !isInfinite {
            accessory.message.text = "Lista vazia :("
        }

        if !isInfinite && !items.isEmpty {
            accessory.message.text = ""
        }

        return accessory
    }
}
