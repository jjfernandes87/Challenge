//
//  CharactersCollectionViewControllerSpec.swift
//  ChallengeMarvelTests
//
//  Created by Dynara Rico Oliveira on 09/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Quick
import Nimble
import CoreData

@testable import ChallengeMarvel

struct MarvelObjectManagerMock: MarvelObjectCalls {
    let marvelObject: MarvelObject
    
    func fetchMarvelObject(offset: Int, nameStartsWith: String, limit: Int, completion: @escaping MarvelObjectCallback) {
        completion { Result.success(self.marvelObject) }
    }
    
    func fetchMarvelObjectDatabase(context: NSManagedObjectContext, nameStartsWith: String, completion: @escaping (Result<[NSManagedObject]>) -> Void) {
        let managedObject = self.marvelObject.data?.data.compactMap({ $0.managedObject(in: context) as NSManagedObject })
        completion(Result.success(managedObject!))
    }
    
}

class CharactersCollectionViewControllerSpec: QuickSpec {
    override func spec() {
        describe("CharactersCollectionViewController") {
            
            var controller: CharactersCollectionViewController!
            var marvelObjectManager: MarvelObjectCalls!
            
            beforeEach {
                let bundle = Bundle(for: type(of: self))
                let mock = LoaderMock(file: "MarvelObject", in: bundle)
                let marvelObject = mock?.map(to: MarvelObject.self)
                marvelObjectManager = MarvelObjectManagerMock(marvelObject: marvelObject!)
                
                let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
                controller = storyboard
                    .instantiateViewController(withIdentifier: CharactersCollectionViewController.storyboardIdentifier) as? CharactersCollectionViewController
                
                controller.marvelObjectManager = marvelObjectManager
                controller.characterSelectionDelegate = self
                
                let _ = controller.view
            }
            
            it("should have expected props setup") {
                controller.viewDidLoad()
                expect(controller.marvelObjectManager).toNot(beNil())
                expect(controller.tabBarItemSelected).toNot(beNil())
                expect(controller.characterSelectionDelegate).toNot(beNil())
                expect(controller.searchBar).toNot(beNil())
                expect(controller.refreshControl).toNot(beNil())
                expect(controller.collectionView).toNot(beNil())
            }
            
            it("should fetch 20 characters") {
                controller.viewDidLoad()
                controller.fetchData()
                let count = controller.characterViewModel.count
                expect(count).toEventually(equal(20))
            }
            
            it("should be to display tab bar") {
                controller.viewDidLoad()
                expect(controller.tabBarController?.tabBar.isHidden).to(beFalsy())
            }
            
            context("didSelectCharacter") {
                beforeEach {
                    let bundle = Bundle(for: type(of: self))
                    let mock = LoaderMock(file: "MarvelObject", in: bundle)
                    let marvelObject = mock?.map(to: MarvelObject.self)
                    marvelObjectManager = MarvelObjectManagerMock(marvelObject: marvelObject!)
                    
                    let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
                    controller = storyboard
                        .instantiateViewController(withIdentifier: CharactersCollectionViewController.storyboardIdentifier) as? CharactersCollectionViewController
                    
                    controller.marvelObjectManager = marvelObjectManager
                    controller.characterSelectionDelegate = self
                    
                    let _ = controller.view
                    
                    controller.viewDidLoad()
                }
                
                it("should load characterSelectionDelegate") {
                    controller.fetchData()
                    let indexPath = IndexPath(row: 0, section: 0)
                    controller.didSelectCharacter(at: indexPath)
                    expect(controller.characterSelectionDelegate).toNot(beNil())
                }
                
                it("should clear list character") {
                    controller.viewDidLoad()
                    controller.fetchData()
                    controller.clearList()
                    let count = controller.characterViewModel.count
                    expect(count).toEventually(equal(0))
                }
            }
            
        }
    }
}

extension CharactersCollectionViewControllerSpec: CharacterSelectionDelegate {
    func characterSelected(_ character: CharacterViewModel) {
        _ = character
    }
}
