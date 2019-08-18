import UIKit
import XCTest
import Nimble
@testable import Challenge

class CoordinatorTests: XCTestCase {
    var appCoordinator: SmallestDeviceCoordinator!

    override func setUp() {
        appCoordinator = SmallestDeviceCoordinator(context: UITabBarController())
    }

    func testStart() {
        appCoordinator.start()

        expect(self.appCoordinator.context.viewControllers!.count).to(equal(2))
    }

    override func tearDown() {
        appCoordinator = nil
    }

}
