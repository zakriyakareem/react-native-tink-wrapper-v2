//
//  TinkController.swift
//  TinkLinkSimpleSample
//
//  Created by Sharjeel Ayubi on 19/05/2023.
//

import Foundation
import UIKit
import TinkLink
import TinkLinkUI
import TinkMoneyManagerUI
import TinkCore

class TinkController: UIView {
    
    var containerView: UIView!
    var closeButton: UIButton!
    var tinkNavigationController: UINavigationController?
    var userToken:String =
    "eyJhbGciOiJFUzI1NiIsImtpZCI6ImFlMmI0MzNkLWFhYmYtNDMzZC1iZTM5LTZhYjNmOTBjNDZjMCIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODQ3NDU5NDQsImlhdCI6MTY4NDczODc0NCwiaXNzIjoidGluazovL2F1dGgiLCJqdGkiOiI0ZWEwZWJmYS01NWQwLTQ0NWYtOGM2Yy0yYzMwZTQyOTVhNWEiLCJvcmlnaW4iOiJtYWluIiwic2NvcGVzIjpbInByb3ZpZGVyczpyZWFkIiwidXNlcjpjcmVhdGUiLCJhdXRob3JpemF0aW9uOnJlYWQiLCJjcmVkZW50aWFsczp3cml0ZSIsImF1dGhvcml6YXRpb246Z3JhbnQiLCJjcmVkZW50aWFsczpyZWZyZXNoIiwidXNlcjpyZWFkIiwiYWNjb3VudHM6cmVhZCIsImNyZWRlbnRpYWxzOnJlYWQiLCJ0cmFuc2FjdGlvbnM6cmVhZCJdLCJzdWIiOiJ0aW5rOi8vYXV0aC91c2VyLzEzZTQxMGZhNzc0ZTQ2MjdiMWNkMjI2MWNmMTBhMjFmIiwidGluazovL2FwcC9pZCI6IjVlNzNmZGNkMWEwYzRiOTNiYWEzMWM3OTZkMTVhZWEwIiwidGluazovL2FwcC92ZXJpZmllZCI6ImZhbHNlIiwidGluazovL2NsaWVudC9pZCI6IjJiNDBkNzY2NzhhMjQxNWViNGJlMTRhNDE1Njg1ZGIyIn0.O3kwEuo7hli28BU3dT2RZDrr-tu21SVmJd-mK_H8L4N2cO3hbGKHZSNi33iQKnBnu-jddbwHhjWzhIJbfEl9YQ"
    // This is a simple sample app, demonstrating how easy it can be to integrate Tinks mobile SDK in your app.

    // First, add your client ID. It can be found on console.tink.com in your apps settings.
    let clientID: String = "2b40d76678a2415eb4be14a415685db2"
    // Then add `tinksdk://example` into the list of redirect URIs under your app's settings in Console.
    let redirectURI: String = "https://console.tink.com/callback"
    // And lastly, the market code for the market you want to test, e.g. "GB" for Great Britain, or "SE" for Sweden.
    let market = Market(code: "BE")
    
    // Now you're all set!
    // Hit Run to test your project with Tinks mobile SDK.
     
    var onSuccess: (()->Void)?
    var onFailure: ((Error)->Void)?
    
    public func initSubview(frame: CGRect) {
       self.frame = frame
        setupContainerView()
        startTinkController()
    }
    
    //Call this function in the start to create a container view in which we are going to display tink
    //It will hide the container View initially because tink will not be initialised yet
    func setupContainerView() {
        containerView = UIView()
        containerView.backgroundColor = .white
        containerView.translatesAutoresizingMaskIntoConstraints = false
        self.addSubview(containerView)
        
        // Add constraints to position the containerView
        NSLayoutConstraint.activate([
            containerView.leadingAnchor.constraint(equalTo: self.leadingAnchor),
            containerView.trailingAnchor.constraint(equalTo: self.trailingAnchor),
            containerView.bottomAnchor.constraint(equalTo: self.bottomAnchor),
            containerView.heightAnchor.constraint(equalTo: self.heightAnchor)
        ])
    }
    
