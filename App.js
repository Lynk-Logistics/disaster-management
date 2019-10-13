
import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, PushNotificationIOS, ImageBackground, StatusBar } from 'react-native';
import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';
import SplashScreen from './screens/SplashScreen';
import PersonaTypeScreen from './screens/PersonaType';
import VictimTypeScreen from './screens/VictimType';
import VolunteerTypeScreen from './screens/VolunteerType';
import RequestRescueScreen from './screens/RequestRescue';
import RequestGoodsScreen from './screens/RequestGoods';
import ReportMissingScreen from './screens/ReportMissing';
import OfferAccomodationScreen from './screens/OfferAccomodation';
import OfferGoodsScreen from './screens/OfferGoods';
import RescuePeopleScreen from './screens/RescuePeople';
import DeliverGoodsScreen from './screens/DeliverGoods';

const AppNavigator = createStackNavigator({
  Splash: {
    screen: SplashScreen,
  },
  PersonaType: {
    screen: PersonaTypeScreen
  },
  Victim: {
    screen: VictimTypeScreen
  },
  Volunteer: {
    screen: VolunteerTypeScreen
  },
  RequestRescue: {
    screen: RequestRescueScreen
  },
  RequestGoods: {
    screen: RequestGoodsScreen
  },
  ReportMissing: {
    screen: ReportMissingScreen
  },
  OfferAccomodation: {
    screen: OfferAccomodationScreen
  },
  OfferGoods: {
    screen: OfferGoodsScreen
  },
  RescuePeople:{
    screen: RescuePeopleScreen
  },
  DeliverGoods:{
    screen: DeliverGoodsScreen
  }
},{
  initialRouteName: 'Splash',
  headerMode: 'none'
});

export default createAppContainer(AppNavigator);

