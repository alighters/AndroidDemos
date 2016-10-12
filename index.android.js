'use strict';

import React from 'react';
import {
  AppRegistry,
  StyleSheet,
  TouchableHighlight,
  Image,
  Text,
  View
} from 'react-native';

import MyToast from './MyToast';


class HelloWorld extends React.Component {

   _onPressButton (){
      MyToast.show("Click the button!!!", MyToast.LONG)
   }

  render() {
    return (
      <View style={styles.container}>
          <Text style={styles.hello}>Hello, World</Text>

          <TouchableHighlight underlayColor='blue' onPress={this._onPressButton}>
            <Image
              style={styles.button}
              source={require('./myButton.png')}
            />
          </TouchableHighlight>
      </View>

    )
  }
}
var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center'
  },
  hello: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  button: {
      width: 100,
      height: 60,
      margin: 10,
    },
});

AppRegistry.registerComponent('HelloWorld', () => HelloWorld);
