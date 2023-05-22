//
//  TinkLinkManager.m
//  TinkWrapperV2
//
//  Created by Zakriya Kareem on 20/05/2023.
//  Copyright © 2023 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(TinkLinkSDK, NSObject)

RCT_EXTERN_METHOD(startSDK: (NSString *)clientID
                  market:(NSString *)market
                  urlScheme:(NSString *)urlScheme
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                )


@end
