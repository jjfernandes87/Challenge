import Foundation

public protocol DataWrapper: Codable {
    associatedtype DataContainer

    var data: DataContainer? { get }
}
