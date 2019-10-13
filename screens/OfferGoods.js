import React from 'react';
import { View, ScrollView, StatusBar, StyleSheet, Image, Text, PermissionsAndroid, Platform, ToastAndroid, ImageBackground, TouchableHighlight, FlatList } from 'react-native';
import Geolocation from 'react-native-geolocation-service';
import Constants from '../constants';
import {Header, Icon, Input, Button, Overlay} from 'react-native-elements';

export default class OfferGoods extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            goods: [],
            goodName: '',
            goodCount: 0,
            name: '',
            contact: '',
            isAdditionalDetailsModelVisible: false
        }
    }
    requestGoods = async() => {
        if (this.state.name === "" && this.state.contact == 0) {
            return alert('Please fill up the details')
        }
        const latitude = await AsyncStorage.getItem('latitude');
        const longitude = await AsyncStorage.getItem('longitude');
        const deviceToken = await AsyncStorage.getItem('notification_device_token');
        const notifcation_token = JSON.parse(deviceToken)
        const Coords = [parseFloat(latitude),parseFloat(longitude)];
        
        console.log(Coords,Constants.offer_goods, deviceToken)

        fetch(Constants.offer_goods, {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: this.state.name,
            coordinates: Coords,
            mobile: this.state.contact,
            deviceToken: notifcation_token.token,
            goods: this.state.goods
        }),
      }).then((response) => response.json())
      .then((responseJson) => {
        console.log(responseJson);
        if(responseJson.error == null){
            this.setState({isAdditionalDetailsModelVisible: false})
            alert('The volunteers will pickup your offerings, Thank You');
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
               leftComponent={<TouchableHighlight underlayColor={'transparent'} onPress={()=> this.props.navigation.goBack()}>
                   <Icon name="arrow-back" color="white"/>
               </TouchableHighlight>}
               centerComponent={{ text: 'Offer Goods', style: { color: '#fff',fontFamily: "Avenir-Roman", fontSize: 17 } }}      
             />
             <View style={styles.NewGoodView}>
                <View style={{flexDirection: 'row', marginTop: '2%'}}>
                    <View style={{flex: 0.75}}>
                    <Text style={{marginLeft: '5%'}}>
                        Enter Good name
                    </Text>
                    <Input 
                    value={this.state.goodName}
                     onChangeText={(name)=>this.setState({goodName: name})}
                     placeholder='Food, Blanket, Meds'
                     leftIcon={{ type: 'font-awesome', name: 'user', color: Constants.app_color }}
                     />
                    </View>

                   <View style={{flex: 0.25}}>
                   <Text style={{marginLeft: '5%'}}>
                        Enter count
                    </Text>
                   <Input
                     value={this.state.goodCount}
                     onChangeText={(count)=>this.setState({goodCount: count})}  
                     keyboardType='numeric' 
                     placeholder='0'
                     leftIcon={{ type: 'font-awesome', name: 'user', color: Constants.app_color }}
                     />
                   </View>
                     
                </View>
                <View style={{marginHorizontal: '5%', marginTop: '2%'}}>
                    <Button
                    onPress={()=> {
                        if (this.state.goodName === "" && this.state.goodCount === 0) {
                            return alert('Enter good name and amount')
                        }
                        this.setState({goods: [...this.state.goods, {name: this.state.goodName , count:this.state.goodCount}], goodName: '', goodCount: 0 })
                    }}
                    title="Add Good"
                    type="solid"
                    raised={true}
                    buttonStyle={{backgroundColor: Constants.app_color}}
                  />
                </View>
             </View>

            <ScrollView style={{flex: 0.6, paddingTop: '5%'}}>
                    {this.state.goods.map((item, index)=>{
                        return (
                            <View key={index} style={{flexDirection: 'row'}}>
                  <View style={{flex: 0.8}}>
                      <Text style={{paddingLeft:'5%', fontFamily: "Avenir-Roman", fontSize: 16 }}>{item.name}</Text>
                      <Text style={{paddingLeft:'5%', fontFamily: "Avenir-Roman", fontSize: 14, color: 'grey' }}>{item.count}</Text>
                  </View>
                  
                  <TouchableHighlight style={{flex: 0.2, backgroundColor: 'white', alignItems:'center', justifyContent:'center'}} underlayColor={'transparent'} 
                  onPress={()=> {
                    console.log(index)
                    let oldGoods = [... this.state.goods];
                    oldGoods.splice(index,1)
                    this.setState({goods: oldGoods})
                  }}>
                   <Icon name="delete" color="red"/>
               </TouchableHighlight>
              </View>
                        )
                    })}
            </ScrollView>

            <Button
                    onPress={()=> {
                       
                    }}
                    title="Submit"
                    type="solid"
                    raised={true}
                    buttonStyle={{backgroundColor: Constants.app_color}}
                  />

<Overlay
              isVisible={this.state.isAdditionalDetailsModelVisible}
              onBackdropPress={() => this.setState({ isAdditionalDetailsModelVisible: false })}
            >
                <Text style={[styles.formTitle,{fontSize: 18, textAlign: 'center', marginBottom: '5%'}]}> Give us your info to reach you fast</Text>
                <View style={{marginHorizontal: '5%', marginBottom: '10%' }}>
             <Text style={styles.formTitle}>
                 Enter your name
             </Text>
              <Input
                value={this.state.name}
                onChangeText={(name)=>this.setState({name})}
                placeholder='John Doe'
                leftIcon={{ type: 'font-awesome', name: 'user', color: Constants.app_color }}
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
                <View style={{marginTop: '15%'}}>
                <Button
                    onPress={()=> {
                        this.requestGoods()
                    }}
                    title="Submit"
                    type="solid"
                    raised={true}
                    buttonStyle={{backgroundColor: Constants.app_color}}
                  />
                </View>
            
             </View>
              
            </Overlay>
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