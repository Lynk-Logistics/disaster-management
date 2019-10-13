import React , {Component} from "react";
import GoogleMapReact  from 'google-map-react';
import { Icon } from 'antd';

const Marker = props => (
      <Icon
        // onClick={async () => {
        //   props.filterData(props.responseId);
        // }}
        type='exclamation-circle' 
        style={{ color: "red", fontSize: 15 }}
        theme='filled'
      />
  );

class MapView extends Component {

    static defaultProps = {
        center: {
          lat: 59.95,
          lng: 30.33
        },
        zoom: 11
      };
      

    render(){
        const locationArr = this.props.region.location ? this.props.region.location.coordinates : null;
        console.log("map",this.props.region.location? this.props.region.location.coordinates : "waiting")
        return(
            <GoogleMapReact
                bootstrapURLKeys={{ key: "AIzaSyC3_Kdx6Wr2gO-wKnOBiproKKhgaTdIVAg" }}
                defaultCenter={{
                    lat: 13.00032,
                    lng: 80.21624
                    }} 
                defaultZoom={12}
                >
                    {
                      locationArr ?
                        locationArr[0].map((data,index) => {
                        console.log(data)
                        return <Marker key={index} 
                        data ={data}
                        lat={data[0]}
                        lng={data[1]}
                        draggable = {true}
                    />})
                        :null
                    }                   
            </GoogleMapReact> 
        )
    }
}

export default MapView;