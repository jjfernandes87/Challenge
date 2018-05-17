//
//  FavoritesCollectionViewCell.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 14/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import UIKit
import SQLite3

protocol GetIndexBtnFromCollectionCellFavoritesDelegate {
    func GetIndexFromCellToController(cell: FavoritesCollectionViewCell)
}

class FavoritesCollectionViewCell: UICollectionViewCell {
    
    @IBOutlet weak var favoritesFavoriteBtn: UIButton!
    @IBOutlet weak var favoritesImage: UIImageView!
    @IBOutlet weak var favoritesDescription: UILabel!
    
    let pathArquivo = (NSHomeDirectory() as NSString).appendingPathComponent("Documents/arquivo.sqlite")
    var dataBase : OpaquePointer? = nil
    
    var delegate: GetIndexBtnFromCollectionCellFavoritesDelegate?
    
    @IBAction func GetIndexFromCellToController(_ sender: UIButton) {
        if let _ = delegate {
            delegate?.GetIndexFromCellToController(cell: self)
        }
    }
    var object : DataBaseObject? {
        didSet {
            if let data = object {
                
                favoritesImage.layer.cornerRadius = 10
                
                favoritesDescription.text = data.name

                    if let path = data.extensionThumb {
                            
                            self.favoritesImage.image = nil
                            
                            if let imageFromCache = Utils.imageCache.object(forKey: path as AnyObject) as? UIImage {
                                favoritesImage.image = imageFromCache
                                favoritesFavoriteBtn.setImage(#imageLiteral(resourceName: "favoritesSelected"), for: .normal)
                            } else {
                                favoritesImage.image = #imageLiteral(resourceName: "defaultImage")
                            }
                        }
                    }
                }
            }
        }

