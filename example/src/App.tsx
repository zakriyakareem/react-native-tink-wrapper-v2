import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { multiply,TinkView } from 'react-native-tink-wrapper-v2';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();

  React.useEffect(() => {
    multiply(3, 7).then(setResult);
  }, []);

  return (
    <View style={styles.container}>
    <TinkView style={{flex:1,}} />
      {/* <Text>Result: {result}</Text> */}
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
