import React from 'react';
import { View, StatusBar, AsyncStorage, StyleSheet, Image, Text, PermissionsAndroid, Platform, ToastAndroid, ImageBackground, TouchableHighlight } from 'react-native';
import Geolocation from 'react-native-geolocation-service';
import Constants from '../constants';
import {Header, Icon, Input, CheckBox, Button} from 'react-native-elements';

export default class RequestRescue extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            noOfPeople: 0,
            phoneNumber: 0,
            isMedicalEmergency:false
        }
    }
    requestRescue = async ()=> {
        if (this.state.noOfPeople == 0 && this.state.isMedicalEmergency == 0) {
            return alert('Please fill up number of people and medical support detail')
        }
        const latitude = await AsyncStorage.getItem('latitude');
        const longitude = await AsyncStorage.getItem('longitude');
        const deviceToken = await AsyncStorage.getItem('notification_device_token');
        const notifcation_token = JSON.parse(deviceToken)
        const Coords = [parseFloat(latitude),parseFloat(longitude)];
        
        console.log(Coords,Constants.request_rescue, deviceToken)
        let data = JSON.stringify({
            name: this.state.name,
            coordinates: Coords,
            count: this.state.noOfPeople,
            mobile: this.state.phoneNumber,
            deviceToken: notifcation_token.token,
        })
        console.log(data)
        fetch(Constants.request_rescue, {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: data,
      }).then((response) => response.json())
      .then((responseJson) => {
        console.log(responseJson);
        if(responseJson.error == null){
            alert('The rescue team will save you');
            this.props.navigation.goBack();
        }
      })
      .catch((error) => {
        console.error(error);
      });
    }



    render() {
        return (
            <View style={styles.container}>
               <Header
               statusBarProps={{ barStyle: 'light-content', translucent: true, backgroundColor: Constants.app_color }}
               barStyle="light-content"
               backgroundColor={Constants.app_color}
               leftComponent={<TouchableHighlight
                underlayColor={'transparent'}
                onPress={()=> this.props.navigation.goBack()}>
                   <Icon name="arrow-back" color="white"/>
               </TouchableHighlight>}
               centerComponent={{ text: 'Request Rescue', style: { color: '#fff',fontFamily: "Avenir-Roman", fontSize: 17 } }}      
             />

             <View style={{marginHorizontal: '5%', marginBottom: '10%' }}>
             <Text style={styles.formTitle}>
                 Enter your name (Optional)
             </Text>
              <Input
                value={this.state.name}
                onChangeText={(name)=>this.setState({name})}
                placeholder='Narendra Modi'
                leftIcon={{ type: 'font-awesome', name: 'user', color: Constants.app_color }}
              />
              <Text style={styles.formTitle}>
                 Enter number of people 
             </Text>
               <Input
                value={this.state.noOfPeople}
                onChangeText={(noOfPeople)=>this.setState({noOfPeople})}
                placeholder='2'
                keyboardType='numeric'
                leftIcon={{ type: 'font-awesome', name: 'users', color: Constants.app_color }}
              />
              <Text style={styles.formTitle}>
                 Enter your phone number (Optional)
             </Text>
              <Input
               value={this.state.phoneNumber}
               onChangeText={(phoneNumber)=>this.setState({phoneNumber})}
                placeholder='9812870823'
                keyboardType='numeric'
                leftIcon={{ type: 'font-awesome', name: 'phone', color: Constants.app_color }}
              />
             <CheckBox
              center
              title='Need Medical Support?'
              onPress={()=>this.setState({isMedicalEmergency: !this.state.isMedicalEmergency})}
              checkedIcon='dot-circle-o'
              uncheckedIcon='circle-o'
              textStyle={{fontFamily: "Avenir-Roman"}}
              containerStyle={{backgroundColor: 'transparent', marginTop: '10%'}}
              checkedColor={Constants.app_color}
              checkedTitle={"Added Medical Support"}
              fontFamily={'Avenir-Roman'}
              checked={this.state.isMedicalEmergency}
            />
             </View>
             <View style={{marginHorizontal: '5%'}}>
             <Button
             onPress={()=> this.requestRescue() }
             title="Submit"
             type="solid"
             raised={true}
             buttonStyle={{backgroundColor: Constants.app_color}}
           />
             </View>
            </View>
        )
    }
    
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    subView: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: Constants.app_color,
    },
    formTitle:{
        marginTop: '5%',
        fontSize: 16
    },
    heading: {
        textAlign: 'center',
        fontSize: 24,
        fontFamily: "Avenir-Roman",
        color: "white"
    }
});