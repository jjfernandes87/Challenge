import UIKit
import Reusable

open class Presenter<ViewType: UIView, Repository>: UIViewController, StoryboardSceneBased {
    public static var sceneStoryboard: UIStoryboard {
        return UIStoryboard(name: "Main", bundle: nil)
    }

    public var repository: Repository?

    public var viewType: ViewType {
        guard let viewType = view as? ViewType else {
            fatalError("view has to be \(ViewType.self)")
        }

        return viewType
    }
}
