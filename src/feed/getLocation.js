import React from 'react';
import {geolocated} from 'react-geolocated';
import Geoloc from './geoLoc';
import GoogleMapReact  from 'google-map-react';
import constants from "../constant";
import InfoCard from './infoCard';  
import {Modal, Button, Row, Col , Icon, message , Slider, Tooltip, Checkbox}  from 'antd';

const axios = require('axios').default;


const Marker = props => (
  <Tooltip  title={props.data.name}>
    {
      props.type==="restorePoints"?
    <Icon
      onClick={async () => {
        props.setSelectedMarker(props.data, props.type);
      }}
      type='home' 
      style={{ color: "green", fontSize: 15 }}
      theme='filled'
    />:
    props.type==="rescueRequest" ?
    <Icon
    onClick={async () => {
      props.setSelectedMarker(props.data, props.type);
    }}
    type='info-circle' 
    style={{ color: "red", fontSize: 20 }}
    theme='filled'
  />:
  props.type === "offersGoods"?
    <Icon
    onClick={async () => {
      props.setSelectedMarker(props.data, props.type);
    }}
    type='carry-out' 
    style={{ color: "#3d5afe", fontSize: 15 }}
    theme='filled'
  />: 
  props.type === "requestGoods" ? <Icon
  onClick={async () => {
    props.setSelectedMarker(props.data, props.type);
  }}
  type='shopping' 
  style={{ color: "#E91E63", fontSize: 15 }}
  theme='filled'
/>:

  props.type === "volunteers"?
    <Icon
    onClick={async () => {
      props.setSelectedMarker(props.data, props.type);
    }}
    type='thunderbolt' 
    style={{ color: "#FF5722", fontSize: 15 }}
    theme='filled'
  />
  :null
    }
    </Tooltip>
);


const CheckboxGroup = Checkbox.Group;
const plainOptions = ['Rescue Requests', 'Restore Points', 'Offer Goods', 'Goods Requests', 'Volunteers'];
const defaultCheckedList = ['Rescue Requests', 'Restore Points'];

class Main extends React.Component {

        state = { 
            visible: false,
            radius : 50,
            rangeData : null,
            selectedPlace : null,
            showVictims: true,
            showDonators: true,
            showVolunteers: true,
            showAccomodations : true,
            checkedList: defaultCheckedList,
            indeterminate: true,
            checkAll: false,
        };

        showModal = () => {
          this.setState({
            visible: true,
          });
        };


  onChange = checkedList => {
    this.setState({
      checkedList,
      indeterminate: !!checkedList.length && checkedList.length < plainOptions.length,
      checkAll: checkedList.length === plainOptions.length,
    });
  };

  onCheckAllChange = e => {
    this.setState({
      checkedList: e.target.checked ? plainOptions : [],
      indeterminate: false,
      checkAll: e.target.checked,
    });
  };

        componentDidUpdate(){
            // console.log( "UPDATE",this.props.coords)
            if(!this.state.rangeData){
              if(this.props.coords){
                this.getNearbyLocations(this.props.coords)
              }
            }
        }  
        
        setSelectedMarker = (data, type) => {
          this.setState({
            selectedPlace : data,
            selectedType: type
          })
        }
      
        getNearbyLocations = (coords) => {
            axios.post(constants.get_nearby_details, {
            coordinates:[coords.latitude, coords.longitude],
            radius: this.state.radius * 1000
        })
        .then(res => {
            if(res.data.error){
                message.error("can't fetch data")                    
            }
            else {
              this.setState({
                rangeData: res.data.data
              })           
            }
        })
        .catch(err => console.log("error", err))
        }
      
        handleCancel = e => {
          this.setState({
            visible: false,
          });}
        
       
          onAfterChange = e => {
            this.setState({
              radius : e
            },() => this.getNearbyLocations(this.props.coords) )
          }
      
