import React , {Component} from 'react';
import {Header} from '../home/header';
import { MainWithGeoLoc} from './getLocation';

class Feed extends Component {

    render(){
        return(
            <div>
                <Header page="feed" />
                <MainWithGeoLoc />
            </div>
        )
    }
}

export default Feed;