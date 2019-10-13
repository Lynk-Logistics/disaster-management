import React, {Component} from 'react';
import {Card} from 'antd';


class InfoCard extends Component {

    renderCard = (data, type) => {
        switch(type){
            case "restorePoints":
                return <Card title={data.name} style={{ width: 250 , backgroundColor: "green", color:"white"}}>
                <p>Can Accomodate: {data.capacity}</p>
                <p>Contact: {data.mobile}</p>
                <p>status : {data.active ? "Active" : "Inactive"}</p>
              </Card>
            
            case "rescueRequest": 
                return <Card title={data.name} style={{ width: 250 , backgroundColor: "red",color:"white" }}>
                <p>Can Accomodate: {data.capacity}</p>
                <p>Number of Victims: {data.activeRequest.count}</p>
                <p>Contact: {data.mobile}</p>
                <p>Need Medical Assistance: {data.activeRequest.medicalSupport ? "Yes" : "No"}</p>
                <p>status : {data.activeRequest.active ? "Active" : "Inactive"}</p>
              </Card>
            case "offersGoods":
                return <Card title={data.name} style={{ width: 250, backgroundColor: "#3d5afe", color:"white" }}>
                <p>Contact: {data.mobile}</p>
                <p>status : {data.active ? "Active" : "Inactive"}</p>
                <h5>Goods offered</h5>
                <table>
                    <tbody>
                    {
                        data.goods.map((item,index) =>
                        <tr key={index}>
                            <td>{item.name}</td>
                            <td>{item.count}</td>
                        </tr> 
                        )
                    }
                    </tbody>
                </table>
                </Card>
            case "requestGoods":
                    return <Card title={data.name} style={{ width: 250 , backgroundColor:"#E91E63", color:"white"}}>
                    <p>Contact: {data.mobile}</p>
                    <p>status : {data.active ? "Active" : "Inactive"}</p>
                    <h5>Goods Needed:</h5>
                    <table>
                        <tbody>
                        {
                            data.goods.map((item,index) =>
                            <tr key={index}>
                                <td>{item.name}</td>
                                <td>{item.count}</td>
                            </tr> 
                            )
                        }
                        </tbody>
                    </table>
                    </Card>
            case "volunteers":
                    return <Card title={data.name} style={{ width: 250 , backgroundColor:"#FF5722" , color:"white"}}>
                    <p>Contact: {data.mobile}</p>
                    <p>status : {data.active ? "Active" : "Inactive"}</p>
                    </Card>
            default:
                return <Card title={data.name} style={{ width: 200 }}>
                <p>Can volunteer</p>
                <p>Capacity: {data.capacity}</p>
                <p>Contact: {data.mobile}</p>
                <p>status : {data.active ? "Active" : "Inactive"}</p>
              </Card>
        }
    }

    render(){
        const {data, type} = this.props;
        console.log(data)
        return(
            this.renderCard(data,type)
        )
    }
}


export default InfoCard;
