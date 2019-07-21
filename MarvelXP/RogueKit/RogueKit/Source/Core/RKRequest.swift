//
//  RKRequest.swift
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

public enum RKRequestMethod: String {
    case delete = "DELETE",
    get = "GET",
    patch = "PATCH",
    post = "POST"
}

public final class RKRequest {
    let method: RKRequestMethod
    let path: String
    let timeout: TimeInterval?
    let body: Data?
    
    private init(method: RKRequestMethod, path: String, timeout: TimeInterval?) {
        self.method = method
        self.path = path
        self.timeout = timeout
        self.body = nil
    }
    
    private init<T: Entity>(method: RKRequestMethod, path: String, timeout: TimeInterval?, entity: T?) throws {
        self.method = method
        self.path = path
        self.timeout = timeout
        
        let encoder = JSONEncoder()
        encoder.keyEncodingStrategy = .convertToSnakeCase
        
        do {
            try body = encoder.encode(entity)
        } catch {
            throw RKError(.encodingFailed)
        }
    }
    
    public static func get(_ path: String, timeout: TimeInterval? = nil) -> RKRequest {
        return RKRequest(method: .get, path: path, timeout: timeout)
    }
    
    public static func post(_ path: String, timeout: TimeInterval? = nil) -> RKRequest {
        return RKRequest(method: .post, path: path, timeout: timeout)
    }

    public static func post<T: Entity>(_ path: String, entity: T, timeout: TimeInterval? = nil) throws -> RKRequest {
        return try RKRequest(method: .post, path: path, timeout: timeout, entity: entity)
    }

    public static func patch(_ path: String, timeout: TimeInterval? = nil) -> RKRequest {
        return RKRequest(method: .patch, path: path, timeout: timeout)
    }
    
    public static func patch<T: Entity>(_ path: String, entity: T, timeout: TimeInterval? = nil) throws -> RKRequest {
        return try RKRequest(method: .patch, path: path, timeout: timeout, entity: entity)
    }
    
    public static func delete(_ path: String, timeout: TimeInterval? = nil) -> RKRequest {
        return RKRequest(method: .delete, path: path, timeout: timeout)
    }
}
