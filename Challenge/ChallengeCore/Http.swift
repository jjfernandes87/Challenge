import Foundation

public enum HttpMethod: String {
    case get = "GET"
    case post = "POST"
}

public struct Http {
    let url: URL
    let body: Data?
    let method: HttpMethod
    let headers: [ String: String ]

    public init(url: String, body: Data? = nil, method: HttpMethod = HttpMethod.get, headers: [ String: String ] = [:]) {
        guard let url = URL(string: url) else {
            fatalError("URL could not be infered.")
        }

        self.url = url
        self.body = body
        self.method = method
        self.headers = headers
    }
}

public func request(http: Http, urlSession: URLSession = URLSession.shared, completion: @escaping (Result<Data, Error>) -> Void) {
    var urlRequest = URLRequest(url: http.url)

    urlRequest.httpBody = http.body
    urlRequest.httpMethod = http.method.rawValue
    urlRequest.allHTTPHeaderFields = http.headers

    urlSession.dataTask(with: urlRequest) { (data, _, error) in
        if let error = error {
            completion(Result.failure(error))
        }

        if let data = data {
            completion(Result.success(data))
        }
    }
    .resume()
}
