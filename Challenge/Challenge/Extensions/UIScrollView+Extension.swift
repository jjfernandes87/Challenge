import UIKit

extension UIScrollView {
    func isNearBottomEdge(edgeOffset: CGFloat = 50.0) -> Bool {
        return contentOffset.y + frame.size.height + edgeOffset > contentSize.height
    }
}
