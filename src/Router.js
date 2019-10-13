import React from 'react';
import IssueForm from './forms/IssueForm';
import { DetailedView } from './DetailedView';
import RegistrationForm from './forms/Registration';
import BoardView from "./BoardView";
import SigninForm from './forms/Signin';
import Header from './Header';
import SuccessBg from './images/success.png';

const SuccessPage = () => {
	return (
		<React.Fragment>
      	<Header />
        <img src={SuccessBg} style={{marginLeft: 'auto', marginRight: 'auto', marginTop: 100, width: 300, height: 300, display: 'block', position: 'relative' }} />
			  <center><h3>Your issue has been successfully registered with us. Updates will be sent through whatsapp.</h3></center>
		</React.Fragment>
	);
};


const routes = {
    "/create": () => <IssueForm />,
    "/show/:id": ({id}) => <DetailedView id={id} />,
    "/register": () => <RegistrationForm />,
    "/signin": () => <SigninForm />,
    "/success": () => <SuccessPage />,
    "/": () => <BoardView />
  };
export default routes;