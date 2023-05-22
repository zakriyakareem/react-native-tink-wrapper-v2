import React, { useState } from 'react';
import { SafeAreaView, ScrollView, StyleSheet, View } from 'react-native';
import { TinkView ,AccountView} from 'react-native-tink-wrapper-v2';

export default function App() {
  const [token, setToken] = useState('');


  const onSuccess = (data: string) => {
    console.log('on success', data);
  };

  return (
    <SafeAreaView style={styles.container}>
      {/* {token =='' ?<TinkView onSuccess={(token:string)=>setToken(token)} />:<AccountView style={{ flex:0.5,width:'10%' }} onSuccess={onSuccess} /> } */}
      <TinkView onSuccess={(token:string)=>setToken(token)} />
      <AccountView style={{ flex:0.5,width:'10%',height:'20%' }} onSuccess={onSuccess} />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingHorizontal:50,
    
  },
 
});
