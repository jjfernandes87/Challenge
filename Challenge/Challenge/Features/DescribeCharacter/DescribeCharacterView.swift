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

        scrollableView.contentInset = UIEdgeInsets(top: 60.0, left: 0.0, bottom: 0.0, right: 0.0)

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
