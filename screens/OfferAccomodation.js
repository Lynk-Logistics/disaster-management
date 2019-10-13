import React from 'react';
import { View, StatusBar, StyleSheet,AsyncStorage, Image, Text, PermissionsAndroid, Platform, ToastAndroid, ImageBackground, TouchableHighlight } from 'react-native';
import Geolocation from 'react-native-geolocation-service';
import Constants from '../constants';
import {Header, Icon, CheckBox, Input, Button} from 'react-native-elements';

export default class OfferAccomodation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            capacity: '0',
            contact: '0',
        }
    }
    createRestorePoint = async () => {
        if (this.state.name === "" && this.state.capacity == 0 && this.state.contact == 0) {
            return alert('Please fill up all the details')
        }
        const latitude = await AsyncStorage.getItem('latitude');
        const longitude = await AsyncStorage.getItem('longitude');
        const Coords = [parseFloat(latitude),parseFloat(longitude)];
        
        console.log(Coords,Constants.offer_accomodation)

        fetch(Constants.offer_accomodation, {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: this.state.name,
            coordinates: Coords,
            mobile: this.state.contact,
	        capacity: this.state.capacity
        }),
      }).then((response) => response.json())
      .then((responseJson) => {
        console.log(responseJson);
        if(responseJson.error == null){
           
            alert('The volunteers will allocate persons, Thank You');
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
                underlayColor={'transparent'} onPress={()=> this.props.navigation.goBack()}>
                   <Icon name="arrow-back" color="white"/>
               </TouchableHighlight>}
               centerComponent={{ text: 'Offer Accomodation', style: { color: '#fff',fontFamily: "Avenir-Roman", fontSize: 17 } }}      
             />
             <View style={{marginHorizontal: '5%', marginBottom: '10%' }}>
             <Text style={styles.formTitle}>
                 Enter your building name
             </Text>
              <Input
                value={this.state.name}
                onChangeText={(name)=>this.setState({name})}
                placeholder='Bhakts Building'
                leftIcon={{ type: 'font-awesome', name: 'user', color: Constants.app_color }}
              />
              <Text style={styles.formTitle}>
                 How many people can you accomodate?
             </Text>
               <Input
                value={this.state.capacity}
                onChangeText={(capacity)=>this.setState({capacity})}
                placeholder='2'
                keyboardType='numeric'
                leftIcon={{ type: 'font-awesome', name: 'users', color: Constants.app_color }}
              />
              <Text style={styles.formTitle}>
                 Enter your phone number
             </Text>
              <Input
                value={this.state.contact}
                onChangeText={(contact)=>this.setState({contact})}
                placeholder='9812870823'
                keyboardType='numeric'
                leftIcon={{ type: 'font-awesome', name: 'phone', color: Constants.app_color }}
              />
            
             </View>
             <View style={{marginHorizontal: '5%'}}>
             <Button
             onPress={()=> this.createRestorePoint()}
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
    formTitle:{
        marginTop: '5%',
        fontSize: 16
    },
    subView: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: Constants.app_color,
    },
    heading: {
        textAlign: 'center',
        fontSize: 24,
        fontFamily: "Avenir-Roman",
        color: "white"
    }
});