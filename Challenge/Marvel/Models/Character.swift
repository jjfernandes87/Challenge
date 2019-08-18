import Foundation

public struct Character: Codable {
    public let id: Int?
    public let name: String?
    public let thumbnail: Image?
    public let description: String?

    public init(id: Int?, name: String?, thumbnail: Image?, description: String?) {
        self.id = id
        self.name = name
        self.thumbnail = thumbnail
        self.description = description
    }

    enum CodingKeys: String, CodingKey {
        case id
        case name
        case thumbnail
        case description = "description"
    }
}
