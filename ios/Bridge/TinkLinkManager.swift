import Foundation
import UIKit
import TinkLink
import TinkLinkUI
import TinkMoneyManagerUI
import TinkCore
@objc(TinkLinkSDK)
class TinkLinkSDK: UIViewController {

  override func viewDidLoad() {
      super.viewDidLoad()
  }

  
  @objc
  private func startSDK(_ clientID: String, market: String, urlScheme: String, resolver resolve: @escaping RCTPromiseResolveBlock, rejecter reject: @escaping RCTPromiseRejectBlock) {
      DispatchQueue.main.async {
          
          let scopes: [Scope] = [
            .transactions(.read),
            .accounts(.read),
            .authorization(.grant),
            .authorization(.read),
            .user(.create),
            .user(.read),
            .credentials(.read),
            .credentials(.write),
            .credentials(.refresh),
            .providers(.read)
          ]
          let redirectURI = NSURL(string: "https://console.tink.com/callback")
               
          
               let configuration = TinkLinkConfiguration(clientID: "2b40d76678a2415eb4be14a415685db2", appURI: redirectURI! as URL)


               let tinkLinkViewController = TinkLinkViewController(configuration: configuration, market: "FR", scopes: scopes, providerPredicate: .kinds(.all)) { (result) in
                 // (Result<(code: AuthorizationCode, credentials: Credentials), TinkLinkError>)
                 switch (result) {
                 case .success(let data):
                   return resolve(["code": data.code.rawValue])
                 case .failure(let error):
                   return reject("tink_failed", error.localizedDescription, nil)
                 }

               }
               UIApplication.shared.windows.first?.rootViewController?.present(tinkLinkViewController, animated: true)
               }
             }
           }
