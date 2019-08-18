import UIKit

protocol SimpleListCharactersViewDelegate: AnyObject {
    func describe(character at: Int)
}

class SimpleListCharactersView: UIView {
    @IBOutlet weak var charactersList: UITableView!

    weak var delegate: SimpleListCharactersViewDelegate?

    override func awakeFromNib() {
        super.awakeFromNib()

        charactersList.delegate = self
        charactersList.rowHeight = 60.0
    }
}

extension SimpleListCharactersView: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        delegate?.describe(character: indexPath.row)
    }
}
