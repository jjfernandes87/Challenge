//
//  DetailsCollectionViewCell.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 06/07/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import UIKit

class DetailsCollectionViewCell: UICollectionViewCell, Identifiable {

    @IBOutlet weak var containerView: UIView?
    @IBOutlet weak var shadowView: UIView?
    @IBOutlet weak var detailsImageView: UIImageView?
    @IBOutlet weak var detailsLabel: UILabel?
    
    var comic: ComicsItem? {
        didSet {
            detailsLabel?.text = comic?.name
            fetchData(urlString: comic?.resourceURI ?? "")
        }
    }
    
    var thumbnail: ThumbnailViewModel? {
        didSet {
            self.detailsImageView?.loadImage(withURL: thumbnail?.pathFull ?? "")
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        self.shadowView?.shadow()
        self.containerView?.round()
    }

    override func prepareForReuse() {
        super.prepareForReuse()
        detailsImageView?.image = UIImage()
        detailsLabel?.text = ""
    }
    
    func fetchData(urlString: String) {
        let marvelObjectManager = MarvelObjectManager()
        marvelObjectManager.fetchComicsEventsObject(urlString: urlString) { (result) in
            switch result() {
            case .success(let comicsEventObject):
                if let comicsEventsThumbnail = comicsEventObject.data.results.first?.thumbnail {
                    self.thumbnail = ThumbnailViewModel(thumbnail: comicsEventsThumbnail)
                }
            case .failure:
                self.detailsImageView?.image = UIImage()
            }
        }
    }
    
}
