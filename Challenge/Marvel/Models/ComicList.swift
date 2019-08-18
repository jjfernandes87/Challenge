import Foundation

public struct ComicList: Codable {
    public let items: [ComicSummary]

    enum CodingKeys: String, CodingKey {
        case items
    }
}
