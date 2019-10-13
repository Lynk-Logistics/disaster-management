import React from "react";
import "./App.css";
import {useRoutes, usePath} from 'hookrouter';
import Routes from './Router';
import Header from './Header';
import PageNotFound from './images/404.png';
import Container from "@material-ui/core/Container";

const SmartNotFound = () => {
	return (
		<React.Fragment>
      	<Header />
		  <Container maxWidth="lg">
		  	<center><h2>404 - PageNotFound</h2></center>
		  	<img src={PageNotFound} style={{marginLeft: 'auto', marginRight: 'auto', marginTop: 'auto', width: 300, height: 300, display: 'block', position: 'relative' }} />
			<center><h1>Oops! We could not reach the rescue team.</h1></center>
		  </Container>
		</React.Fragment>
	);
};

function App() {
  const routeResult = useRoutes(Routes)
  return routeResult || <SmartNotFound />;
}

export default App;
