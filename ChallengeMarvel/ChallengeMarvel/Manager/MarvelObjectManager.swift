//
//  MarvelObjectManager.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//


import Foundation
import CoreData

protocol MarvelObjectCalls {
    func fetchMarvelObject(offset: Int, nameStartsWith: String, limit: Int, completion: @escaping MarvelObjectCallback)
    func fetchMarvelObjectDatabase(context: NSManagedObjectContext, nameStartsWith: String,
                                   completion: @escaping (Result<[NSManagedObject]>) -> Void)
}

class MarvelObjectManager: BaseManager, MarvelObjectCalls {
    
    private lazy var marvelObjectBusiness: MarvelObjectBusiness = {
        return MarvelObjectBusiness()
    }()
    
    public func fetchMarvelObject(offset: Int, nameStartsWith: String, limit: Int,
                                  completion: @escaping MarvelObjectCallback) {
        addOperation {
            self.marvelObjectBusiness.fetchMarvelObject(offset: offset,
                                                        nameStartsWith: nameStartsWith,
                                                        limit: limit,
                                                        completion: { (marvelObject) in
                                                            OperationQueue.main.addOperation {
                                                                completion(marvelObject)
                                                            }
            })
        }
    }
    
    public func fetchComicsEventsObject(urlString: String, completion: @escaping ComicsEventsObjectCallback) {
        addOperation {
            self.marvelObjectBusiness.fetchComicsEventsObject(urlString: urlString,
                                                              completion: { (comicsEventsObject) in
                                                                OperationQueue.main.addOperation {
                                                                    completion(comicsEventsObject)
                                                                }
            })
        }
    }
    
    public func fetchCharacterObject(id: Int, completion: @escaping MarvelObjectCallback) {
        addOperation {
            self.marvelObjectBusiness.fetchCharacterObject(id: id,
                                                           completion: { (marvelObject) in
                                                            OperationQueue.main.addOperation {
                                                                completion(marvelObject)
                                                            }
            })
        }
    }
    
    public func fetchMarvelObjectDatabase(context: NSManagedObjectContext, nameStartsWith: String,
                                          completion: @escaping (Result<[NSManagedObject]>) -> Void) {
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Character")
        request.sortDescriptors = [NSSortDescriptor(key: "name", ascending: true)]
        if nameStartsWith.count > 0 {
            request.predicate = NSPredicate(format: "(name BEGINSWITH[c] %@)" , nameStartsWith)
        }
        request.returnsObjectsAsFaults = false
        
        do {
            let result = try context.fetch(request)
            let managedObject = result.compactMap({ return $0 as? NSManagedObject })
            completion(.success(managedObject))
        } catch {
            completion(.failure)
        }
        
    }
    
}
