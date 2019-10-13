import React from 'react';
import { Row, Col} from 'antd';
import 'antd/dist/antd.css'; 

export const Header = props => {
    if(props.page === "home"){
        return(
            <Row className="appHeader">
                    <Col span={4} style={{height:"50px"}}>
                        <div className="title">
                            <p>LynK Aid</p>
                        </div>
                    </Col>
                    <Col span={16}>
                    </Col>
                    <Col span={4} className="feedLink">
                        <div >
                            <a href="/" style={{color:"white", fontWeight:"bold"}}> Go to your Zone </a> 
                        </div>
                    </Col>
                </Row> 
        )
    }
    return(
        <Row  className="appHeader">
                    <Col span={4} style={{height:"50px"}}>
                        <div className="title">
                            <p>LynK Aid</p>
                        </div>
                    </Col>
                    <Col span={16}>
                    </Col>
                    <Col span={4} className="feedLink">
                        <div >
                            <a href="/zone" style={{color:"white", fontWeight:"bold"}}> see all zones </a> 
                        </div>
                    </Col>
                </Row> 
    )
}