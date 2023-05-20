//
//  TinkView.swift
//  TinkWrapperV2
//
//  Created by Zakriya Kareem on 18/05/2023.
//  Copyright © 2023 Facebook. All rights reserved.
//

import Foundation
import UIKit
import TinkLink
@objc(TinkView)
public class TinkView: UIView {
    
    var tinkcontroller = TinkController()
    
    override open func reactSetFrame(_ frame: CGRect) {

    super.reactSetFrame(frame)
    self.tinkcontroller.initSubview(frame: frame)
    self.addSubview(self.tinkcontroller)
    }
    
    
    
}

