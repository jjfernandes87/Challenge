//
//  HomeViewController.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 14/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import UIKit
import Alamofire
import SQLite3

class HomeViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource, GetIndexBtnFromCollectionCellHomeDelegate {
    
    @IBOutlet weak var noConnectionView: UIView!
    var charactesArray = [[String:AnyObject]]()
    let pathArquivo = (NSHomeDirectory() as NSString).appendingPathComponent("Documents/arquivo.sqlite")
    var dataBase : OpaquePointer? = nil
    //let refreshControl = UIRefreshControl()
    var offset = 0
    var checkCaller = false
    
    @IBOutlet weak var HomeCollectionView: UICollectionView!
    
    override func viewWillAppear(_ animated: Bool) {
        
        CheckInternetStatus()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
       HomeCollectionView.addSubview(refreshControl)
        
       HomeCollectionView.delegate = self
       HomeCollectionView.dataSource = self
        
       Utils.formatNavigationTitleFontWithDefaultStyle(view: self, description: "Characters")
       CheckDBIfExistIfNotCreateDB()
    }
    
    lazy var refreshControl: UIRefreshControl = {
        let refreshControl = UIRefreshControl()
        refreshControl.addTarget(self, action: #selector(HomeViewController.handleRefresh(_:)), for: UIControlEvents.valueChanged)
        
        return refreshControl
    }()
    
    @objc func handleRefresh(_ refreshControl: UIRefreshControl) {
        getCharacters()
        self.HomeCollectionView.reloadData()
        refreshControl.endRefreshing()
    }
    
    func addHeroAsFavorite(cell: HomeCollectionViewCell) {
        
        if var name = cell.object?.name {
            
            if name.contains("'") {
              name = name.replacingOccurrences(of: "'", with: "")
            }
            
            if let path = cell.object?.thumbnail?.path {
                
                if let extensionThumb = cell.object?.thumbnail?.extensionThumb {
                    
                    let url = "\(path).\(extensionThumb)"
                    
                    if var description = cell.object?.description {
                        
                        if description.contains("'") {
                            description = description.replacingOccurrences(of: "'", with: "")
                        }
                        
                        if let idFromService = cell.object?.id {
                        
                        let comandoCadastro = "INSERT into MyHeros values (NULL, '\(idFromService)', '\(name)', '\(url)', '\(true)', '\(description)')"
                        
                        if(sqlite3_exec(dataBase, comandoCadastro, nil, nil, nil)) == SQLITE_OK  {
                            
                            let alert = UIAlertController(title: "Marvel Heroes", message: "Heroi adiconado com sucesso aos seus Favoritos!", preferredStyle: UIAlertControllerStyle.alert)
                            
                            let OKAction = UIAlertAction(title: "Ok", style: .default) { (action) in
                                print("Saved Successfully")
                                self.HomeCollectionView.reloadData()
                            }
                            alert.addAction(OKAction)
                            
                            self.present(alert, animated: true, completion: nil)
                            
                        }else{
                            print("SQL Insert Error")
                            print(sqlite3_errmsg)
                        }
                    }
                }
            }
        }
    }
}
    
    func removeHeroAsFavorite(cell: HomeCollectionViewCell) {
        
        if let idFromService = cell.object?.id {
         
            let comandoDelete = "DELETE from MyHeros WHERE IDFromService = '\(idFromService)'"
            
            if(sqlite3_exec(dataBase, comandoDelete, nil, nil, nil)) == SQLITE_OK  {
                
                let alert = UIAlertController(title: "Marvel Heroes", message: "Heroi removido com sucesso dos Favoritos!", preferredStyle: UIAlertControllerStyle.alert)
                
                let OKAction = UIAlertAction(title: "Ok", style: .default) { (action) in
                    print("Deleted Successfully")
                    DispatchQueue.main.async {
                        self.HomeCollectionView.reloadData()
                    }
                    
                }
                alert.addAction(OKAction)
                
                self.present(alert, animated: true, completion: nil)
                
            }
        }
    }
    
    func GetIndexFromCellToController(cell: HomeCollectionViewCell) {
        if let indexPath = self.HomeCollectionView.indexPath(for: cell) {
            guard let cell = self.HomeCollectionView.cellForItem(at: indexPath) as? HomeCollectionViewCell else { return }
            
            if let idFromService = cell.object?.id {
                let comandoCheckIfIsFavoriteOrNot = "SELECT * from MyHeros WHERE Favorite = 'true' AND IDFromService = '\(idFromService)'"
                
                if(sqlite3_exec(dataBase, comandoCheckIfIsFavoriteOrNot, nil, nil, nil)) == SQLITE_OK  {
                    
                    var resultado : OpaquePointer? = nil
                  
                    if (sqlite3_prepare_v2(dataBase, comandoCheckIfIsFavoriteOrNot, -1, &resultado, nil) == SQLITE_OK) {
                        
                        if sqlite3_step(resultado) == SQLITE_ROW {
                            
                            self.removeHeroAsFavorite(cell: cell)
                        }else{
                             self.addHeroAsFavorite(cell: cell)
                        }
                        sqlite3_finalize(resultado)
                    }
                }else{
                  print("SQL Select Error")
                }
            }
        }
    }
    
    func CheckDBIfExistIfNotCreateDB(){
        print("Path:\(pathArquivo)")
        if (FileManager.default.fileExists(atPath: pathArquivo)) {

            if(sqlite3_open(pathArquivo, &dataBase) == SQLITE_OK)  {
                print("Database opened successfully")
            }else{
                print ("Database opened error")
            }
        }else{
            if (sqlite3_open(pathArquivo, &dataBase) == SQLITE_OK) {
                print("Database created Successfully")

                let comando1 = " create table if not exists MyHeros (ID_Hero INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, IDFromService INTERGER, Hero_Name TEXT, Hero_Url TEXT, Favorite Bool, Hero_Description TEXT )"

                if (sqlite3_exec(dataBase, comando1, nil, nil, nil)) == SQLITE_OK {
                    print("Table created Successfully")

                }else{
                    print("Table created Error \(sqlite3_errmsg(dataBase))")
                }
            }else{
                print("Database created Error")
            }
        }
    }
    
    func CheckInternetStatus() {
        
        let reachability = Reachability()!
        
        reachability.whenReachable = { _ in
            print("Connected")

            if self.checkCaller == false {
                self.checkCaller = true
            DispatchQueue.main.async {
                self.noConnectionView.isHidden = true
                self.getCharacters(offset: 0, presentLoadingView: true)
                }
            }else{
                DispatchQueue.main.async {
                self.noConnectionView.isHidden = true
                self.HomeCollectionView.reloadData()
                }
            }
        }
        
        reachability.whenUnreachable = { _ in
                print("Not Connected to Internet")
            
            DispatchQueue.main.async {
                self.noConnectionView.isHidden = false
                self.HomeCollectionView.reloadData()
            }
                
                let alert = UIAlertController(title: "Conexão sem Internet", message: "Somente o modulo de favoritos se encontra disponivel enquanto não estiver conectado a internet", preferredStyle: UIAlertControllerStyle.alert)
                
                let OKAction = UIAlertAction(title: "OK", style: .default) { (action) in
                    
                }
                alert.addAction(OKAction)
                
                self.present(alert, animated: true, completion: nil)
            }
        
        NotificationCenter.default.addObserver(self, selector: #selector(internetChanged), name: ReachabilityChangedNotification, object: reachability)
        
        do {
            try reachability.startNotifier()
        }catch{
            print("Could not start notifier")
        }
    }
    
    @objc func internetChanged(note: Notification) {
        
        let reachability2 = note.object as! Reachability
        if reachability2.isReachable {
            if reachability2.isReachableViaWiFi {
                print("Connected via Wifi")
                
        }else{
                print("Not Connected to Wifi")
                }
        }
    }
    
     func validateResultZero() {
        if charactesArray.count == 0 {
            let alert = UIAlertController(title: "Marvel Heros", message: "Não temos herois neste momento, Por favor tentar mais tarde", preferredStyle: UIAlertControllerStyle.alert)
            
            let OKAction = UIAlertAction(title: "OK", style: .default) { (action) in
                
                Utils.removeLoadingScreen(view: self.view)
                
            }
            alert.addAction(OKAction)
            self.present(alert, animated: true, completion: nil)
        }else{
            HomeCollectionView.reloadData()
        }
    }
    
    
//CollectionView Delegates
    
    
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        let actualPosition = scrollView.contentOffset.y
        let contentHeight = scrollView.contentSize.height - 1000
        
        if (actualPosition >= contentHeight) {
            offset += 20
            DispatchQueue.main.async {
                self.getCharacters(offset: self.offset, presentLoadingView: false)
            }
        }
    }

    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return charactesArray.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        if let cell = HomeCollectionView.dequeueReusableCell(withReuseIdentifier: "HomeCollection", for: indexPath) as? HomeCollectionViewCell {
            
                let charactes = DataObject(dictionary: charactesArray[indexPath.row])
                cell.object = charactes
                cell.delegate = self
                
                return cell
            }
        return UICollectionViewCell()
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        
        if let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "HomeCollection", for: indexPath) as? HomeCollectionViewCell {
            
            let hero = DataObject(dictionary: charactesArray[indexPath.row])
            cell.object = hero
            
            if let t = storyboard?.instantiateViewController(withIdentifier: "HeroDetails") as? HeroDetailsViewController {
                
                if let id = cell.object?.id {
                    t.id = id
                    if let heroName = cell.object?.name {
                        t.heroName = heroName
                    }
                    if let thumbPath = cell.object?.thumbnail?.path {
                        
                        if let extensionThumb = cell.object?.thumbnail?.extensionThumb {
                        
                        let thumbPath = "\(thumbPath).\(extensionThumb)"
                            t.thumbPath = thumbPath
                        }
                    }
                    if let heroDescription = cell.object?.description {
                        t.heroDescriptionText = heroDescription
                    }
                    
                    navigationController?.pushViewController(t, animated: true)
                }
            }
        }
    }
    
