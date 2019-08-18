import UIKit

class CharacterCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var star: UIButton!
    @IBOutlet weak var thumbnail: UIImageView!

    var starAction: (() -> Void)?

    @IBAction func touchupinFromStar(_ sender: UIButton) {
        starAction?()
    }
}
