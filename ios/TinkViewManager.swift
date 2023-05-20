//
//  TinkViewManager.swift
//  TinkWrapperV2
//
//  Created by Zakriya Kareem on 18/05/2023.
//  Copyright © 2023 Facebook. All rights reserved.
//
import Foundation
import UIKit
@objc (TinkViewManager)

class TinkViewManager: RCTViewManager {

  override static func requiresMainQueueSetup() -> Bool {

    return true

  }



 override func view() -> (TinkView) {

DispatchQueue.main.async(execute: {

UIApplication.shared.isIdleTimerDisabled = true

})

return TinkView()

 }

}
