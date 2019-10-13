import React, { Component } from 'react';
import { Route, BrowserRouter as Router, Switch } from 'react-router-dom';
import Home from './home';
import Feed from './feed'

export default class Routes extends Component {
	render() {
		return (
			<Router>
				<Switch>
					<Route exact path='/zone' component={Home} />		
                    <Route exact path="/" component = {Feed} />			
				</Switch>
			</Router>
		);
	}
}
