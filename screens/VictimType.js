import React from 'react';
import { View, StatusBar, StyleSheet, Image, Text, PermissionsAndroid, Platform, ToastAndroid, ImageBackground, TouchableHighlight } from 'react-native';
import Geolocation from 'react-native-geolocation-service';
import Constants from '../constants';
import {Header} from 'react-native-elements';

export default class PersonaType extends React.Component {
    constructor(props) {
        super(props);
    }
    componentWillMount() {

    }
    render() {
        return (
            <View style={styles.container}>
                <StatusBar backgroundColor={Constants.app_color} barStyle="light-content" />
                
                <TouchableHighlight
                    underlayColor='transparent'
                    style={{ flex: 0.3333333333}}
                    onPress={() => this.props.navigation.navigate('RequestRescue')}>
                    <ImageBackground style={{flex: 1}} source={require('../images/rescue-1.jpg')}>
                        <View style={{ flex: 1, backgroundColor: 'rgba(0,0,0,.6)', justifyContent: 'center', alignItems: 'center' }}>
                            <Text style={styles.heading}>
                                Request Rescue
                            </Text>
                        </View>
                    </ImageBackground>
                </TouchableHighlight>

                <TouchableHighlight
                    underlayColor='transparent'
                    style={{ flex: 0.3333333333 }}
                    onPress={() => this.props.navigation.navigate('RequestGoods')}>
                    <ImageBackground style={{ flex: 1 }} source={require('../images/goods.jpg')}>
                        <View style={{ flex: 1, backgroundColor: 'rgba(0,0,0,.6)', justifyContent: 'center', alignItems: 'center' }}>
                            <Text style={styles.heading}>
                                Request Goods
                            </Text>
                        </View>
                    </ImageBackground>
                </TouchableHighlight>

                <TouchableHighlight
                    underlayColor='transparent'
                    style={{ flex: 0.3333333333}}
                    onPress={() => this.props.navigation.navigate('ReportMissing')}>
                    <ImageBackground style={{flex: 1}} source={require('../images/report-missing.jpg')}>
                        <View style={{ flex: 1, backgroundColor: 'rgba(0,0,0,.6)', justifyContent: 'center', alignItems: 'center' }}>
                            <Text style={styles.heading}>
                                Report Missing
                            </Text>
                        </View>
                    </ImageBackground>
                </TouchableHighlight>

                
            </View>
        )
    }
    
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: Constants.app_color,
        flexDirection: 'column'
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