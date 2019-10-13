import React,{useState,useEffect} from 'react';
import './styles.scss';
import PostRequirement from './../PostRequirements/PostRequirement';
import TrackStatus from './../TrackStatus/TrackStatus';
import TrackList from './../TasksList/TasksList';
import axios from 'axios';
import {Redirect} from 'react-router-dom';

function Home(props) {

  const [posts,setPosts] = useState(null);

  const [redirect,setRedirect] = useState(null);

  useEffect(()=> {
    axios.get('/get_posts')
    .then((response)=>{
        console.log(response);
        setPosts(response.victims);
      })
    .catch((error)=> {
        setRedirect(<Redirect to={{
           pathname: '/error'
        }} />);
    });
  },[]);

  /*[{
    name: 'hdggd',
    need: 'djhf',
    location: 'hdh'
 }, {
    name: 'hdggd',
    need: 'djhf',
    location: 'hdh'
 }]*/


 const [currentRoute,setCurrentRoute] = useState('posts');

let req, task;

let userType = localStorage.getItem('userType');

req = userType === 'victim' ? 'Post Requirement' : userType === 'volunteer' ? 'Post Availability' : 'Supply goods';

task = userType === 'victim' ? 'Requirements' : 'Tasks';

const goto = (type) => {
  let route = null;
  switch(type){
      case 1:
          break;
      case 2:

        if(userType === 'victim') {
            route = 'postRequirement';
        } else if(userType === 'volunteer') {
            route = 'posts';
        } else if(userType === 'supplier') {
            route = 'supplyGoods';
        }
          break;
      case 3:
          break;
      case 4:
          route = 'track';
          break;
      default:
          break;
  }
  setCurrentRoute(route);
}

useEffect(()=> {
  document.body.style.background = '#f3f3f3'
},[]);


    //console.log('posts ',posts);
  return (
    <div className="home">
        {redirect}
        {console.log('posts ',posts)}
        <div className='menu'>
            <div className='menu-header'>Menu</div>
            <div className='menu-button' onClick={()=>goto(1)}>My Profile</div>
            <div className='menu-button' onClick={()=>goto(2)}>{req}</div>
            <div className='menu-button' onClick={()=>goto(3)}>Mark Safe Places</div>
            <div className='menu-button' onClick={()=>goto(4)}>Track {task}</div>
        </div>
        {currentRoute === 'posts' ?
           <div className = 'post-container'>
           <div className = 'post-header'>Posts</div>
               {posts.map((item,index)=> <div className='post' key={index}>{item.name} is in need of {item.need} at {item.location}</div>)}
           </div> :
           currentRoute === 'postRequirement' ?
           <PostRequirement /> :
           userType === 'victim' ?
           <TrackList /> :
           <TrackStatus />
        }
        
        </div>
  );
}

export default Home;
