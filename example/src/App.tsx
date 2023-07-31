import React, { useState } from 'react';
import { SafeAreaView, ScrollView, StyleSheet, Text, View } from 'react-native';
import { TinkView ,AccountView} from 'react-native-tink-wrapper-v2';

export default function App() {
  const [token, setToken] = useState('');


  const onSuccess = (data: string) => {
    console.log('on success', data);
  };

  return (
    <SafeAreaView style={styles.container}>
      {/* {token =='' ?<TinkView onSuccess={(token:string)=>setToken(token)} />:<AccountView style={{ flex:0.5,width:'10%' }} onSuccess={onSuccess} /> } */}
       {/* <TinkView style={{ flex:1 }} onSuccess={(token:string)=>setToken(token)} /> */}
      <AccountView style={{ flex:0.5,height:320 }} onSuccess={onSuccess} /> 
      <Text style={{color:'blue',fontSize:32,flex:1}}>Hello</Text>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingHorizontal:50,
    
  },
 
});
