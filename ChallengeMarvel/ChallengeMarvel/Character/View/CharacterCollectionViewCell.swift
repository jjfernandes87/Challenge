//
//  CharacterCollectionViewCell.swift
//  ChallengeMarvel
//
//  Created by Dynara Rico Oliveira on 30/06/19.
//  Copyright © 2019 Dynara Rico Oliveira. All rights reserved.
//

import UIKit

protocol CharacterCollectionViewCellDelegate: class {
    func reloadData()
}

class CharacterCollectionViewCell: UICollectionViewCell, Identifiable {
    
    @IBOutlet weak var characterNameLabel: UILabel?
    @IBOutlet weak var characterImageView: UIImageView?
    @IBOutlet weak var containerView: UIView?
    @IBOutlet weak var favoriteButton: UIButton?
    @IBOutlet weak var shadowView: UIView?
    
    weak var delegate: CharacterCollectionViewCellDelegate?
    let appDelegate = UIApplication.shared.delegate as? AppDelegate
    
    var characterViewModel: CharacterViewModel? {
        didSet {
            characterNameLabel?.text = characterViewModel?.name
            if characterViewModel?.image != nil {
                characterImageView?.image = characterViewModel?.image
            } else {
                characterImageView?.loadImage(withURL: characterViewModel?.thumbnailViewModel?.pathFull ?? "")
            }
            favoriteButton?.tintColor = characterViewModel?.favorite ?? false ? .orange : .lightGray
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        self.containerView?.round()
        self.shadowView?.shadow()
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        characterNameLabel?.text = ""
        characterImageView?.image = UIImage()
    }
    
    @IBAction func favoriteButtonClick(_ sender: Any) {
        characterViewModel?.save {
            self.favoriteButton?.tintColor = self.favoriteButton?.tintColor == .lightGray ? .orange : .lightGray
            self.delegate?.reloadData()
        }
    }
}