//Apis
 
    func getCharacters(offset: Int = 0, presentLoadingView: Bool = true) {
        
        if presentLoadingView {
        self.charactesArray = []
        Utils.setLoadingScreen(view: self.view)
        }
        
        let manager = Alamofire.SessionManager.default
        manager.session.configuration.timeoutIntervalForRequest = 30

        let offset = offset
        let queryParams: [String:String] = ["offset": String(offset), "limit": String(ApiServiceURL.limit)]
        let url = ApiServiceURL.path + ApiServiceURL.pathCharacters + queryParams.queryString! + ApiServiceURL.getCredencial()

        manager.request("\(url)", method: .get, parameters: nil).responseJSON { response in
            
            self.refreshControl.endRefreshing()
            
            switch (response.result) {
            case .success:
                
                if let jsonDict = response.result.value as? [String:AnyObject] {
                    
                    if let data = jsonDict["data"] as? [String:AnyObject] {
                       
                        if let result = data["results"] as? [[String:AnyObject]] {
                            
                            for item in result {
                                
                                self.charactesArray.append(item)
                                
//                                if let thumbnailObject = item["thumbnail"] as? [String:AnyObject] {
//
//                                    if let path = thumbnailObject["path"] as? String {
//
//                                        if let extensionThumb = thumbnailObject["extension"] as? String {
//
//                                            let pathPlusExtension = "\(path).\(extensionThumb)"
//
//                                            if let url = URL(string: pathPlusExtension) {
//
//                                                if let data = try? Data(contentsOf: url) {
//                                                    if let imageToCache = UIImage(data: data) {
//                                                        Utils.imageCache.setObject(imageToCache, forKey: pathPlusExtension as AnyObject)
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
                            }
                        }
                    }
                    Utils.removeLoadingScreen(view: self.view)
                    self.validateResultZero()
                }
            case .failure(let error):
                print(error)
                break
            }
        }
    }
}
