import React,{useState,useEffect} from 'react';
import './styles.scss';
import {Redirect} from 'react-router-dom';

function Main() {
    const [a,seta]=useState(null) ;
    
    useEffect(()=> {
      document.body.style.backgroundColor = '#29BBC4';
    });
    const handleClick = (type) => {
        let user = type === 1 ? 'victim' : type === 2 ? 'volunteer' : 'supplier';
        localStorage.setItem('userType',user);
        seta(<Redirect to={{
          pathname: '/login',
          state: {userType: user}
        }} />);
    }
    return (
        <div className='main'>
            {a}
           <div className = 'card'>
               <button className='button' onClick={()=>handleClick(1)}>Are you a victim who needs help?</button>
           </div>
           <div className = 'card'>
               <button className='button' onClick={()=>handleClick(2)}>Are you a volunteer willing to help?</button>
           </div>
           <div className = 'card'>
               <button className='button' onClick={()=>handleClick(3)}>Are you a supplier willing to donate?</button>
           </div>
        </div>
    );

}

export default Main;