    //Call this function whenever you want to start tink.
    //It will initiate tink and add it into the container view that we created in the start.
    func startTinkController() {
        let configuration = TinkMoneyManagerConfiguration(clientID: clientID)
            let tink = Tink(configuration: configuration)
               tink.userSession = .accessToken(userToken)
        
//        Tink.shared.refresh()
//        tink.refresh()
       
              
        let overviewViewController = FinanceOverviewViewController(tink: tink, features: [.accounts])

               overviewViewController.title = "Overview"
               overviewViewController.tabBarItem = UITabBarItem(title: "Overview", image: UIImage(systemName: "chart.pie.fill"), tag: 0)
        overviewViewController.configuration.noAccountsAction = .addAccount(onTap: {
            
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
                       print("success TinkLinkViewController")
                   case .failure(let error):
                     return print("Failure TinkViewController")
                   }

                 }
            self.parentViewController?.present(tinkLinkViewController, animated: true)
                 
        })
//        Tink.configure(with: configuration)
//        let accountsViewController = AccountsViewController()
//        accountsViewController.show(accountsViewController, sender: self)
        
//        let configuration = TinkLinkConfiguration(clientID: clientID, redirectURI: redirectURI)
//        tinkNavigationController = Tink.Transactions.connectAccountsForOneTimeAccess(configuration: configuration, market: market) { [weak self] result in
//            switch result {
//            case .success(let connection):
//                print("TinkLink OneTimeConnection code: \(String(describing: connection.code)), TinkLink OneTimeConnection credentialsID: \(connection.credentialsID)")
//                self?.onSuccess?(connection)
//            case .failure(let error):
//                print("TinkLink OneTimeConnection error: \(error)")
//                self?.onFailure?(error)
//            }
//        }
        let parentController = self.parentViewController
        let navigationController = UINavigationController(rootViewController: overviewViewController)
        self.tinkNavigationController = navigationController
        parentController?.addChild(navigationController)
        containerView.addSubview(navigationController.view)
        navigationController.didMove(toParent: parentController)
        tinkNavigationController?.view.frame = containerView.bounds
        addCloseButton()
//        tinkNavigationController?.setNavigationBarHidden(true, animated: false)
    }
    
    // Create the close button in containerView
    func addCloseButton() {
        closeButton = UIButton(type: .system)
        if #available(iOS 13.0, *) {
            closeButton.setImage(UIImage(systemName: "xmark"), for: .normal)
        } else {
            // Fallback on earlier versions
        }
        closeButton.setTitle("", for: .normal)
        closeButton.backgroundColor = .white
        closeButton.addTarget(self, action: #selector(closeButtonTapped), for: .touchUpInside)
        
        // Set the close button position
        closeButton.translatesAutoresizingMaskIntoConstraints = false
        containerView.addSubview(closeButton)
        NSLayoutConstraint.activate([
            closeButton.topAnchor.constraint(equalTo: containerView.topAnchor, constant: 0),
            closeButton.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -5),
            closeButton.heightAnchor.constraint(equalToConstant: 44),
            closeButton.widthAnchor.constraint(equalToConstant: 44)
        ])
    }
    
    //on click action of close button
    @objc func closeButtonTapped() {
        print("Close button tapped")
        removeTinkNavigationController()
        closeButton.removeFromSuperview()
        containerView.removeFromSuperview()
        self.removeFromSuperview()
    }
    
    func removeTinkNavigationController() {
        tinkNavigationController?.dismiss(animated: true)
        tinkNavigationController?.willMove(toParent: nil)
        tinkNavigationController?.view.removeFromSuperview()
        tinkNavigationController?.removeFromParent()
        tinkNavigationController = nil
    }
    
}

extension UIResponder {
    public var parentViewController: UIViewController? {
        return next as? UIViewController ?? next?.parentViewController
    }
}
