import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    var appCoordinator: Coordinator?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        let context = UINavigationController()
        window = UIWindow(frame: UIScreen.main.bounds)

        if UIDevice.current.model.lowercased().contains("ipad") {
            appCoordinator = BiggestDeviceCoordinator(context: UITabBarController())
        } else {
            appCoordinator = SmallestDeviceCoordinator(context: UITabBarController())
        }

        window?.backgroundColor = UIColor.white
        window?.rootViewController = context

        context.isNavigationBarHidden = true
        context.pushViewController(appCoordinator?.context ?? UITabBarController(), animated: false)

        window?.makeKeyAndVisible()

        appCoordinator?.start()

        return true
    }

    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        let urlComponents = URLComponents(url: url, resolvingAgainstBaseURL: false)

        if let characterId = Int(urlComponents?.queryItems?.first?.value ?? "") {
            appCoordinator?.describe(characterId: characterId, context: window?.rootViewController as! UINavigationController)
        }

        return true
    }
}
