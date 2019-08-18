import UIKit
import Marvel
import SDWebImage
import ChallengeCore

class DescribeCharacterPresenter: Presenter<DescribeCharacterView, DescribeCharacterRepositoryType> {
    weak var router: DescribeCharacterRouter?

    private var comicsDataSource: ResourcesDataSource?
    private var seriesDataSource: ResourcesDataSource?

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)

        navigationController?.isNavigationBarHidden = false
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)

        navigationController?.isNavigationBarHidden = true
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        if let character = repository?.character {
            let viewModel = CharacterViewModel(character: character, isFavorited: repository?.isFavorited(character: character) ?? false)

            viewType.name.text = viewModel.name
            viewType.descriptionCharacter.text = viewModel.description
            viewType.image.sd_setImage(with: URL(string: viewModel.thumbnail), completed: nil)
        }

        repository?.delegate = self

        repository?.loadComics()
        repository?.loadSeries()
    }
}

extension DescribeCharacterPresenter: DescribeCharacterRepositoryTypeDelegate {
    func `catch`(error: Error) {
        DispatchQueue.main.async {
            self.router?.present(error: error)
        }
    }

    func describeCharacter(didFetch series: [Series]) {
        if series.count == 0 {
            return
        }

        seriesDataSource = ResourcesDataSource(items:
            series.map { series in CharacterResourceViewModel(series: series) }
        )

        DispatchQueue.main.async {
            self.viewType.seriesCollection.resourcesList.dataSource = self.seriesDataSource
        }
    }

    func describeCharacter(didFetch comics: [Comics]) {
        if comics.count == 0 {
            return
        }

        comicsDataSource = ResourcesDataSource(items:
            comics.map { comics in CharacterResourceViewModel(comics: comics) }
        )

        DispatchQueue.main.async {
            self.viewType.comicsCollection.resourcesList.dataSource = self.comicsDataSource
        }
    }
}
