import React , {Component} from 'react';
import { Row, Col} from 'antd';
import 'antd/dist/antd.css'; 
import {Header} from './header';
import './home.css'
import SideBar from './sidebar';
import MapView from '../mapView';

class Home extends Component {

      state = {
          selectedRegion : ""
      }

      selectRegion = data => {
          this.setState({
              selectedRegion : data
          })
      }

    render(){
        return(
            <div style={{height:"100vh"}}>
                 <Header page="home" />   
                <Row className="appBody">
                    <Col span={8} className="sideBar">
                        <div style={{height:"inherit"}} >
                            <SideBar  selectRegion = {this.selectRegion}/>
                        </div >
                    </Col>
                    <Col span={16} className="regionBar">
                        <div style={{height:"inherit"}}>
                            <MapView region={this.state.selectedRegion}/>
                        </div >
                    </Col>
                </Row>
            </div>
        )
    }
}

export default Home;