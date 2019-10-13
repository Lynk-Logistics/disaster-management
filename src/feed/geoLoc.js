import React from 'react';
import {Button, Input, message} from 'antd';
import constants from "../constant";
import 'antd/dist/antd.css'; 
const axios = require('axios').default;
 
class Geoloc extends React.Component {
    state={
        
            lat :"",
            lng: "",
            name: "",
            contact:"",
            capacity:""
        
    }
    
    setLocation = (lat,lng) => {
        this.setState({
            lat: lat,
            lng: lng
        }, () => this.registerLocation())
    }

    registerLocation = () => {
        axios.post(constants.set_service_Zone, {
            name : this.state.name,
            coordinates:[this.state.lat, this.state.lng],
            mobile: this.state.contact,
            capacity: this.state.capacity
        })
        .then(res => {
            if(res.data.error){
                message.error("can't fetch data")                    
            }
            else {
            message.success("Successfully registered the service point")                
              console.log(res.data.data)
              this.cancelRegister();
            }
        })
        .catch(err => console.log("error", err))
    }

    onChangeHandler = event => {
        this.setState({
            [event.target.name] : event.target.value
        })
    }

    cancelRegister = () => {
        this.setState({
            lat :"",
            lng: "",
            name: "",
            contact:""
        }, () => this.props.handleCancel())
    }
  render() {
    return !this.props.isGeolocationAvailable
      ? <div>Your browser does not support Geolocation</div>
      : !this.props.isGeolocationEnabled
        ? <div>Geolocation is not enabled</div>
        : this.props.coords
          ? <div style={{textAlign:"justify"}}><table>
            <tbody>
              <tr><td>Latitude:</td><td>{this.props.coords.latitude}</td></tr>
              <tr><td>Longitude:</td><td>{this.props.coords.longitude}</td></tr>
            </tbody>
          </table>
          <label>Service point Name:</label>&nbsp;&nbsp;
          <Input type="text" value={this.state.name} name="name" placeholder="Building Name" onChange={this.onChangeHandler} 
          style={{width:"250px"}} />
          <br />
          <br />
          <label>Contact:</label>&nbsp;&nbsp;
            <Input type="number" value={this.state.contact} name="contact" placeholder="contact number" onChange={this.onChangeHandler} style={{width:"250px"}} />
            <br />
            <br />
            <label>capacity:</label>&nbsp;&nbsp;
            <Input type="number" value={this.state.capacity} name="capacity" placeholder="Room capacity" onChange={this.onChangeHandler} style={{width:"250px"}} />
            <br />
            <br />
            <Button type="primary" onClick={() => this.setLocation(this.props.coords.latitude, this.props.coords.longitude)}>
                Publish this location as Service Point
            </Button> &nbsp;&nbsp;
            <Button type="danger" onClick = {this.cancelRegister}>Cancel</Button>
          </div>
          : <div>Getting the location data</div>;
  }
}
 
export default Geoloc;