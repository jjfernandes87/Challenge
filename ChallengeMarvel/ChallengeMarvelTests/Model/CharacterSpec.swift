//
//  CharacterSpec.swift
//  ChallengeMarvelTests
//
//  Created by Dynara Rico Oliveira on 09/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import Foundation
import Quick
import Nimble

@testable import ChallengeMarvel

class CharacterSpec: QuickSpec {
    override func spec() {
        describe("character") {

            var marvelObject: MarvelObject!
            var character: [Character]!
            var characterViewModel: CharacterViewModel!
            
            beforeEach {
                let bundle = Bundle(for: type(of: self))
                let mock = LoaderMock(file: "MarvelObject", in: bundle)
                marvelObject = mock?.map(to: MarvelObject.self)
                character = marvelObject.data?.data
                characterViewModel = CharacterViewModel(context: nil, character: character.first, managedObject: nil)
            }
            
            it("should be to create a marvelObject from json") {
                expect(marvelObject).toNot(beNil())
            }

            it("should be to load a character from marvelObject") {
                expect(character).toNot(beNil())
            }
            
            it("should be to load 20 character from marvelObject") {
                expect(character.count).to(be(20))
            }
            
            it("should be to load a characterViewModel") {
                expect(characterViewModel).toNot(beNil())
            }
        }
    }
}
