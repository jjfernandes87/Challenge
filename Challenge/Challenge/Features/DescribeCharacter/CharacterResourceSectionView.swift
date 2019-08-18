import UIKit
import Reusable

class CharacterResourceSectionView: UIView, NibLoadable {
    @IBOutlet weak var title: UILabel!
    @IBOutlet weak var resourcesList: UICollectionView!

    override func awakeFromNib() {
        super.awakeFromNib()

        let listLayout = UICollectionViewFlowLayout()

        listLayout.minimumLineSpacing = 10.0
        listLayout.scrollDirection = UICollectionView.ScrollDirection.horizontal
        listLayout.itemSize = CGSize(width: frame.width / 2.0 - 40.0, height: 200.0)
        listLayout.sectionInset = UIEdgeInsets(top: 30.0, left: 15.0, bottom: 30.0, right: 15.0)

        resourcesList.collectionViewLayout = listLayout
        resourcesList.register(cellType: CharacterResourceCollectionViewCell.self)
    }
}
