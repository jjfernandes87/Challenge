import UIKit
import Reusable
import SDWebImage

class ResourcesDataSource: NSObject, UICollectionViewDataSource {
    var items: [CharacterResourceViewModel]

    init(items: [CharacterResourceViewModel]) {
        self.items = items
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return items.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let model = items[indexPath.row]
        let cell: CharacterResourceCollectionViewCell = collectionView.dequeueReusableCell(for: indexPath)

        cell.title.text = model.title
        cell.thumbnail.sd_setImage(with: URL(string: model.image), completed: nil)

        return cell
    }
}
