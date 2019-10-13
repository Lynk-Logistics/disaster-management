import React,{useState,useEffect} from 'react';
import './styles.scss';
import axios from 'axios';
import {Redirect} from 'react-router-dom';

function SignUp() {

  const [userDetails,setUserDetails] = useState({
      name: '',
      mobile: '',
      password: ''
  });

  const [redirect,setRedirect] = useState(null);
  //change here
  let userType = 'volunteer';
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
      obj.name = userDetails.name;
      obj.mobile_number = userDetails.mobile;
      if( (userType === 'volunteer') || (userType === 'supplier') ) {
          obj.password = userDetails.password;
      }
      //ajax request
      let user = localStorage.getItem('userType');
      axios.post('/'+user+'/signup', {...obj})
      .then((response)=>{
          console.log(response);
          let str = user + '_id';
          localStorage.setItem('userId',response.str);
          setRedirect(
              <Redirect to={{
                  pathname: '/home'
              }} />
          )
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
    <div className="sign-up">
        {redirect}
       <div className='card'>
       <div className='header'>Sign Up</div>
       <form onSubmit = {handleSubmit}>
           <label className='label'>Name</label>
           <input className = 'input' type = 'text' value={userDetails.name} onChange={(event)=>setUserDetails({...userDetails, name: event.target.value})} />
           <label className='label'>Mobile</label>
           <input className = 'input' type = 'text' minLength='10' maxLength='10' value={userDetails.mobile} onChange={(event)=>setUserDetails({...userDetails, mobile: event.target.value})} />
            {passwordField}
            <button className = 'button' type = 'submit' >sign in</button>
       </form>
       </div>
    </div>
  );
}

export default SignUp;
