import Foundation
import ChallengeCore

private let environment = Environment(publicKey: "b84a90ac720fe75f2493ccb28f4e7051", privateKey: "81d4a002dc4ee76a435a30d761f6f9dbc9903347")

private func authParams() -> [URLQueryItem] {
    return authParams(environment: environment)
}

public func fetchCharacter(id: Int, limit: Int, offset: Int, completion: @escaping (Result<CharacterDataWrapper, Error>) -> Void) {
    var urlComponents = URLComponents()

    urlComponents.scheme = "https"
    urlComponents.host = "gateway.marvel.com"
    urlComponents.path = "/v1/public/characters/\(id)"
    urlComponents.queryItems = [
        URLQueryItem(name: "orderBy", value: "name"),
        URLQueryItem(name: "limit", value: "\(limit)"),
        URLQueryItem(name: "offset", value: "\(offset)")
    ] + authParams()

    let http = Http(url: urlComponents.string!)
    let jsonParser = JSONParser<CharacterDataWrapper>()

    request(http: http) { (result) in
        switch result {
        case .failure(let error):
            completion(Result.failure(error))
        case .success(let data):
            do {
                completion(Result.success(try jsonParser.decode(data)))
            } catch {
                completion(Result.failure(error))
            }
        }
    }
}

public func fetchSeries(characterId: Int, limit: Int, offset: Int, completion: @escaping (Result<SeriesDataWrapper, Error>) -> Void) {
    var urlComponents = URLComponents()

    urlComponents.scheme = "https"
    urlComponents.host = "gateway.marvel.com"
    urlComponents.path = "/v1/public/characters/\(characterId)/series"
    urlComponents.queryItems = [
        URLQueryItem(name: "orderBy", value: "title"),
        URLQueryItem(name: "limit", value: "\(limit)"),
        URLQueryItem(name: "offset", value: "\(offset)")
    ] + authParams()

    let http = Http(url: urlComponents.string!)
    let jsonParser = JSONParser<SeriesDataWrapper>()

    request(http: http) { (result) in
        switch result {
        case .failure(let error):
            completion(Result.failure(error))
        case .success(let data):
            do {
                completion(Result.success(try jsonParser.decode(data)))
            } catch {
                completion(Result.failure(error))
            }
        }
    }
}

public func fetchComics(characterId: Int, limit: Int, offset: Int, completion: @escaping (Result<ComicsDataWrapper, Error>) -> Void) {
    var urlComponents = URLComponents()

    urlComponents.scheme = "https"
    urlComponents.host = "gateway.marvel.com"
    urlComponents.path = "/v1/public/characters/\(characterId)/comics"
    urlComponents.queryItems = [
        URLQueryItem(name: "orderBy", value: "title"),
        URLQueryItem(name: "limit", value: "\(limit)"),
        URLQueryItem(name: "offset", value: "\(offset)")
    ] + authParams()

    let http = Http(url: urlComponents.string!)
    let jsonParser = JSONParser<ComicsDataWrapper>()

    request(http: http) { (result) in
        switch result {
        case .failure(let error):
            completion(Result.failure(error))
        case .success(let data):
            do {
                completion(Result.success(try jsonParser.decode(data)))
            } catch {
                completion(Result.failure(error))
            }
        }
    }
}

public func fetchCharacters(limit: Int, offset: Int, completion: @escaping (Result<CharacterDataWrapper, Error>) -> Void) {
    var urlComponents = URLComponents()

    urlComponents.scheme = "https"
    urlComponents.host = "gateway.marvel.com"
    urlComponents.path = "/v1/public/characters"
    urlComponents.queryItems = [
        URLQueryItem(name: "orderBy", value: "name"),
        URLQueryItem(name: "limit", value: "\(limit)"),
        URLQueryItem(name: "offset", value: "\(offset)")
    ] + authParams()

    let http = Http(url: urlComponents.string!)
    let jsonParser = JSONParser<CharacterDataWrapper>()

    request(http: http) { (result) in
        switch result {
        case .failure(let error):
            completion(Result.failure(error))
        case .success(let data):
            do {
                completion(Result.success(try jsonParser.decode(data)))
            } catch {
                completion(Result.failure(error))
            }
        }
    }
}

public func searchCharacter(name: String, limit: Int, offset: Int, completion: @escaping (Result<CharacterDataWrapper, Error>) -> Void) {
    var urlComponents = URLComponents()

    urlComponents.scheme = "https"
    urlComponents.host = "gateway.marvel.com"
    urlComponents.path = "/v1/public/characters"
    urlComponents.queryItems = [
        URLQueryItem(name: "nameStartsWith", value: name),
        URLQueryItem(name: "orderBy", value: "name"),
        URLQueryItem(name: "limit", value: "\(limit)"),
        URLQueryItem(name: "offset", value: "\(offset)")
    ] + authParams()

    let http = Http(url: urlComponents.string!)
    let jsonParser = JSONParser<CharacterDataWrapper>()

    request(http: http) { (result) in
        switch result {
        case .failure(let error):
            completion(Result.failure(error))
        case .success(let data):
            do {
                completion(Result.success(try jsonParser.decode(data)))
            } catch {
                completion(Result.failure(error))
            }
        }
    }
}
