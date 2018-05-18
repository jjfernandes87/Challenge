//
//  TodayViewController.swift
//  MarvelChallengeWidget
//
//  Created by Felipe Mac on 18/5/18.
//  Copyright © 2018 Felipe Mac. All rights reserved.
//

import UIKit
import NotificationCenter

class TodayViewController: UIViewController, NCWidgetProviding, UICollectionViewDelegate, UICollectionViewDataSource {
    
    var updateResult:NCUpdateResult = NCUpdateResult.noData
    var geoInfoDictionary:NSDictionary?
    
    var widgetArray = [[String:AnyObject]]()
    let dataObject = ["imageHero":"favoritesSelected"]
    
    @IBOutlet weak var MarvelWidgetTitle: UILabel!
    @IBOutlet weak var widgetCollectionView: UICollectionView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view from its nib.
        self.widgetCollectionView.delegate = self
        self.widgetCollectionView.dataSource = self
        self.extensionContext?.widgetLargestAvailableDisplayMode = .expanded
        widgetArray.append(dataObject as [String : AnyObject])
        widgetCollectionView.reloadData()
    }
    
    func callApi() {
    
        let params : [String:String] = ["offset": "0", "limit": "3"]
        var request = URLRequest(url: URL(string: "https://gateway.marvel.com/v1/public/characters?offset=0&limit=3&hash=f930283af9d02dafeb7b18630d4660d6&apikey=4cb73a5f44f93bfb931662413dc03e65&ts=1526651915.634&")!)
        request.httpMethod = "GET"
        request.httpBody = try? JSONSerialization.data(withJSONObject: params, options: [])
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        let session = URLSession.shared
        let task = session.dataTask(with: request, completionHandler: { data, response, error -> Void in
            
            do {
                let json = try JSONSerialization.jsonObject(with: data!) as! Dictionary<String, AnyObject>
                print(json)
            } catch {
                print("error")
            }
        })
        
        task.resume()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func widgetPerformUpdate(completionHandler: (@escaping (NCUpdateResult) -> Void)) {
        // Perform any setup necessary in order to update the view.
        
        // If an error is encountered, use NCUpdateResult.Failed
        // If there's no update required, use NCUpdateResult.NoData
        // If there's an update, use NCUpdateResult.NewData
        
        completionHandler(NCUpdateResult.newData)
    }
    
    func widgetActiveDisplayModeDidChange(_ activeDisplayMode: NCWidgetDisplayMode, withMaximumSize maxSize: CGSize) {
        
        if activeDisplayMode == .expanded {
          preferredContentSize = CGSize(width: 300, height: 300)
        }else{
           preferredContentSize = maxSize
        }
    }
    
    //CollectionView Delegates
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return widgetArray.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        if let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "WidgetCell", for: indexPath) as? WidgetCollectionViewCell {
            
            let object = ObjectModel(dictionary: widgetArray[indexPath.row])
            cell.object = object
            
            return cell
        }
        
        return UICollectionViewCell()
    }
}
