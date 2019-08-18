import Foundation

public struct Series: Codable {
    public var id: Int?
    public var title: String?
    public var thumbnail: Image?

    public init(id: Int?, title: String?, thumbnail: Image?) {
        self.id = id
        self.title = title
        self.thumbnail = thumbnail
    }

    enum CodingKeys: String, CodingKey {
        case id
        case title
        case thumbnail
    }
}
