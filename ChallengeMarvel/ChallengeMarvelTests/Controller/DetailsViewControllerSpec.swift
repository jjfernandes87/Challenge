//
//  DetailsViewControllerSpec.swift
//  ChallengeMarvelTests
//
//  Created by Dynara Rico Oliveira on 09/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Quick
import Nimble

@testable import ChallengeMarvel

class DetailsViewControllerSpec: QuickSpec {
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    override func spec() {
        describe("DetailsViewController") {
            
            var controller: DetailsViewController!
            
            beforeEach {
                let bundle = Bundle(for: type(of: self))
                let mock = LoaderMock(file: "MarvelObject", in: bundle)
                let marvelObject = mock?.map(to: MarvelObject.self)
                
                
                let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
                controller = storyboard
                    .instantiateViewController(withIdentifier: DetailsViewController.storyboardIdentifier) as? DetailsViewController
                
                let marvelObjectViewModel = MarvelObjectViewModel(context: self.appDelegate.persistentContainer.newBackgroundContext(),
                                                                  marvelObject: marvelObject!)
                
                controller.characterViewModel = marvelObjectViewModel.characterViewModel.first
                
                let _ = controller.view
            }
            
            it("should have expected props setup") {
                controller.viewDidLoad()
                expect(controller.characterViewModel).toNot(beNil())
                expect(controller.detailsImageView).toNot(beNil())
                expect(controller.descriptionLabel).toNot(beNil())
                expect(controller.comicsCollectionView).toNot(beNil())
                expect(controller.seriesCollectionView).toNot(beNil())
            }
            
            it("should mark favorite character") {
                controller.viewDidLoad()
                let favorite = controller.characterViewModel?.favorite ?? false
                controller.setFavorite()
                expect(controller.characterViewModel?.favorite).to(be(!favorite))
            }
        }
    }
}
