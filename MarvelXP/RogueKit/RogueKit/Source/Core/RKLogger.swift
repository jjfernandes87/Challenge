//
//  RKLogger.swift
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

public class RKLogger {
 
    @inline(__always) public static func log(request: URLRequest) {
        let components = NSURLComponents(string: request.url?.absoluteString ?? "")
        var message = "\n----------------------------------\n\(request.httpMethod ?? "") \(components?.path ?? "")?\(components?.query ?? "") HTTP/1.1\nHost: \(components?.host ?? "")\n"
        
        for (name, value) in request.allHTTPHeaderFields ?? [:] {
            message += "\(name): \(value)\n"
        }
        
        if let string = request.httpBody?.stringValue {
            message += "\(string)\n"
        }
        
        print("\(message)----------------------------------\n")
    }
    
    @inline(__always) public static func log(response: URLResponse?, url: URL, startTime: CFAbsoluteTime, data: Data?) {
        var message = "\n----------------------------------\nResponse: \(url.absoluteString)\n\((CFAbsoluteTimeGetCurrent() - startTime).humanReadable)\n\n"
        
        if let response = response as? HTTPURLResponse {
            for (name, value) in response.allHeaderFields {
                message += "\(name): \(value)\n"
            }
            
            if let string = data?.stringValue {
                message += "\(string)\n"
            }
        }
        
        print("\(message)----------------------------------\n")
    }
}
