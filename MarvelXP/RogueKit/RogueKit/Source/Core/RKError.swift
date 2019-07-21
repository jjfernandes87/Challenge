//
//  RKError.swift
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

public enum RKErrorType {
    case invalidData,
    invalidRequest(message: String?),
    encodingFailed,
    decodeFailed(message: String),
    serverError(code: Int, message: String?),
    invalidURL(url: String),
    unknow
}

public struct RKError: Error {
    public let errorType: RKErrorType
    
    init(_ errorType: RKErrorType) {
        self.errorType = errorType
    }
    
    public var localizedDescription: String {
        var errorMessage = ""
        
        switch errorType {
        case .invalidData:
            errorMessage = "Invalid data when decoding"
        case let .invalidRequest(message):
            errorMessage = message ?? "Unknow Error"
        case let .decodeFailed(message):
            errorMessage = message
        case .encodingFailed:
            errorMessage = "Invalid data when encoding"
        case let .serverError(code, message):
            errorMessage = "Server Error Code [\(code)]: \(message ?? "Unknow Error")"
        case let .invalidURL(url):
            errorMessage = "Invalid URL: \(url)"
        case .unknow:
            errorMessage = "Unknow Error"
        }
        
        return errorMessage
    }
}

