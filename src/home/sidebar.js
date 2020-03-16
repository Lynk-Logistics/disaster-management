import React , {Component} from 'react';
import { Card ,message, Tag} from 'antd';
import constants from "../constant";
import 'antd/dist/antd.css'; 
const axios = require('axios').default;



class SideBar extends Component{

    constructor(props){
        super(props);
        this.callApi();
    }

    callApi = () => {
        axios.get(constants.get_list_of_regions)
            .then(res => {
                if(res.data.error){
                    message.error("can't fetch data")                    
                }
                else {
                    this.setState({
                        regions : res.data.data
                    })
                }
            })
            .catch(err => console.log("error", err))
    }
 
    state={
        regions:[
            
        ],
        selectedRegion : ""
    }
    
    getTagColor = severity =>{
        let color = "#108ee9";
        switch(severity){
            case "severe":
                color = "#f50";
                break;
            case "moderate":
                color = "#108ee9";
                break;
            case "safe":
                color = "#87d068";
                break;
            default:
                color = "#108ee9";
                break;
        }
        return color;
    }

    selectRegion = data => {
        this.setState({
            selectedRegion: data
        }, () => this.props.selectRegion(data))
    }
        
    render(){
        return(
            <div style={{overflow:"auto", height: "inherit"}}>
                {
                    this.state.regions.map((data, i) => (
                    <span key={i} onClick={() => this.selectRegion(data) } >
                    <Card key={data._id} 
                        size="small" 
                        title={data.name} 
                        className={this.state.selectedRegion && (this.state.selectedRegion._id === data._id) ? "selectedCard" :"RegionCard"}
                        hoverable={true}                       
                      >
                        <Tag color={this.getTagColor(data.severity)}>{data.severity}</Tag>                      
                    </Card>
                    </span>
                    ))
                }
               
            </div>
        )
    }
}



export default SideBar;
