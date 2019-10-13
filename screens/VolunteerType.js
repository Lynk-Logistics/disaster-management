import React from 'react';
import { View, StatusBar, StyleSheet, Image, Text, PermissionsAndroid, Platform, ToastAndroid, ImageBackground, TouchableHighlight } from 'react-native';
import Geolocation from 'react-native-geolocation-service';
import Constants from '../constants';
import {Header} from 'react-native-elements';

export default class VolunteerType extends React.Component {
    constructor(props) {
        super(props);
    }
    componentWillMount() {

    }
    render() {
        return (
            <View style={styles.container}>
                <StatusBar backgroundColor={Constants.app_color} barStyle="light-content" />
                
                <View style={styles.subView}>
                    <Text style={styles.heading}>
                        Offer
                    </Text>
                    <View style={{flex:1, flexDirection: 'row'}}>
                        <TouchableHighlight
                            underlayColor='transparent'
                            style={{ flex: 0.5 }}
                            onPress={() => this.props.navigation.navigate('OfferAccomodation')}>
                            <ImageBackground style={{ flex: 1 }} source={require('../images/accomodation.jpg')}>
                                <View style={{ flex: 1, backgroundColor: 'rgba(0,0,0,.6)', justifyContent: 'center', alignItems: 'center' }}>
                                    <Text style={styles.cardText}>
                                        Accomodation
                                    </Text>
                                </View>
                            </ImageBackground>
                        </TouchableHighlight>

                        <TouchableHighlight
                            underlayColor='transparent'
                            style={{ flex: 0.5 }}
                            onPress={() => this.props.navigation.navigate('OfferGoods')}>
                            <ImageBackground style={{ flex: 1 }} source={require('../images/goods-2.jpg')}>
                                <View style={{ flex: 1, backgroundColor: 'rgba(0,0,0,.6)', justifyContent: 'center', alignItems: 'center' }}>
                                    <Text style={styles.cardText}>
                                        Goods
                                    </Text>
                                </View>
                            </ImageBackground>
                        </TouchableHighlight>
                    </View>
                </View>
                <View style={styles.subView}>
                    <Text style={styles.heading}>
                        Help
                    </Text>
                    <View style={{flex:1, flexDirection: 'row'}}>
                        <TouchableHighlight
                            underlayColor='transparent'
                            style={{ flex: 0.5 }}
                            onPress={() => this.props.navigation.navigate('RescuePeople')}>
                            <ImageBackground style={{ flex: 1 }} source={require('../images/rescue-2.jpg')}>
                                <View style={{ flex: 1, backgroundColor: 'rgba(0,0,0,.6)', justifyContent: 'center', alignItems: 'center' }}>
                                    <Text style={styles.cardText}>
                                        Rescue People
                                    </Text>
                                </View>
                            </ImageBackground>
                        </TouchableHighlight>

                        <TouchableHighlight
                            underlayColor='transparent'
                            style={{ flex: 0.5 }}
                            onPress={() => this.props.navigation.navigate('DeliverGoods')}>
                            <ImageBackground style={{ flex: 1 }} source={require('../images/goods.jpg')}>
                                <View style={{ flex: 1, backgroundColor: 'rgba(0,0,0,.6)', justifyContent: 'center', alignItems: 'center' }}>
                                    <Text style={styles.cardText}>
                                        Deliver Goods
                                    </Text>
                                </View>
                            </ImageBackground>
                        </TouchableHighlight>
                    </View>
                </View>

            </View>
        )
    }

}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        //backgroundColor: Constants.app_color,
    },
    subView: {
        flex: 0.5,
        // alignItems: 'center',
        // justifyContent: 'center',
        // backgroundColor: Constants.app_color,
    },
    heading:{
        height: '12%',
        width: '100%',
        textAlign: 'center',
        backgroundColor:'white',
        fontSize: 24,
        fontFamily: "Avenir-Roman",
        color: 'black'
    },
    cardText: {
        textAlign: 'center',
        fontSize: 24,
        fontFamily: "Avenir-Roman",
        color: "white"
    }
});