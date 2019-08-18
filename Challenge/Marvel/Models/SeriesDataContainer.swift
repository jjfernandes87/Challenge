import Foundation

public struct SeriesDataContainer: DataContainer {
    public var limit: Int?
    public var total: Int?
    public var count: Int?
    public var offset: Int?
    public var results: [Series]?

    enum CodingKeys: String, CodingKey {
        case limit
        case total
        case count
        case offset
        case results
    }
}
