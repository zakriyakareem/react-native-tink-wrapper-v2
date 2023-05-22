
import React from 'react';
import { View } from 'react-native';
import {
  requireNativeComponent,
  UIManager,
  ViewStyle,
  Platform,
  NativeEventEmitter,
  NativeModules,
  Button
} from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-tink-wrapper-v2' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const ComponentName = 'TinkView';

type TinkViewProps = {
  style: ViewStyle;
  onSuccess?: (data: string) => void;
};

// const TinkViewNativeModule = NativeModules.TinkViewManager;
// const TinkViewEventEmitter = new NativeEventEmitter(TinkViewNativeModule);

export const AccountView = UIManager.getViewManagerConfig(ComponentName) != null
  ? requireNativeComponent<TinkViewProps>(ComponentName, null)
  : () => {
      throw new Error(LINKING_ERROR);
    };

const TinkLinkSDK = NativeModules.TinkLinkSDK;
export const TinkView =(props:any)=>{
  return <View>
    <Button
title="Tink"
onPress={async () => {
const tinkResult = await TinkLinkSDK.startSDK("2b40d76678a2415eb4be14a415685db2", "FR", "myapp");
props.onSuccess(tinkResult["code"])
console.log(tinkResult["code"]);
}}
>

</Button>
  </View>
}
    

// React.useEffect(() => {
//   const subscription = TinkViewEventEmitter.addListener('onSuccess', (data) => {
//     console.log('data ',data)
//     const { onSuccess } = TinkViewProps;
//     if (onSuccess) {
//       onSuccess(data);
//     }
//   });

//   return () => {
//     subscription.remove();
//   };
// }, []);








