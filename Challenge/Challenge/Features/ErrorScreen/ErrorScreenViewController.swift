import UIKit
import Reusable

class ErrorScreenViewController: UIViewController, StoryboardSceneBased {
    static var sceneStoryboard: UIStoryboard {
        return UIStoryboard(name: "Main", bundle: nil)
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        (view as? ErrorScreenView)?.delegate = self
    }

    func set(error: Error) {
        (view as? ErrorScreenView)?.error.text = error.localizedDescription
    }
}

extension ErrorScreenViewController: ErrorScreenViewDelegate {
    func close() {
        navigationController?.popViewController(animated: true)
        // dismiss(animated: true, completion: nil)
    }
}
