import Foundation

public struct Storage {
    let fileManager: FileManager

    public init(fileManager: FileManager) {
        self.fileManager = fileManager
    }

    public func createDirectory(name: String) throws {
        try fileManager.createDirectory(atPath: filename(for: name).path, withIntermediateDirectories: true, attributes: nil)
    }

    public func fileExists(at key: String) -> Bool {
        return fileManager.fileExists(atPath: filename(for: key).path)
    }

    public func content(of: String) throws -> [String] {
        return try fileManager.contentsOfDirectory(atPath: filename(for: of).path)
    }

    public func store(data: Data, at key: String) throws {
        try data.write(to: filename(for: key))
    }

    public func retrieve(from key: String) throws -> Data {
        return try Data(contentsOf: filename(for: key))
    }

    public func delete(at key: String) throws {
        try fileManager.removeItem(at: filename(for: key))
    }

    private func filename(for key: String) -> URL {
        return fileManager.urls(for: FileManager.SearchPathDirectory.documentDirectory, in: FileManager.SearchPathDomainMask.userDomainMask)[0].appendingPathComponent(key, isDirectory: false)
    }
}
