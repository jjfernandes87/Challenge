import Foundation

public struct JSONParser<T> {
    public init() {
    }
}

public extension JSONParser where T: Encodable {
    func encode(_ object: T) throws -> Data {
        return try JSONEncoder().encode(object)
    }
}

public extension JSONParser where T: Decodable {
    func decode(_ data: Data) throws -> T {
        let jsonDecoder = JSONDecoder()
        let dateFormatter = DateFormatter()

        dateFormatter.dateFormat = "yyyy-MM-dd"

        jsonDecoder.dateDecodingStrategy = JSONDecoder.DateDecodingStrategy.formatted(dateFormatter)

        return try jsonDecoder.decode(T.self, from: data)
    }
}
