import Foundation

public struct CharacterDataWrapper: DataWrapper {
    public let status: String?
    public let data: CharacterDataContainer?

    enum CodingKeys: String, CodingKey {
        case data
        case status
    }
}
