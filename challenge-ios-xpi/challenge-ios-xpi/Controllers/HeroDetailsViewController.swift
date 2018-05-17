//
//  HeroDetailsViewController.swift
//  challenge-ios-xpi
//
//  Created by Felipe Mac on 14/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import UIKit
import Alamofire

class HeroDetailsViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    
    @IBOutlet weak var heroImageView: UIImageView!
    @IBOutlet weak var heroDescription: UITextView!
    @IBOutlet weak var seriesCollectionView: UICollectionView!
    @IBOutlet weak var comicsCollectionView: UICollectionView!
    @IBOutlet weak var SeriesLabel: UILabel!
    @IBOutlet weak var ComicsLabel: UILabel!
    @IBOutlet weak var noConnectionView: UIView!
    
    var mainArray = [[String:AnyObject]]()
    var comicsArray = [[String:AnyObject]]()
    var seriesArray = [[String:AnyObject]]()
    var heroName = ""
    var thumbPath = ""
    var heroDescriptionText = ""
    var id : Int?
    
    override func viewWillAppear(_ animated: Bool) {
        CheckInternetStatus()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setDelegates()
    }
    
    func setDelegates() {
        comicsCollectionView.delegate = self
        comicsCollectionView.dataSource = self
        seriesCollectionView.delegate = self
        seriesCollectionView.dataSource = self
    }
    
    func displayHeroData() {
        DispatchQueue.main.async {
            self.heroImageView.layer.cornerRadius = 5
            self.heroDescription.text = self.heroDescriptionText
            self.SeriesLabel.font = UIFont(name: "Pacifico-Regular", size: 20)
            self.ComicsLabel.font = UIFont(name: "Pacifico-Regular", size: 20)
            Utils.formatNavigationTitleFontWithDefaultStyle(view: self, description: self.heroName)
        }
        
        if let imageFromCache = Utils.imageCache.object(forKey: thumbPath as AnyObject) as? UIImage {
            DispatchQueue.main.async {
                self.heroImageView.image = imageFromCache
            }
        } else {
            DispatchQueue.main.async {
                self.heroImageView.image = #imageLiteral(resourceName: "MarvelNotFound")
            }
        }
    }
    
    func CheckInternetStatus() {
        
        let reachability = Reachability()!
        
        reachability.whenReachable = { _ in
            print("Connected")
            DispatchQueue.main.async {                
                self.displayHeroData()
                self.comicsCollectionView.reloadData()
                self.seriesCollectionView.reloadData()
                self.noConnectionView.isHidden = true
                self.getHero()
            }
        }
        
        reachability.whenUnreachable = { _ in
            DispatchQueue.main.async {
                self.noConnectionView.isHidden = false
                Utils.removeLoadingScreen(view: self.view)
            }
            print("Not Connected to Internet")
            
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
        if comicsArray.count == 0 {
            ComicsLabel.isHidden = true
            comicsCollectionView.isHidden = true
        }else{
            ComicsLabel.isHidden = false
            comicsCollectionView.isHidden = false
            comicsCollectionView.reloadData()
        }
        if seriesArray.count == 0 {
            SeriesLabel.isHidden = true
            seriesCollectionView.isHidden = true
        }else{
            SeriesLabel.isHidden = false
            seriesCollectionView.isHidden = false
            seriesCollectionView.reloadData()
        }
    }
    
    //CollectionView Delegates
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        
        if collectionView == comicsCollectionView {
            
            return 1
        }
        
        if collectionView == seriesCollectionView {
            
            return 1
        }
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        
        if collectionView == comicsCollectionView {
            
            return comicsArray.count
        }
        
        if collectionView == seriesCollectionView {
            
            return seriesArray.count
        }
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        if collectionView == comicsCollectionView {
            
            if let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ComicsCell", for: indexPath) as? ComicsCollectionViewCell {
                
                let object = Items(dictionary: comicsArray[indexPath.row])
                cell.object = object
                
                return cell
            }
            return UICollectionViewCell()
        }
        
        if collectionView == seriesCollectionView {
            
            if let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "SeriesCell", for: indexPath) as? SeriesCollectionViewCell {
                
                let object = Items(dictionary: seriesArray[indexPath.row])
                cell.object = object
                
                return cell
            }
        }
        
        return UICollectionViewCell()
    }
    
    //Apis
    func getHero(presentLoadingView: Bool = true) {
        
        if presentLoadingView {
            Utils.setLoadingScreen(view: self.view)
        }
        let heroUrl = "\(ApiServiceURL.pathCharacterPlusId)\(id ?? 0)?"
        
        let manager = Alamofire.SessionManager.default
        manager.session.configuration.timeoutIntervalForRequest = 30
        
        //let offset = offset
        //let queryParams: [String:String] = ["offset": String(offset), "limit": String(ApiServiceURL.limit)]
        let url = ApiServiceURL.path + heroUrl + ApiServiceURL.getCredencial()
        
        manager.request("\(url)", method: .get, parameters: nil).responseJSON { response in
            
            switch (response.result) {
            case .success:
                
                if let jsonDict = response.result.value as? [String:AnyObject] {
                    
                    if let data = jsonDict["data"] as? [String:AnyObject] {
                        
                        if let result = data["results"] as? [[String:AnyObject]] {
                            
                            self.mainArray = result
                            
                            if let comics = result[0]["comics"] as? [String:AnyObject] {
                                if let items = comics["items"] as? [[String:AnyObject]] {
                                    self.comicsArray = items
                                }
                            }
                            if let series = result[0]["series"] as? [String:AnyObject] {
                                if let items = series["items"] as? [[String:AnyObject]] {
                                    
                                    self.seriesArray = items
                                }
                            }
                        }
                    }
                }
                Utils.removeLoadingScreen(view: self.view)
                self.validateResultZero()
            case .failure(let error):
                print(error)
                break
            }
        }
    }
}

