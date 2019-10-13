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
import {
  Badge,
  Card,
  CardHeader,
  CardFooter,
  DropdownMenu,
  DropdownItem,
  UncontrolledDropdown,
  DropdownToggle,
  Media,
  Pagination,
  PaginationItem,
  PaginationLink,
  Progress,
  Table,
  Container,
  Row,
  UncontrolledTooltip,
  Col,
  Image
} from 'reactstrap';
// core components
import Header from 'components/Headers/Header.jsx';
import ManualValidationCard from 'components/ManualValidationCard';

import { get, post } from 'axios';
const data = [
  {
    id: '_unique_id',
    img_url:
      'https://res.cloudinary.com/ashiknesin/image/upload/v1570902952/lynk-hacks/q1hfcmnmgsl9tnzxq8pj.png',
    address: 'Madipakkam, Velachery',
    landmark: 'Somewhere near',
    name: 'Ashik',
    mobileNo: 90698068064,
    cvInsights: [{ label: 'Rain', value: 80 }]
  }
];

class Tables extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      pendingVerifications: []
    };
  }
  componentDidMount = () => {
    this.fetchPendingVerifications();
  };

  fetchPendingVerifications = async () => {
    try {
      const { data } = await get(
        `https://nesin-heroku-flask.herokuapp.com/api/get-pending-verifications`
      );
      const pendingVerifications = data.response.map(item => {
        return {
          id: Object.keys(item)[0],
          ...Object.values(item)[0]
        };
      });
      this.setState({ pendingVerifications });

      console.log(pendingVerifications);
    } catch (e) {
      alert('Something went wrong while processing fetchPendingVerifications');
    }
  };

  onAcceptClick = async id => {
    const formData = {
      status: 'verified',
      id
    };
    console.log(`onAcceptClick ${id}`);
    try {
      const { data } = await post(
        `https://nesin-heroku-flask.herokuapp.com/api/update-pending-verifications`,
        formData
      );
      alert(data.msg);
      this.fetchPendingVerifications();
    } catch (e) {
      alert('Something went wrong while processing onAcceptClick');
    }
  };

  onRejectClick = async id => {
    const formData = {
      status: 'rejected',
      id
    };
    console.log(`onAcceptClick ${id}`);
    try {
      const { data } = await post(
        `https://nesin-heroku-flask.herokuapp.com/api/update-pending-verifications`,
        formData
      );
      alert(data.msg);
      this.fetchPendingVerifications();
    } catch (e) {
      alert('Something went wrong while processing onRejectClick');
    }
  };
  render() {
    return (
      <>
        <Header />
        {/* Page content */}
        <Container className="mt--7" fluid>
          <Row>
            {this.state.pendingVerifications.map(item => (
              <Col xs="4" className="mt-6">
                <ManualValidationCard
                  {...item}
                  onAcceptClick={() => this.onAcceptClick(item.id)}
                  onRejectClick={() => this.onRejectClick(item.id)}
                />
              </Col>
            ))}
          </Row>
        </Container>
      </>
    );
  }
}

export default Tables;
