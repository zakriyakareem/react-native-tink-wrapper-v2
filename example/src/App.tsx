import React, { useState } from 'react';
import { StyleSheet, View } from 'react-native';
import { TinkView } from 'react-native-tink-wrapper-v2';

export default function App() {
  const [tinkData, setTinkData] = useState('');

  const onSuccess = (data: string) => {
    setTinkData(data);
    console.log('on success', data);
  };

  return (
    <View style={styles.container}>
      <TinkView style={{ flex: 0.5 }} onSuccess={onSuccess} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
