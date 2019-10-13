import React from 'react';
import { View, StatusBar, AsyncStorage,StyleSheet, Image, Text, PermissionsAndroid, Platform, ToastAndroid, ImageBackground, TouchableHighlight } from 'react-native';
import Geolocation from 'react-native-geolocation-service';
import Constants from '../constants';
import {Header} from 'react-native-elements';

export default class PersonaType extends React.Component {
    constructor(props) {
        super(props);
    }
    hasLocationPermission = async () => {
        if (Platform.OS === 'ios' ||
            (Platform.OS === 'android' && Platform.Version < 23)) {
            return true;
        }

        const hasPermission = await PermissionsAndroid.check(
            PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION
        );

        if (hasPermission) return true;

        const status = await PermissionsAndroid.request(
            PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION
        );

        if (status === PermissionsAndroid.RESULTS.GRANTED) return true;

        if (status === PermissionsAndroid.RESULTS.DENIED) {
            ToastAndroid.show('Location permission denied by user.', ToastAndroid.LONG);
        } else if (status === PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN) {
            ToastAndroid.show('Location permission revoked by user.', ToastAndroid.LONG);
        }

        return false;
    }
    componentWillMount() {

    }
    render() {
        return (
            <View style={styles.container}>
                <StatusBar backgroundColor={Constants.app_color} barStyle="light-content" />
                
                <TouchableHighlight
                    underlayColor='transparent'
                    style={{ flex: 0.5}}
                    onPress={() => this.props.navigation.navigate('Volunteer')}>
                    <ImageBackground style={{flex: 1}} source={require('../images/i-can-help.jpg')}>
                        <View style={{ flex: 1, backgroundColor: 'rgba(0,0,0,.6)', justifyContent: 'center', alignItems: 'center' }}>
                            <Text style={styles.heading}>
                                I can help
                            </Text>
                        </View>
                    </ImageBackground>
                </TouchableHighlight>
                <TouchableHighlight
                    underlayColor='transparent'
                    style={{ flex: 0.5 }}
                    onPress={() => this.props.navigation.navigate('Victim')}>
                    <ImageBackground style={{ flex: 1 }} source={require('../images/i-need-help.jpg')}>
                        <View style={{ flex: 1, backgroundColor: 'rgba(0,0,0,.6)', justifyContent: 'center', alignItems: 'center' }}>
                            <Text style={styles.heading}>
                                I need help
                            </Text>
                        </View>
                    </ImageBackground>
                </TouchableHighlight>
            </View>
        )
    }
    componentDidMount = async () => {
        let hasLocationPermission = this.hasLocationPermission();
        
        if (hasLocationPermission) {
            Geolocation.getCurrentPosition(
                (position) => {
                    console.log(position);
                

                    AsyncStorage.setItem('latitude', JSON.stringify(position.coords.latitude));
                    AsyncStorage.setItem('longitude', JSON.stringify(position.coords.longitude));

                   

                    this.setState({
                        latitude: position.coords.latitude,
                        longitude: position.coords.longitude
                    })
                },
                (error) => {
                    // See error code charts below.
                    console.log(error.code, error.message);
                },
                { enableHighAccuracy: true, timeout: 15000, maximumAge: 10000 }
            );
        }
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