  render() {
     const  restorePoints = this.state.rangeData ? this.state.rangeData.restorePoints : null;
     const  rescueRequests = this.state.rangeData ? this.state.rangeData.rescueRequests : null;
     const  offerGoodsRequest = this.state.rangeData ? this.state.rangeData.offerGoodsRequest : null;
     const goodsRequest = this.state.rangeData ? this.state.rangeData.goodsRequest : null;
     const volunteers = this.state.rangeData ? this.state.rangeData.volunteers : null;

     const centerPoint = restorePoints && restorePoints.length ? {
       lat : restorePoints[0].location.coordinates[0],
       lng : restorePoints[0].location.coordinates[1]
     } : {
        lat: 13.00032,
        lng: 80.21624
     }
    return (
      <div style={{padding:"2%", height:"91vh"}}>
        <Row style={{height:"inherit"}}>
            <Col span={8}>
            {
              this.props.coords?
              <div>
                <label>Select Range</label>
                <Slider
                step={10}
                min={50}
                onAfterChange={this.onAfterChange} 
                />
              </div> : null
            }
            <div>
          <div style={{ borderBottom: '1px solid #E9E9E9' }}>
              <Checkbox
                indeterminate={this.state.indeterminate}
                onChange={this.onCheckAllChange}
                checked={this.state.checkAll}
              >
                Check all
              </Checkbox>
            </div>
            <br />
            <CheckboxGroup
              options={plainOptions}
              value={this.state.checkedList}
              onChange={this.onChange}
            />
          </div>
              {
                this.state.rangeData && this.state.rangeData.rescueRequests ? null :
                <Button type="primary" onClick={this.showModal} style={{marginTop:"20px"}}>
                  Register as Service Point
                </Button>
              }
             &nbsp;&nbsp; <Button type="primary" onClick={this.showModal} style={{marginTop:"20px"}}>
                  Register as Service Point
                </Button>
                <Modal
                title="Register as  service Point"
                visible={this.state.visible}
                onCancel={this.handleCancel}
                footer={null}>
                    <Geoloc {...this.props} handleCancel = {this.handleCancel} />       
                </Modal>
                <hr />
              {
                this.state.selectedPlace ? <InfoCard data={this.state.selectedPlace} type={this.state.selectedType}  style={{paddingTop:"20px"}} /> : null 
              }
              
            </Col>
            <Col span={16} style={{height:"100%", backgroundColor:"black"}}>
            <GoogleMapReact
                bootstrapURLKeys={{ key: " YOUR_API_KEY_HERE" }}
                defaultCenter={centerPoint} 
                defaultZoom={12}
                >
                    {
                      this.state.checkedList.includes("Restore Points") && restorePoints && restorePoints.length ?
                        restorePoints.map((data,index) => {
                        return <Marker key={index} 
                        data ={data}
                        type="restorePoints"
                        setSelectedMarker = {this.setSelectedMarker}
                        lat={data.location.coordinates[0]}
                        lng={data.location.coordinates[1]}
                    />})
                        :null
                    }
                     
                    {
                      this.state.checkedList.includes("Rescue Requests") && rescueRequests && rescueRequests.length ?
                      rescueRequests.map((data,index) => {
                          return <Marker key={index} 
                          type="rescueRequest"
                          data ={data}
                          setSelectedMarker = {this.setSelectedMarker}
                          lat={data.location.coordinates[0]}
                          lng={data.location.coordinates[1]}
                      />})
                          :null
                    }
                     {
                      this.state.checkedList.includes("Offer Goods") && offerGoodsRequest && offerGoodsRequest.length ?
                      offerGoodsRequest.map((data,index) => {
                          return <Marker key={index} 
                          data ={data}
                          type="offersGoods"
                          setSelectedMarker = {this.setSelectedMarker}
                          lat={data.location.coordinates[0]}
                          lng={data.location.coordinates[1]}
                      />})
                          :null
                    }

                    {
                      this.state.checkedList.includes("Goods Requests") && goodsRequest && goodsRequest.length ?
                      goodsRequest.map((data,index) => {
                          return <Marker key={index} 
                          data ={data}
                          type="requestGoods"
                          setSelectedMarker = {this.setSelectedMarker}
                          lat={data.location.coordinates[0]}
                          lng={data.location.coordinates[1]}
                      />})
                          :null
                    }

                    {
                      this.state.checkedList.includes("Volunteers") && volunteers && volunteers.length ?
                      volunteers.map((data,index) => {
                          return <Marker key={index} 
                          data ={data}
                          type="volunteers"
                          setSelectedMarker = {this.setSelectedMarker}
                          lat={data.location.coordinates[0]}
                          lng={data.location.coordinates[1]}
                      />})
                          :null
                    }



            </GoogleMapReact> 

            </Col>
        </Row>
      </div>
    );
  }
}

export const MainWithGeoLoc = geolocated({
  positionOptions: {
    enableHighAccuracy: false,
  },
  userDecisionTimeout: 5000,
})(Main);

