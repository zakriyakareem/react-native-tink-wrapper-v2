
import React, { useEffect, useRef } from 'react';
import { HostComponent, View, findNodeHandle } from 'react-native';
import {
  requireNativeComponent,
  UIManager,
  ViewStyle,
  NativeModules,
  Button
} from 'react-native';

type TinkViewProps = {
  style: ViewStyle;
  callbackUrl: string;
  redirectUrl: string;
  token: string;
  clientId: string;
  onSuccess?: (data: string) => void;
};

export const AccountViewComponent: HostComponent<TinkViewProps> = requireNativeComponent('TinkView');

export const AccountView = (props: TinkViewProps) => {
  const ref = useRef(null);

  useEffect(() => {
    const viewId = findNodeHandle(ref.current);
    UIManager.dispatchViewManagerCommand(
      viewId,
        // @ts-ignore
        UIManager.TinkView.Commands.create.toString(),
      [viewId],
    );
  });


  return <AccountViewComponent
    style={props.style}
    redirectUrl={props.redirectUrl}
    callbackUrl={props.callbackUrl}
    token={props.token}
    clientId={props.clientId}
    ref={ref}
  />

}

const TinkLinkSDK = NativeModules.TinkLinkSDK;
export const TinkView = (props: any) => {
  return <View>
    <Button
      title="Tink"
      onPress={async () => {
        const tinkResult = await TinkLinkSDK.startSDK("2b40d76678a2415eb4be14a415685db2", "FR", "myapp");
        props.onSuccess(tinkResult["code"])
        console.log(tinkResult["code"]);
      }}
    />
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








