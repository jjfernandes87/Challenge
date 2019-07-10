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
        describe("marvel object") {
            
            var character: Marvel.Character!
            
            beforeEach {
                let testBundle = Bundle(for: type(of: self))
                let mockLoader = MockLoader(file: "character", in: testBundle)
                character = mockLoader?.map(to: Character.self)
            }
            
            it("should be able to create a chracter from json") {
                expect(character).toNot(beNil())
            }
            
            it("should have a thumbImage") {
                expect(character.thumImage).toNot(beNil())
            }
            
        }
    }
}
