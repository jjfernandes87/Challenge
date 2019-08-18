import Foundation

public struct ComicsDataWrapper: DataWrapper {
    public var data: ComicsDataContainer?

    enum CodingKeys: String, CodingKey {
        case data
    }
}
