import Foundation

public struct Image: Codable {
    public let path: String?
    public let `extension`: String?

    enum CodingKeys: String, CodingKey {
        case path
        case `extension` = "extension"
    }
}
