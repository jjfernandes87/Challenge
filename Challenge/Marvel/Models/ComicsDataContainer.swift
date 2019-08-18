import Foundation

public struct ComicsDataContainer: DataContainer {
    public var limit: Int?
    public var total: Int?
    public var count: Int?
    public var offset: Int?
    public var results: [Comics]?

    enum CodingKeys: String, CodingKey {
        case limit
        case total
        case count
        case offset
        case results
    }
}
