import React from 'react';
import { View, StatusBar, StyleSheet, Image, Text, PermissionsAndroid, Platform, ToastAndroid, ImageBackground, TouchableHighlight } from 'react-native';
import Geolocation from 'react-native-geolocation-service';
import Constants from '../constants';
import {Header, Icon} from 'react-native-elements';

export default class ReportMissing extends React.Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }
    componentWillMount() {

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
               centerComponent={{ text: 'Report Missing', style: { color: '#fff',fontFamily: "Avenir-Roman", fontSize: 17 } }}      
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