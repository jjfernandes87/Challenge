import UIKit
import Marvel

protocol ListCharactersRouter: AnyObject {
    func present(error: Error)
    func routeToDescribe(character: Character)
}
