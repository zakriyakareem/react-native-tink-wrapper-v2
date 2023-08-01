import React from 'react';
import { PixelRatio, Platform, StatusBar, View, useWindowDimensions } from 'react-native';
import { AccountView } from 'react-native-tink-wrapper-v2';

export default function App() {
  const windowDimensions = useWindowDimensions();
  const windowHeight = StatusBar.currentHeight
    ? windowDimensions.height - StatusBar.currentHeight
    : windowDimensions.height;
  const windowHeightOS = Platform.OS === 'android' ? PixelRatio.getPixelSizeForLayoutSize(windowHeight) : windowHeight;
  const windowWidthOS =
    Platform.OS === 'android' ? PixelRatio.getPixelSizeForLayoutSize(windowDimensions.width) : windowDimensions.width;

  const onSuccess = (data: string) => {
    console.log('on success', data);
  };

  return (
    <View
      style={{
        width: '100%',
        height: windowHeight,
      }}>
      {/* {token =='' ?<TinkView onSuccess={(token:string)=>setToken(token)} />:<AccountView style={{ flex:0.5,width:'10%' }} onSuccess={onSuccess} /> } */}
      <AccountView
       style={{
        height: windowHeightOS,
        width: windowWidthOS,
      }}
      callbackUrl={"https://console.tink.com/callback"}
      redirectUrl={"https://console.tink.com/"}
      clientId={"2b40d76678a2415eb4be14a415685db2"}
      token={"eyJhbGciOiJFUzI1NiIsImtpZCI6ImFlMmI0MzNkLWFhYmYtNDMzZC1iZTM5LTZhYjNmOTBjNDZjMCIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODQ3NDU5NDQsImlhdCI6MTY4NDczODc0NCwiaXNzIjoidGluazovL2F1dGgiLCJqdGkiOiI0ZWEwZWJmYS01NWQwLTQ0NWYtOGM2Yy0yYzMwZTQyOTVhNWEiLCJvcmlnaW4iOiJtYWluIiwic2NvcGVzIjpbInByb3ZpZGVyczpyZWFkIiwidXNlcjpjcmVhdGUiLCJhdXRob3JpemF0aW9uOnJlYWQiLCJjcmVkZW50aWFsczp3cml0ZSIsImF1dGhvcml6YXRpb246Z3JhbnQiLCJjcmVkZW50aWFsczpyZWZyZXNoIiwidXNlcjpyZWFkIiwiYWNjb3VudHM6cmVhZCIsImNyZWRlbnRpYWxzOnJlYWQiLCJ0cmFuc2FjdGlvbnM6cmVhZCJdLCJzdWIiOiJ0aW5rOi8vYXV0aC91c2VyLzEzZTQxMGZhNzc0ZTQ2MjdiMWNkMjI2MWNmMTBhMjFmIiwidGluazovL2FwcC9pZCI6IjVlNzNmZGNkMWEwYzRiOTNiYWEzMWM3OTZkMTVhZWEwIiwidGluazovL2FwcC92ZXJpZmllZCI6ImZhbHNlIiwidGluazovL2NsaWVudC9pZCI6IjJiNDBkNzY2NzhhMjQxNWViNGJlMTRhNDE1Njg1ZGIyIn0.O3kwEuo7hli28BU3dT2RZDrr-tu21SVmJd-mK_H8L4N2cO3hbGKHZSNi33iQKnBnu-jddbwHhjWzhIJbfEl9YQ"}
      onSuccess={onSuccess} />
    </View>
  );
}