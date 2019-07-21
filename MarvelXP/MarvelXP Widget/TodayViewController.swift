//
//  TodayViewController.swift
//  MarvelXP Widget
//
//  Created by Roger Sanoli on 21/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import UIKit
import NotificationCenter
import RogueKit

class TodayViewController: UIViewController, NCWidgetProviding {

    @IBOutlet private weak var image1: UIImageView!
    @IBOutlet private weak var image2: UIImageView!
    @IBOutlet private weak var image3: UIImageView!

    private var entities: [WidgetCharacterEntity]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //requestCharacters()
    }
    
    func widgetPerformUpdate(completionHandler: (@escaping (NCUpdateResult) -> Void)) {
        requestCharacters()
        completionHandler(NCUpdateResult.newData)
    }
    
    private func requestCharacters() {
        RogueKit.request(WidgetRepository.fetchCharacters) { [unowned self] (result: Result<WidgetBaseResponseEntity, Error>) in
            switch result {
            case let .success(response):
                self.entities = response.data?.results
                self.updateViews()
            case .failure(_):
                break
            }
        }
    }
    
    private func updateViews() {
        guard let characters = self.entities,
        characters.count > 2
        else { return }
        
        image1.download(characters[0].thumbnail?.getURLPath() ?? "")
        image2.download(characters[1].thumbnail?.getURLPath() ?? "")
        image3.download(characters[2].thumbnail?.getURLPath() ?? "")
    }
    
    @IBAction func characterPressed(_ sender: Any) {
        guard let button = sender as? UIButton,
        let characters = self.entities,
            characters.count > 2,
        let characterID = characters[button.tag].id,
            let url = URL(string: "character-detail:?characterID=\(characterID)")
            else { return }
        
        extensionContext?.open(url, completionHandler: nil)
    }
}
