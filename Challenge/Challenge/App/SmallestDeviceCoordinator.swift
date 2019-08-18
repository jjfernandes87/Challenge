import UIKit
import Marvel

class SmallestDeviceCoordinator: Coordinator {
    var context: UITabBarController

    required init(context: UITabBarController) {
        self.context = context

        context.viewControllers = []
    }

    func start() {
        context.setViewControllers([ setupList(), setupFavorites() ], animated: true)
    }

    func setupList() -> UINavigationController {
        let presenter = ListCharactersPresenter.instantiate()
        let navigationController = UINavigationController(rootViewController: presenter)

        presenter.router = self
        presenter.title = "List"
        presenter.repository = RemoteCharactersRepository()
        presenter.tabBarItem = UITabBarItem(tabBarSystemItem: UITabBarItem.SystemItem.mostRecent, tag: 0)

        navigationController.title = "List"

        return navigationController
    }

    func setupFavorites() -> UINavigationController {
        let presenter = ListCharactersPresenter.instantiate()
        let navigationController = UINavigationController(rootViewController: presenter)

        presenter.router = self
        presenter.title = "Favorites"
        presenter.repository = LocalCharactersRepository()
        presenter.tabBarItem = UITabBarItem(tabBarSystemItem: UITabBarItem.SystemItem.favorites, tag: 1)

        navigationController.title = "Favorites"

        return navigationController
    }
}

extension SmallestDeviceCoordinator: ListCharactersRouter {
    func routeToDescribe(character: Character) {
        let presenter = DescribeCharacterPresenter.instantiate()

        presenter.router = self
        presenter.repository = DescribeCharacterRepository(character: character)

        (context.viewControllers?[context.selectedIndex] as? UINavigationController)?.pushViewController(presenter, animated: true)
    }
}

extension SmallestDeviceCoordinator: DescribeCharacterRouter {
}
