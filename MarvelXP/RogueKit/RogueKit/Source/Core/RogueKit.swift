//
//  RogueKit.swift
//
//  Copyright (c) 2018 Roger dos Santos Oliveira
//
//  Permission is hereby granted, free of charge, to any person obtaining a copy
//  of this software and associated documentation files (the "Software"), to deal
//  in the Software without restriction, including without limitation the rights
//  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//  copies of the Software, and to permit persons to whom the Software is
//  furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//  THE SOFTWARE.
//

import Foundation
import UIKit
import MobileCoreServices

public typealias ResultCallback<T> = (_ result: Result<T, Error>) -> Void

public final class RogueKit {
    
    private static let session = URLSession(configuration: .default)
    private(set) static var headers = [String: String]()
    
    #if DEBUG
    public static var timeout: TimeInterval = 240
    #else
    public static var timeout: TimeInterval = 60
    #endif
    
    public static func setDefaultHeader(_ key: String, value: String) {
        headers[key] = value
    }
    
    @discardableResult public static func request<T: Entity>(_ repository: RKRepository, completion: @escaping ResultCallback<T>) -> URLSessionDataTask? {
        do {
            let request = try repository.createRequest()
            let urlPath = "\(repository.domain)\(request.path)"
            
            guard let url = URL(string: urlPath) else {
                completion(.failure(RKError(.invalidURL(url: urlPath))))
                return nil
            }
            
            return executeRequest(method: request.method, url: url, timeout: request.timeout, body: request.body, completion: { (result) in
                switch result {
                case let .success(data):
                    completion(decodeEntity(fromData: data))
                case let .failure(error):
                    completion(.failure(error))
                }
            })
            
        } catch {
            completion(.failure(error))
        }
        
        return nil
    }
    
    private static func createURLRequest(method: RKRequestMethod, url: URL, timeout: TimeInterval?, body: Data?) -> URLRequest {
        var request = URLRequest(url: url, cachePolicy: .useProtocolCachePolicy, timeoutInterval: timeout ?? RogueKit.timeout)
        
        request.httpShouldHandleCookies = false
        request.httpShouldUsePipelining = true
        request.httpMethod = method.rawValue
        
        if let body = body {
            request.httpBody = body
            request.setValue("application/json; charset=utf-8", forHTTPHeaderField: "Content-Type")
        }
        
        for (name, value) in RogueKit.headers {
            request.addValue(value, forHTTPHeaderField: name)
        }
        
        return request
    }
    
    private static func executeRequest(method: RKRequestMethod, url: URL, timeout: TimeInterval?, body: Data?, completion: @escaping ResultCallback<Data?>) -> URLSessionDataTask? {
        
        let request = createURLRequest(method: method, url: url, timeout: timeout, body: body)
        
        #if DEBUG
        let startTime = CFAbsoluteTimeGetCurrent()
        RKLogger.log(request: request)
        #endif
        
        let task = session.dataTask(with: request) { data, response, error in
            #if DEBUG
            RKLogger.log(response: response, url: url, startTime: startTime, data: data)
            #endif
            
            guard let response = response as? HTTPURLResponse else {
                if let error = error as NSError? {
                    completion(.failure(error))
                } else {
                    completion(.failure(RKError(.invalidRequest(message: error?.localizedDescription))))
                }
                
                return
            }
            
            if response.statusCode >= 200 && response.statusCode <= 299 {
                completion(.success(data))
            } else {
                completion(.failure(RKError(.serverError(code: response.statusCode, message: data?.stringValue))))
            }
        }
        
        task.priority = URLSessionTask.highPriority
        task.resume()
        
        return task
    }
    
    private static func decodeEntity<T: Entity>(fromData data: Data?) -> Result<T, Error> {
        guard let data = data else {
            return .failure(RKError(.invalidData))
        }
        
        let decoder = JSONDecoder()
        decoder.keyDecodingStrategy = .convertFromSnakeCase
        
        do {
            return .success(try decoder.decode(T.self, from: data))
        } catch let error as DecodingError {
            
            switch error {
            case let .dataCorrupted(context):
                return .failure(RKError(.decodeFailed(message: "\(context.debugDescription) in \(context.valuePath))")))
                
            case let .keyNotFound(key, context):
                return .failure(RKError(.decodeFailed(message: "Key \(key.stringValue) not found in \(context.valuePath))")))
                
            case let .typeMismatch(type, context):
                return .failure(RKError(.decodeFailed(message: "Type mismatch (\(String(describing: type))) in \(context.valuePath))")))
                
            case let .valueNotFound(type, context):
                return .failure(RKError(.decodeFailed(message: "Value not found for \(String(describing: type)) in \(context.valuePath))")))
                
            @unknown default:
                return .failure(error)
            }
        } catch let error {
            return .failure(error)
        }
    }
    
    @discardableResult public static func downloadImage(from URL: URL, completion: @escaping ResultCallback<UIImage>) -> URLSessionDataTask? {
        
        var request = URLRequest(url: URL, cachePolicy: .useProtocolCachePolicy, timeoutInterval: timeout)
        
        request.httpShouldHandleCookies = false
        request.httpShouldUsePipelining = true
        
        request.httpMethod = RKRequestMethod.get.rawValue
        
        #if DEBUG
        let startTime = CFAbsoluteTimeGetCurrent()
        #endif
        
        let task = session.dataTask(with: request) { data, response, error in
            #if DEBUG
            RKLogger.log(response: response, url: URL, startTime: startTime, data: nil)
            #endif
            
            guard let response = response as? HTTPURLResponse else {
                if let error = error as NSError? {
                    completion(.failure(error))
                } else {
                    completion(.failure(RKError(.invalidRequest(message: error?.localizedDescription))))
                }
                return
            }
            
            if response.statusCode >= 200 && response.statusCode <= 299 {
                if let data = data, let image = UIImage(data: data) {
                    completion(.success(image))
                } else {
                    completion(.failure(RKError(.decodeFailed(message: "Invalid image"))))
                }
            } else {
                completion(.failure(RKError(.serverError(code: response.statusCode, message: data?.stringValue))))
            }
        }
        
        task.resume()
        return task
    }
}
