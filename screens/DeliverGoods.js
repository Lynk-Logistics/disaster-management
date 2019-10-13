import React from 'react';
import { View, StatusBar, StyleSheet,AsyncStorage, Image, Text, PermissionsAndroid, Platform, ToastAndroid, ImageBackground, TouchableHighlight, FlatList, Linking } from 'react-native';
import Geolocation from 'react-native-geolocation-service';
import Constants from '../constants';
import {Header, Icon} from 'react-native-elements';
import SegmentedControlTab from "react-native-segmented-control-tab";

export default class DeliverGoods extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedIndex: 0,
            offeredGoods: [],
            requestedGoods: []
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
                this.setState({offeredGoods: responseJson.data.offerGoodsRequest ,requestedGoods: responseJson.data.goodsRequest})
            }
          })
          .catch((error) => {
            console.error(error);
          });
          console.log('Done Fetching')
    }
    handleIndexChange = index => {
        this.setState({
          ...this.state,
          selectedIndex: index
        });
    };
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
               centerComponent={{ text: 'Deliver Goods', style: { color: '#fff',fontFamily: "Avenir-Roman", fontSize: 17 } }}      
             />
             <View style={{marginHorizontal: '5%', marginTop:'5%'}}>
             <SegmentedControlTab
              tabStyle={{ borderWidth: 1, borderColor: Constants.app_color}}
              values={["Who wants", "Who offers"]}
              activeTabStyle={{backgroundColor:Constants.app_color}}
              activeTabTextStyle={styles.heading}
              tabTextStyle={[styles.heading,{color:Constants.app_color}]}
              selectedIndex={this.state.selectedIndex}
              onTabPress={this.handleIndexChange}
            />
             </View>
            
            <FlatList 
             style={{flex:1}}
             data={this.state.selectedIndex == 0 ? this.state.requestedGoods : this.state.offeredGoods}
             keyExtractor={item => item._id}
             renderItem={({ item, index }) => {
                 if (this.state.selectedIndex == 0){
                    const goodsList = item.goods.map((good, index)=>
                    <View key={index} style={{flexDirection:'row', borderBottomColor: Constants.app_color, borderBottomWidth: 0.5}}>
                        <Text style={{paddingLeft:'5%', width: '75%', fontFamily: "Avenir-Roman", fontSize: 16 }}>{good.name}</Text>
                        <Text style={{paddingLeft:'5%', width: '25%', fontFamily: "Avenir-Roman", fontSize: 14, color: 'grey' }}>{good.count}</Text>
                    </View>)
                    return(
                        <View style={{borderBottomColor: Constants.app_color, borderBottomWidth: 1.5, paddingTop:'8%'}}>
                            <View style={{flexDirection: 'row',borderBottomColor: Constants.app_color, borderBottomWidth: 1.5}}>
                             <View style={{flex: 0.75, borderBottomColor: Constants.app_color, borderBottomWidth: 0.5}}>
                                <Text style={{paddingLeft:'5%', fontFamily: "Avenir-Roman", fontSize: 16 }}>Person name: {item.name}</Text>
                                <Text style={{paddingLeft:'5%', fontFamily: "Avenir-Roman", fontSize: 14, color: 'grey' }}>Ph.no: {item.mobile}</Text>
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
                            <Text style={{paddingLeft:'5%', width: '100%', fontFamily: "Avenir-Roman", fontSize: 16, backgroundColor:'#c8e6c9' }}>Goods wanted by the victim</Text>
                            {goodsList}
                        </View>
                    )
                 }else{
                    const goodsList = item.goods.map((good, index)=>
                    <View key={index} style={{flexDirection:'row', borderBottomColor: Constants.app_color, borderBottomWidth: 0.5}}>
                        <Text style={{paddingLeft:'5%', width: '75%', fontFamily: "Avenir-Roman", fontSize: 16 }}>{good.name}</Text>
                        <Text style={{paddingLeft:'5%', width: '25%', fontFamily: "Avenir-Roman", fontSize: 14, color: 'grey' }}>{good.count}</Text>
                    </View>)
                    return(
                        <View style={{borderBottomColor: Constants.app_color, borderBottomWidth: 1.5, paddingTop:'5%'}}>
                            <View style={{flexDirection: 'row',  borderBottomColor: Constants.app_color, borderBottomWidth: 0.5}}>
                             <View style={{flex: 0.75}}>
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
                            <Text style={{paddingLeft:'5%', width: '100%', fontFamily: "Avenir-Roman", fontSize: 16, backgroundColor:'#c8e6c9' }}>Goods offered</Text>
                            {goodsList}
                        </View>
                    )
                 }
                 
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
        fontSize: 18,
        fontFamily: "Avenir-Roman",
        color: "white"
    }
});