import UIKit
import Marvel

protocol Coordinator {
    var context: UITabBarController { get }

    init(context: UITabBarController)

    func start()
}

extension Coordinator {
    func present(error: Error) {
        if context.navigationController?.topViewController is ErrorScreenViewController? {
            return
        }

        let view = ErrorScreenViewController.instantiate()
        view.set(error: error)

        context.show(view, sender: nil)
    }

    func describe(characterId: Int, context: UINavigationController) {
        let presenter = DescribeCharacterPresenter.instantiate()

        fetchCharacter(id: characterId, limit: 1, offset: 0) { (result) in
            switch result {
            case Result.success(let wrapper):
                if let character = wrapper.data?.results?[0] {
                    DispatchQueue.main.async {
                        presenter.repository = DescribeCharacterRepository(character: character)
                        context.show(presenter, sender: nil)
                    }
                }
            case Result.failure(let error):
                DispatchQueue.main.async {
                    self.present(error: error)
                }
            }
        }
    }
}
