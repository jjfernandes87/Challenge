//
//  FavoritesViewController.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 14/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import UIKit
import SQLite3

class FavoritesViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource, GetIndexBtnFromCollectionCellFavoritesDelegate {
    
    @IBOutlet weak var favoritesCollectionView: UICollectionView!
    
    let pathArquivo = (NSHomeDirectory() as NSString).appendingPathComponent("Documents/arquivo.sqlite")
    var dataBase : OpaquePointer?
    var resultado : OpaquePointer?
    var arrayFromDb : [[String:AnyObject]] = []
    
    @IBOutlet weak var noHerosView: UIView!
    @IBOutlet weak var noHerosImage: UIImageView!
    
    override func viewWillAppear(_ animated: Bool) {
        getFavoritesFromDB()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        favoritesCollectionView.delegate = self
        favoritesCollectionView.dataSource = self
        
        Utils.formatNavigationTitleFontWithDefaultStyle(view: self, description: "Favorites")
    }
    
    func GetIndexFromCellToController(cell: FavoritesCollectionViewCell) {
        if let indexPath = self.favoritesCollectionView.indexPath(for: cell) {
            guard let _ = self.favoritesCollectionView.cellForItem(at: indexPath) as? FavoritesCollectionViewCell else { return }
            
            if let idFromService = cell.object?.id {
                let comandoCheckIfIsFavoriteOrNot = "SELECT * from MyHeros WHERE Favorite = 'true' AND IDFromService = '\(idFromService)'"
                
                if(sqlite3_exec(dataBase, comandoCheckIfIsFavoriteOrNot, nil, nil, nil)) == SQLITE_OK  {
                    
                    var resultado : OpaquePointer? = nil
                    
                    if (sqlite3_prepare_v2(dataBase, comandoCheckIfIsFavoriteOrNot, -1, &resultado, nil) == SQLITE_OK) {
                        
                        if sqlite3_step(resultado) == SQLITE_ROW {
                            
                            
                        }else{
                            self.removeHeroAsFavorite(cell: cell)
                        }
                        sqlite3_finalize(resultado)
                    }
                }else{
                    print("SQL Select Error")
                }
            }
        }
    }
    
    func validateResultZero() {
        if arrayFromDb.count == 0 {
            self.noHerosView.isHidden = false
            
            let alert = UIAlertController(title: "Marvel Heros", message: "Não há herois adicionados a sua lista de Favoritos", preferredStyle: UIAlertControllerStyle.alert)
            
            let OKAction = UIAlertAction(title: "OK", style: .default) { (action) in
                
                Utils.removeLoadingScreen(view: self.view)
            }
            alert.addAction(OKAction)
            self.present(alert, animated: true, completion: nil)
        }else{
            self.noHerosView.isHidden = true
        }
    }
    
    func removeHeroAsFavorite(cell: FavoritesCollectionViewCell) {
        
        if let id = cell.object?.id {
            
            let comandoDelete = "DELETE from MyHeros WHERE ID_Hero = '\(id)'"
            
            if(sqlite3_exec(dataBase, comandoDelete, nil, nil, nil)) == SQLITE_OK  {
                
                let alert = UIAlertController(title: "Marvel Heroes", message: "Heroi removido com sucesso dos Favoritos!", preferredStyle: UIAlertControllerStyle.alert)
                
                let OKAction = UIAlertAction(title: "Ok", style: .default) { (action) in
                    print("Deleted Successfully")
                    self.getFavoritesFromDB()
                }
                alert.addAction(OKAction)
                
                self.present(alert, animated: true, completion: nil)
            }
        }
    }
    
    //CollectionView Delegates
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return arrayFromDb.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        if let cell = favoritesCollectionView.dequeueReusableCell(withReuseIdentifier: "FavoritesCollection", for: indexPath) as? FavoritesCollectionViewCell {
            
            let charactes = DataBaseObject(dictionary: arrayFromDb[indexPath.row])
            cell.object = charactes
            cell.delegate = self
            
            return cell
        }
        return UICollectionViewCell()
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        if let cell = favoritesCollectionView.dequeueReusableCell(withReuseIdentifier: "FavoritesCollection", for: indexPath) as? FavoritesCollectionViewCell {
            
            let hero = DataBaseObject(dictionary: arrayFromDb[indexPath.row])
            cell.object = hero
            
            if let t = storyboard?.instantiateViewController(withIdentifier: "HeroDetails") as? HeroDetailsViewController {
                
                if let id = cell.object?.idFromService {
                    
                    if let heroName = cell.object?.name {
                        t.heroName = heroName
                    }
                    if let heroDescription = cell.object?.description {
                        t.heroDescriptionText = heroDescription
                    }                
                    if let thumPath = cell.object?.extensionThumb {
                        t.thumbPath = thumPath
                    }
                    t.id = id
                    navigationController?.pushViewController(t, animated: true)
                }
            }
        }
    }
    
    func getFavoritesFromDB() {
        arrayFromDb = []
        
        if (FileManager.default.fileExists(atPath: pathArquivo)) {
            
            if(sqlite3_open(pathArquivo, &dataBase) == SQLITE_OK)  {
                
                let selectAllHeroes = "SELECT * from MyHeros"
                
                if (sqlite3_prepare_v2(dataBase, selectAllHeroes, -1, &resultado, nil) == SQLITE_OK) {
                    
                    if (sqlite3_step(resultado) == SQLITE_ROW) {
                        
                        while sqlite3_step(resultado) == SQLITE_ROW {
                            
                            let ID_Hero = sqlite3_column_int(resultado, 0)
                            
                            let IDFromService = sqlite3_column_int(resultado, 1)
                            
                            let Hero_Name = String(cString: sqlite3_column_text(resultado, 2))
                            
                            let Hero_Url  = String(cString: sqlite3_column_text(resultado, 3))
                            
                            var dicionario : [String:AnyObject] = [:]
                            
                            dicionario["id"] = ID_Hero as AnyObject?
                            dicionario["idFromService"] = IDFromService as AnyObject?
                            dicionario["name"] = Hero_Name as AnyObject?
                            dicionario["extensionThumb"] = Hero_Url as AnyObject?
                            
                            self.arrayFromDb.append(dicionario)
                        }
                    }
                    sqlite3_finalize(resultado)
                    
                }else{
                    let errorMessage = String(cString: sqlite3_errmsg(dataBase)!)
                    print("Query could not be prepared! \(errorMessage)")
                }
                
                favoritesCollectionView.reloadData()
                self.validateResultZero()
            }
            
        }else{
            print ("Database opened error")
        }
    }
}
