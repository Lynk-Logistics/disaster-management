import React from 'react';
import './App.scss';
//import ShowMap from './Components/ShowMap/ShowMap';
import ErrorPage from './Components/ErrorPage/ErrorPage';
import Home from './Components/Home/Home';
import SignUp from './Components/SignUp/SignUp';
import Login from './Components/Login/Login';
import Main from './Components/Main/Main';
import {BrowserRouter,Route,Redirect,Switch} from 'react-router-dom';

function App() {

  /*let some = {
      status: 4,
      need: 'goods',
      sub_need: 'clothes',
      victim_details: {
        name: 'abc',
        mobile: '367636763673',
        location: 'dhhhd'
      },
      supplier_details: {
        name: 'sabc',
        mobile: '367636763673',
        location: 'dhhhd'
      },
      volunteer_details: {
        name: 'vabc',
        mobile: '367636763673',
        location: 'dhhhd'
      },

      <BrowserRouter>
          <Redirect to={{
            pathname: '/login',
            state: {userType: 'volunteer'}
          }} />
          <Switch>
           <Route path='/home' exact component = {Home} />
           <Route path='/signup' exact component = {SignUp} />
           <Route path='/login' exact component = {Login} />
          </Switch>
       </BrowserRouter>
  }*/
  return (
    <div className="App">
       <BrowserRouter>
          <Redirect to={{
            pathname: '/main'
          }} />
          <Switch>
           <Route path='/home' exact component = {Home} />
           <Route path='/signup' exact component = {SignUp} />
           <Route path='/login' exact component = {Login} />
           <Route path='/main' exact component={Main} />
           <Route path='/error' exact component={ErrorPage} />
          </Switch>
       </BrowserRouter>
    </div>
  );
}

export default App;
