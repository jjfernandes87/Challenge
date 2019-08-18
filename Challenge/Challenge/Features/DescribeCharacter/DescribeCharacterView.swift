import UIKit

class DescribeCharacterView: UIView {
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var image: UIImageView!
    @IBOutlet weak var contentView: UIStackView!
    @IBOutlet weak var scrollableView: UIScrollView!
    @IBOutlet weak var descriptionCharacter: UITextView!

    var seriesCollection: CharacterResourceSectionView!
    var comicsCollection: CharacterResourceSectionView!

    override func awakeFromNib() {
        super.awakeFromNib()

        seriesCollection = CharacterResourceSectionView.loadFromNib()
        seriesCollection.title.text = "Series"
        seriesCollection.heightAnchor.constraint(equalToConstant: 240.0).isActive = true

        comicsCollection = CharacterResourceSectionView.loadFromNib()
        comicsCollection.title.text = "Comics"
        comicsCollection.heightAnchor.constraint(equalToConstant: 240.0).isActive = true

        contentView.addArrangedSubview(comicsCollection)
        contentView.addArrangedSubview(seriesCollection)
    }
}
