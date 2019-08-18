import UIKit
import Marvel
import NotificationCenter

class SimpleListCharactersPresenter: UIViewController, NCWidgetProviding {
    private var characters: [Character]!
    private var dataSource: SimpleListCharactersDataSource?

    var viewType: SimpleListCharactersView {
        return view as! SimpleListCharactersView
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        viewType.delegate = self

        extensionContext?.widgetLargestAvailableDisplayMode = NCWidgetDisplayMode.expanded

        fetchCharacters(limit: 3, offset: 0) { (result) in
            switch result {
            case .success(let wrapper):
                DispatchQueue.main.async {
                    self.characters = wrapper.data?.results ?? []
                    self.dataSource = SimpleListCharactersDataSource(items: self.characters.map { character in CharacterViewModel(character: character) })
                    self.viewType.charactersList.dataSource = self.dataSource
                    self.viewType.charactersList.reloadData()
                }
            default: break
            }
        }
    }

    func widgetPerformUpdate(com nHandler: @escaping (NCUpdateResult) -> Void) {
        nHandler(NCUpdateResult.newData)
    }

    func widgetActiveDisplayModeDidChange(_ activeDisplayMode: NCWidgetDisplayMode, withMaximumSize maxSize: CGSize) {
        if activeDisplayMode == NCWidgetDisplayMode.expanded {
            preferredContentSize = CGSize(width: 0.0, height: 240.0)
        } else {
            preferredContentSize = CGSize(width: 0.0, height: 180.0)
        }
    }
}

extension SimpleListCharactersPresenter: SimpleListCharactersViewDelegate {
    func describe(character at: Int) {
        extensionContext?.open(URL(string: "challenge://?characterId=\(characters[at].id ?? 0)")!, completionHandler: nil)
    }
}
