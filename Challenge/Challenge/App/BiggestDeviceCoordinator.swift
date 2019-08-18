import UIKit
import Marvel

class BiggestDeviceCoordinator: Coordinator {
    var context: UITabBarController
    var splitListCharacters: UISplitViewController
    var splitFavoritesCharacters: UISplitViewController

    required init(context: UITabBarController) {
        self.context = context
        self.splitListCharacters = UISplitViewController()
        self.splitFavoritesCharacters = UISplitViewController()
    }

    func start() {
        setupList()
        setupFavorites()

        context.setViewControllers([ splitListCharacters, splitFavoritesCharacters ], animated: false)
    }

    func setupList() {
        let presenter = ListCharactersPresenter.instantiate()

        presenter.router = self
        presenter.title = "List"
        presenter.repository = RemoteCharactersRepository()

        splitListCharacters.viewControllers = [ UINavigationController(rootViewController: presenter), UINavigationController() ]
        splitListCharacters.tabBarItem = UITabBarItem(tabBarSystemItem: UITabBarItem.SystemItem.mostRecent, tag: 1)
    }

    func setupFavorites() {
        let presenter = ListCharactersPresenter.instantiate()

        presenter.router = self
        presenter.title = "Favorites"
        presenter.repository = LocalCharactersRepository()

        splitFavoritesCharacters.viewControllers = [ UINavigationController(rootViewController: presenter), UINavigationController() ]
        splitFavoritesCharacters.tabBarItem = UITabBarItem(tabBarSystemItem: UITabBarItem.SystemItem.favorites, tag: 1)
    }
}

extension BiggestDeviceCoordinator: ListCharactersRouter {
    func routeToDescribe(character: Character) {
        let presenter = DescribeCharacterPresenter.instantiate()

        presenter.router = self
        presenter.repository = DescribeCharacterRepository(character: character)

        if self.context.selectedIndex == 0 {
            (splitListCharacters.viewControllers[1] as! UINavigationController).setViewControllers([ presenter ], animated: false)
        } else {
            (splitFavoritesCharacters.viewControllers[1] as! UINavigationController).setViewControllers([ presenter ], animated: false)
        }
    }
}

extension BiggestDeviceCoordinator: DescribeCharacterRouter {
}
