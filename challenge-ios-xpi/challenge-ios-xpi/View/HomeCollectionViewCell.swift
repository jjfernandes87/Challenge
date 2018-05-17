//
//  HomeCollectionViewCell.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 14/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import UIKit
import SQLite3

protocol GetIndexBtnFromCollectionCellHomeDelegate {
    func GetIndexFromCellToController(cell: HomeCollectionViewCell)
}

class HomeCollectionViewCell: UICollectionViewCell {
    
    @IBOutlet weak var homeFavoriteBtn: UIButton!
    @IBOutlet weak var homeImage: UIImageView!
    @IBOutlet weak var HomeDescription: UILabel!
    
    let pathArquivo = (NSHomeDirectory() as NSString).appendingPathComponent("Documents/arquivo.sqlite")
    var dataBase : OpaquePointer? = nil
    
    var delegate: GetIndexBtnFromCollectionCellHomeDelegate?
    
    @IBAction func GetIndexFromCellToController(_ sender: UIButton) {        
        if let _ = delegate {
            delegate?.GetIndexFromCellToController(cell: self)
        }
    }
    
    var object : DataObject? {
        didSet {
            
            homeImage.layer.cornerRadius = 10
            homeImage.contentMode = .scaleAspectFit
            
            if let data = object {
                
                HomeDescription.text = data.name
                
                if let thumbnailData = data.thumbnail {
                    
                    if let path = thumbnailData.path {
                        
                        if let extensionThumb = thumbnailData.extensionThumb {
                            
                            let thumbPath = "\(path).\(extensionThumb)"
                            
                            self.homeImage.image = nil
                            
                            if let imageFromCache = Utils.imageCache.object(forKey: thumbPath as AnyObject) as? UIImage {
                                homeImage.image = imageFromCache
                            } else {
                                homeImage.image = #imageLiteral(resourceName: "favoritesUnselected")
                            }
                        }
                    }
                }
                
                if (FileManager.default.fileExists(atPath: pathArquivo)) {
                    
                    if(sqlite3_open(pathArquivo, &dataBase) == SQLITE_OK)  {
                        
                        if let idFromService = object?.id {
                            let comandoCheckIfIsFavoriteOrNot = "SELECT * from MyHeros WHERE Favorite = 'true' AND IDFromService = '\(idFromService)'"
                            
                            if(sqlite3_exec(dataBase, comandoCheckIfIsFavoriteOrNot, nil, nil, nil)) == SQLITE_OK  {
                                
                                var resultado : OpaquePointer? = nil
                                
                                if (sqlite3_prepare_v2(dataBase, comandoCheckIfIsFavoriteOrNot, -1, &resultado, nil) == SQLITE_OK) {
                                    
                                    if sqlite3_step(resultado) == SQLITE_ROW {

                                        homeFavoriteBtn.setImage(#imageLiteral(resourceName: "favoritesSelected"), for: .normal)
                                    }else{
                                        homeFavoriteBtn.setImage(#imageLiteral(resourceName: "favoritesUnselected"), for: .normal)
                                    }
                                    sqlite3_finalize(resultado)
                                }
                            }else{
                                print("SQL Select Error")
                            }
                        }
                    }else{
                        print ("Database opened error")
                    }
                }
            }
        }
    }
}
