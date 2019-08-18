import Foundation

public struct SeriesDataWrapper: DataWrapper {
    public var data: SeriesDataContainer?

    enum CodingKeys: String, CodingKey {
        case data
    }
}
