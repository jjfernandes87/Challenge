import UIKit

protocol ErrorScreenViewDelegate: AnyObject {
    func close()
}

class ErrorScreenView: UIView {
    weak var delegate: ErrorScreenViewDelegate?

    @IBOutlet weak var error: UILabel!

    @IBAction func touchupinFrom(sender: UIButton) {
        delegate?.close()
    }
}
