import React,{useState,useEffect} from 'react';
import './styles.scss';
import {Link} from 'react-router-dom';
import axios from 'axios';
import {Redirect} from 'react-router-dom';

function Login(props) {

  const [userDetails,setUserDetails] = useState({
      mobile: '',
      password: ''
  });
  let location = null;

  //change here
  let userType = props.location.state ? props.location.state.userType : localStorage.getItem('userType');
  localStorage.setItem('userType',userType);


  const [redirect,setRedirect] = useState(null);

    const showPosition = (position)=> {
        console.log('position',position);
                let latitude = position.coords.latitude;
                let longitude = position.coords.longitude;
                location = {
                    lat: latitude,
                    long: longitude
                }
                console.log('location',location);
                localStorage.setItem('location',location);
                if(userType === 'volunteer') {
                    axios.post('/volunteer/'+localStorage.getItem('userId'))
                    .then((response)=> {
                        console.log(response);
                    }).catch((error)=> {
                        setRedirect(<Redirect to={{
                            pathname: '/error'
                         }} />);
                    })
                }
    }
     let options = {timeout: 60000};



    const handleError = (err) => {
        console.warn('ERROR(' + err.code + '): ' + err.message);
    }

    if(navigator.geolocation) {
        console.log('yes');
        navigator.geolocation.watchPosition(showPosition,handleError,options);
    }
  let passwordField = <span>
                      <label className = 'label'>Password</label>
                      <input className = 'input' type = 'password' value = {userDetails.password} onChange={(event)=>setUserDetails({...userDetails,password: event.target.value})} />
                     </span>
  if(userType === 'victim') {
      passwordField = null;
  }

  const handleSubmit = (event) => {
      event.preventDefault();
      let obj = {};
      obj.mobile_number = userDetails.mobile;
      if( (userType === 'volunteer') || (userType === 'supplier') ) {
          obj.password = userDetails.password;
      }
      //ajax call
      let user = localStorage.getItem('userType');
      axios.post('/'+user+'/login', {...obj})
      .then((response)=>{
          let str = user + '_details';
          console.log('login-',response.str);
          localStorage.setItem('userDetails',response.str);
          setRedirect(<Redirect to={{
              pathname: '/home'
          }} />
          );
        })
      .catch((error)=> {
          setRedirect(<Redirect to={{
             pathname: '/error'
          }} />);
      });
  }

  useEffect(()=> {
     document.body.style.backgroundColor = '#29BBC4';
  },[]);
                    

  return (
    <div className="login">
        {redirect}
        <div className='card'>
        <div className = 'header'>Login</div>
       <form onSubmit = {handleSubmit}>
           <label className='label'>Mobile</label>
           <input className = 'input' type = 'text' minLength='10' maxLength='10' value={userDetails.mobile} onChange={(event)=>setUserDetails({...userDetails, mobile: event.target.value})} />
            {passwordField}
            <button className = 'button' type = 'submit' >Login</button>
       </form>
       <Link className = 'link' to='/signup'>New User? Register</Link>
       </div>
    </div>
  );
}

export default Login;
