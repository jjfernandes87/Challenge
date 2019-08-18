import Foundation

public struct ComicSummary: Codable {
    public let name: String?
    public let resourceURI: String?

    enum CodingKeys: String, CodingKey {
        case name
        case resourceURI
    }
}
