import React, { Component } from 'react';
import {
  Map as GoogleMap,
  GoogleApiWrapper,
  Polyline,
} from 'google-maps-react';
import { decodePolyline } from '../utils/map';
import { googleMapsApi } from '../config';
import styled from '@emotion/styled';

const StyleContainer = styled.div`
  min-height:${props =>
    props.minHeight ? props.minHeight : '200px'};
`;

class Map extends Component {
  constructor(props){
    super(props);
    this.state = {
      center: {lat: 13.08268, lng: 80.270721}
    }
  }
  componentWillReceiveProps(nextProps){
    console.log(`componentWillReceiveProps`)
    if(nextProps.center && JSON.stringify(this.state.center) !== JSON.stringify(nextProps.center)) {
      this.setState({center: nextProps.center})
      console.log(`refresh`)
      // this.forceUpdate();
    }
  }
  render() {
    let polyline = null;
    if(this.props.polyline){
      polyline = decodePolyline(this.props.polyline);
    }
    console.log(this.props)
    return (
      <StyleContainer {...this.props}>
        <GoogleMap
          className="map"
          google={this.props.google}
          style={{ height: '100%', position: 'relative', width: '100%' }}
          options={{
            center: this.state.center,
            zoom: 8
          }}
          zoom={14}
          {...this.props}
          onMapLoad={map => {
            alert('Hello')
            // if(this.state.locationCenter) {
            //   const marker = new window.google.maps.Marker({
            //   position: this.state.locationCenter,
            //   map: map,
            //   title: 'Hello World!'
            // });
            // } else {
            //   alert('Nope!')
            // }

          }}
        >
          {this.props.polyline && <Polyline
            fillColor="#0000FF"
            fillOpacity={0.35}
            path={polyline}
            strokeColor="#0000FF"
            strokeOpacity={0.8}
            strokeWeight={2}
          />}

          {this.props.children}

        </GoogleMap>
      </StyleContainer>
    );
  }
}

export default GoogleApiWrapper({
  apiKey: googleMapsApi,
})(Map);
