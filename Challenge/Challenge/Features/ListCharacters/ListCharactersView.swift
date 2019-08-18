import UIKit

protocol ListCharacterViewDelegate: AnyObject {
    func fetch()
    func refresh()
    func describe(at index: Int)
    func search(character name: String)
}

class ListCharacterView: UIView {
    var refresher = UIRefreshControl()

    weak var delegate: ListCharacterViewDelegate?

    @IBOutlet weak var searchCharacter: UISearchBar!
    @IBOutlet weak var charactersList: UICollectionView!

    override func awakeFromNib() {
        super.awakeFromNib()

        let listLayout = UICollectionViewFlowLayout()

        listLayout.minimumLineSpacing = 10.0
        listLayout.scrollDirection = UICollectionView.ScrollDirection.vertical
        listLayout.itemSize = CGSize(width: frame.width / 2.0 - 40.0, height: 200.0)
        listLayout.sectionInset = UIEdgeInsets(top: 30.0, left: 15.0, bottom: 30.0, right: 15.0)

        charactersList.delegate = self
        searchCharacter.delegate = self

        charactersList.refreshControl = refresher
        charactersList.collectionViewLayout = listLayout

        refresher.addTarget(self, action: #selector(refresh), for: UIControl.Event.primaryActionTriggered)
    }

    @objc func refresh() {
        delegate?.refresh()
        refresher.endRefreshing()
        searchCharacter.text = ""
    }
}

extension ListCharacterView: UISearchBarDelegate {
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        delegate?.refresh()

        searchBar.resignFirstResponder()
    }

    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        delegate?.search(character: searchBar.text!)

        searchBar.resignFirstResponder()
    }
}

extension ListCharacterView: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        delegate?.describe(at: indexPath.row)
    }

    func collectionView(_ collectionView: UICollectionView, didEndDisplaying cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        if indexPath.row % 10 == 0 {
            if searchCharacter.text!.isEmpty {
                delegate?.fetch()
            }
        }
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, referenceSizeForFooterInSection section: Int) -> CGSize {
        return CGSize(width: 0.0, height: 50.0)
    }
}
