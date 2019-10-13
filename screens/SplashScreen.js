import React from 'react';
import {View, StatusBar, StyleSheet, Image, Text, AsyncStorage } from 'react-native';
import Constants from '../constants';
var PushNotification = require("react-native-push-notification");
import { Icon } from 'react-native-elements';

const _storeData = async (token) => {
  try {
    await AsyncStorage.setItem('notification_device_token', JSON.stringify(token));
  } catch (error) {
    // Error saving data
    console.log(error)
  }
};

export default class SplashScreen extends React.Component {
    constructor(props) {
        super(props);
        PushNotification.configure({
          onRegister: function (token) {
            console.log("TOKEN:", token);
            _storeData(token);
          },
          onNotification: function (notification) {
            console.log("NOTIFICATION:", notification);
            const clicked = notification.userInteraction;
            if (clicked) {
              try {
                  // switch(notification.screen_type){
                  //   case 'lease_details':
                  //     return this.props.navigation.navigate('LeaseDetails', { property_id: notification.property_id, lease_id: notification.lease_id })
                  // }
              } catch(error){
                this.props.navigation.navigate('LoginScreen');
              }
            }
            notification.finish(PushNotificationIOS.FetchResult.NoData);
          },
          senderID: "793603569477",
          permissions: {
            alert: true,
            badge: true,
            sound: true
          },
          popInitialNotification: true,
          requestPermissions: true
        });
      }
    render(){
        return(
        <View style={styles.container}>
            <StatusBar backgroundColor={Constants.app_color} barStyle="light-content" translucent={true} />
            <View style={styles.subView}>
                {/* <Icon style={{height:"30%", width:"50%"}} name="location" /> */}
                <Text style={styles.heading} >LYNK Aid</Text>
            </View>
        </View>)
    }
    componentDidMount(){
        setTimeout(()=>this.props.navigation.replace('PersonaType'), 2800)
    }
}

const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: Constants.app_color,
    },
    subView: {
        flex: 1,
        alignItems: 'center',
        justifyContent:'center',
        backgroundColor: Constants.app_color,
    },
    heading: {
        fontSize: 24,
        fontFamily: "Avenir-Roman",
        color: "white"
    }
  });