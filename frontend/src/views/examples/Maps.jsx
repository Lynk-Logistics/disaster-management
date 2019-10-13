/*!

=========================================================
* Argon Dashboard React - v1.0.0
=========================================================

* Product Page: https://www.creative-tim.com/product/argon-dashboard-react
* Copyright 2019 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/argon-dashboard-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import React from 'react';
import { googleMapsApi } from './../../config';

import Map from 'components/Map';

// reactstrap components
import { Card, Container, Row } from 'reactstrap';

// core components
import Header from 'components/Headers/Header.jsx';
// reactstrap components
import {
  DropdownMenu,
  DropdownItem,
  UncontrolledDropdown,
  DropdownToggle,
  Form,
  FormGroup,
  InputGroupAddon,
  InputGroupText,
  Input,
  InputGroup,
  Navbar,
  Nav,
  Media,
  Button
} from 'reactstrap';

import LocationSearchInput from 'components/LocationSearchInput';
import Iframe from 'react-iframe';

class Maps extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      iframeUrl: `https://nesin-heroku-flask.herokuapp.com/api/directions?from_lat=13.0521019&from_lng=80.22552859999996&to_lat=12.9800691&to_lng=80.21842179999999`,
      locationFromCenter: {
        lat: null,
        lng: null
      },
      locationToCenter: {
        lat: null,
        lng: null
      }
    };
  }
  updateIframeUrl = () => {
    const { locationFromCenter, locationToCenter } = this.state;
    this.setState({
      iframeUrl: `https://nesin-heroku-flask.herokuapp.com/api/directions?from_lat=${locationFromCenter.lat}&from_lng=${locationFromCenter.lng}&to_lat=${locationToCenter.lat}&to_lng=${locationToCenter.lng}`
    });
  };
  handleOnLocationFromSelect = async ({ address, resultsPromise }) => {
    const center = await resultsPromise;
    this.setState({ locationFromCenter: center, address }, () => {
      console.log(this.state);
    });
  };
  handleOnLocationToSelect = async ({ address, resultsPromise }) => {
    const center = await resultsPromise;
    this.setState({ locationToCenter: center, address }, () => {
      console.log(this.state);
    });
    this.updateIframeUrl();
  };

  render() {
    const polyline =
      'uhcnAkaxhNZsBLqAwAg@_@KeEmAaBk@_Bi@sBo@uGoBwC}@s@QBMcAU{IaBuGiAkDs@_GeAuAUqA]_A_@m@]cBaBm@]o@Ys@OsAQmCGmJHkFH}@DeIrAaG`AgANgCl@}ATk@HuCb@iCb@uC`@aV|DyAVEWKq@a@aD}@oFU_Ag@sB]g@}BqBsBgC_@c@IUSqAEMMeCyCGoCWcE[y@EyAQcCUkAMFSJ@';

    return (
      <>
        <Header />

        {/* Page content */}
        <Container className="mt--9" fluid>
          <Form className="navbar-search navbar-search-dark form-inline mr-3 d-none d-md-flex ml-lg-auto">
            <FormGroup className="mb-0  ml-8">
              <InputGroup
                className="input-group-alternative"
                style={{ maxWidth: '300px,' }}
              >
                <LocationSearchInput
                  onSelect={this.handleOnLocationFromSelect}
                  placeholder="From"
                />
              </InputGroup>
              <InputGroup
                className="input-group-alternative ml-4"
                style={{ maxWidth: '300px' }}
              >
                <LocationSearchInput
                  onSelect={this.handleOnLocationToSelect}
                  placeholder="To"
                />
              </InputGroup>
              <Button
                color="info"
                href="#pablo"
                onClick={e => e.preventDefault()}
                rounded
                className={'ml-4'}
              >
                Find Safe Route
              </Button>
            </FormGroup>
          </Form>
          <Row className="mt-5">
            <div className="col">
              <Card className="shadow border-0">
                {/* <Iframe
                  url={this.state.iframeUrl}
                  // width="450px"
                  height="450px"
                  id="myId"
                  className="myClassname"
                  display="initial"
                  position="relative"
                ></Iframe> */}
              </Card>
            </div>
          </Row>
        </Container>
      </>
    );
  }
}

export default Maps;
