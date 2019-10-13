import React,{useState,useEffect} from 'react';
import './styles.scss';
import TrackStatus from './../TrackStatus/TrackStatus';
import axios from 'axios';
import {Redirect} from 'react-router-dom';

function TasksList(props) {

    //console.log('props',props);
    const [state,setState] = useState(null);

    const [redirect,setRedirect] = useState(null);

    const [requestList,setRequestList] = useState(null);

    /*let requestList = [{
        request_id: 4,
        need: 'goods',
        sub_need: 'clothes'
    },{
        request_id: 5,
        need: 'goods',
        sub_need : 'food'
    }

];*/
 
    useEffect(()=> {
      let id = localStorage.getItem('userId');
      axios.get('/victim/'+id+'/request')
      .then((response)=> {
        setRequestList(response.request_list);
      }).catch((e)=> {
          setRedirect(<Redirect to={{
              pathname: '/error'
          }} />);
      })
    },[]);

    const handleClick = (id)=> {
        setState({reqid: id});
    }
  
                    

  return (
      <div>
      {state === null ? 
    <div className="tasks-list">
        <div className = 'task-container' >
           {requestList.map((item,index)=> <div className = 'task' onClick={()=>handleClick(item.request_id)}>View Status of Your Requirement for {item.need === 'goods' ? item.sub_need : item.need}</div>)} 
        </div>
    </div>
      :
      <TrackStatus requestId = {state.reqid} />
      }
      </div>
  );
}

export default TasksList;
