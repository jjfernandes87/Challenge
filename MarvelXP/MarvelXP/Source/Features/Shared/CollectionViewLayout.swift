//
//  CollectionViewLayout.swift
//  MarvelXP
//
//  Created by Roger Sanoli on 21/07/19.
//  Copyright © 2019 RogerSanoli. All rights reserved.
//

import Foundation
import UIKit

public class CollectionViewLayout {
    
    private static var portraitLayout: UICollectionViewFlowLayout?
    private static var landscapeLayout: UICollectionViewFlowLayout?
    
    /*
     
     A idéia era gerar o layout para cada orientação apenas uma vez (note a linha comentada abaixo).
     Infelizmente, existe um bug da Apple que gera um EXC_ARITHMETIC em alguns devices antigos aqui.
     Existem alguns workarounds, como chamar reloadData antes de mudar o layout, mas isto interfiriria na experiência do usuário.
     Preferi neste caso gerar um novo layout a cada mudança de orientação, o que gera um pouco mais de memória mas mantém a experiência intacta.
     
     Referência: https://forums.developer.apple.com/thread/19944
     
     */
    
    public static func layoutFor(orientation: UIInterfaceOrientation) -> UICollectionViewFlowLayout {
        return orientation.isPortrait ? self.generatePortraitLayout() : self.generateLandscapeLayout()
        //return orientation.isPortrait ? self.portraitLayout ?? self.generatePortraitLayout() : self.landscapeLayout ?? self.generateLandscapeLayout()
    }
    
    private static func generatePortraitLayout() -> UICollectionViewFlowLayout {
        let percentage: CGFloat = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiom.pad ? 0.25 : 0.5
        let layout = generateBaseLayout(percentage)
        self.portraitLayout = layout
        return layout
    }
    
    private static func generateLandscapeLayout() -> UICollectionViewFlowLayout {
        let percentage: CGFloat = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiom.pad ? 0.33 : 0.6
        let layout = generateBaseLayout(percentage)
        self.landscapeLayout = layout
        return layout
    }
    
    private static func generateBaseLayout(_ percentage: CGFloat) -> UICollectionViewFlowLayout {
        let screenSize = CGSize.screenSize(forcePortrait: true)
        let border: CGFloat = 10
        let cellSize = CGSize(width: screenSize.width * percentage - border, height: screenSize.width * percentage - border)
        
        let layout = UICollectionViewFlowLayout()
        layout.scrollDirection = .vertical
        layout.itemSize = cellSize
        layout.sectionInset = UIEdgeInsets(top: 1, left: 1, bottom: 1, right: 1)
        layout.minimumLineSpacing = 1.0
        layout.minimumInteritemSpacing = 1.0
        return layout
    }
}
