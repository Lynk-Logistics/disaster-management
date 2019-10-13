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

// reactstrap components
import { Button, Container, Row, Col } from 'reactstrap';

class UserHeader extends React.Component {
  render() {
    return (
      <>
        <div
          className="header pb-8 pt-5 pt-lg-8 d-flex align-items-center"
          style={{
            minHeight: '400px'
            // backgroundImage:
            //   "url(" + require("assets/img/theme/profile-cover.jpg") + ")",
            // backgroundSize: "cover",
            // backgroundPosition: "center top"
          }}
        >
          {/* Mask */}
          <span className="mask bg-gradient-default opacity-8" />
          {/* Header container */}
          <Container className="d-flex align-items-center" fluid>
            <Row>
              <Col lg="12" md="12">
                <p className="text-white mt-0">
                  Steps for marking unsafe areas:
                </p>
                <ol style={{ color: '#fff' }}>
                  <li>Upload Affected Area Photo</li>
                  <li>Enter location & contact information</li>
                  <li>Cross check the data & submit</li>
                </ol>
              </Col>
            </Row>
          </Container>
        </div>
      </>
    );
  }
}

export default UserHeader;
