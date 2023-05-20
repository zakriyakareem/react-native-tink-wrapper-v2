import React, { useState } from 'react';
import { SafeAreaView, StyleSheet, View } from 'react-native';
import { TinkView } from 'react-native-tink-wrapper-v2';

export default function App() {
  const [tinkData, setTinkData] = useState('');

  const onSuccess = (data: string) => {
    setTinkData(data);
    console.log('on success', data);
  };

  return (
    <SafeAreaView style={styles.container}>
      <TinkView style={{ height:400 }} onSuccess={onSuccess} />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    
  },
 
});
