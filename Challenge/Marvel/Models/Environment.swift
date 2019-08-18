import Foundation

public struct Environment {
    public let publicKey: String
    public let privateKey: String

    public init(publicKey: String, privateKey: String) {
        self.publicKey = publicKey
        self.privateKey = privateKey
    }
}

public func authParams(environment: Environment) -> [URLQueryItem] {
    let ts = Int(Date().timeIntervalSince1970)

    return [
        URLQueryItem(name: "ts", value: "\(ts)"),
        URLQueryItem(name: "apikey", value: environment.publicKey),
        URLQueryItem(name: "hash", value: md5(string: "\(ts)\(environment.privateKey)\(environment.publicKey)"))
    ]
}
