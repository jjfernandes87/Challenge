import Foundation

public protocol DataContainer: Codable {
    associatedtype Model

    var limit: Int? { get }
    var total: Int? { get }
    var count: Int? { get }
    var offset: Int? { get }
    var results: [Model]? { get }
}
