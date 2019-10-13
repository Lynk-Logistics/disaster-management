import React from 'react';
import { View, FlatList, StatusBar, StyleSheet, Image, Text, PermissionsAndroid, Platform, ToastAndroid, ImageBackground, TouchableHighlight, AsyncStorage } from 'react-native';
import Geolocation from 'react-native-geolocation-service';
import Constants from '../constants';
import {Header, Icon, CheckBox, Input} from 'react-native-elements';

export default class RescuePeople extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isMedicalEmergency: false,
            rescuePeople: []
        }
    }
    componentWillMount= async ()=> {
        console.log('Hello')

        const latitude = await AsyncStorage.getItem('latitude');
        const longitude = await AsyncStorage.getItem('longitude');
        const Coords = [parseFloat(latitude),parseFloat(longitude)];
        
        console.log(Coords,Constants.nearby_details)

        fetch(Constants.nearby_details, {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
            }, body: JSON.stringify({
                coordinates:Coords,
	            radius: 2000,
	            comment:"radius in meter"
            })
          }).then((response) => response.json())
          .then((responseJson) => {
            console.log(responseJson);
            if (responseJson.error == null) {
                this.setState({rescuePeople: responseJson.data.rescueRequests })
            }
          })
          .catch((error) => {
            console.error(error);
          });
          console.log('Done Fetching')
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
               centerComponent={{ text: 'Rescue People', style: { color: '#fff',fontFamily: "Avenir-Roman", fontSize: 17 } }}      
             />
 <FlatList 
             style={{flex:1}}
             data={this.state.rescuePeople}
             keyExtractor={item => item._id}
             renderItem={({ item, index }) => {
                    return(
                        <View style={{borderBottomColor: Constants.app_color, borderBottomWidth: 1.5, paddingTop:'8%'}}>
                            <View style={{flexDirection: 'row',borderBottomColor: Constants.app_color, borderBottomWidth: 1.5}}>
                             <View style={{flex: 0.75, borderBottomColor: Constants.app_color, borderBottomWidth: 0.5}}>
                                <Text style={{paddingLeft:'5%', fontFamily: "Avenir-Roman", fontSize: 16 }}>Person name: {item.name}</Text>
                                <Text style={{paddingLeft:'5%', fontFamily: "Avenir-Roman", fontSize: 14, color: 'grey' }}>Ph.no: {item.mobile}</Text>
                                <Text style={{paddingLeft:'5%', fontFamily: "Avenir-Roman", fontSize: 14, color: 'grey' }}>Distance: { Math.round(item.distance)}M</Text>
                             </View>
                             <View style={{flex: 0.25}}>
                                <TouchableHighlight underlayColor={'transparent'} style={{flex: 1,justifyContent:'center',alignItems:'center'}} onPress={()=>{
                                    let latitude = item.location.coordinates[0];
                                    let longitude = item.location.coordinates[1]
                                    let url = `https://www.google.com/maps/search/?api=1&query=${latitude},${longitude}`
                                    Linking.canOpenURL(url)
                                    .then((supported) => {
                                      if (!supported) {
                                        console.log("Can't handle url: " + url);
                                      } else {
                                        return Linking.openURL(url);
                                      }
                                    })
                                    .catch((err) => console.error('An error occurred', err));
                                }}>
                                 <Text style={{textAlign:'center', fontFamily: "Avenir-Roman", fontSize: 16, color: 'blue' }}>Tap to view location</Text>
                                </TouchableHighlight>
                             </View>
                            </View>
                        </View>
                    )
             }}
             
            />

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
    heading: {
        textAlign: 'center',
        fontSize: 24,
        fontFamily: "Avenir-Roman",
        color: "white"
    }
});