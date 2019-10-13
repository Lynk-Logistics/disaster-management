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
import React from "react";

// reactstrap components
import {
  Button,
  Card,
  CardHeader,
  CardBody,
  FormGroup,
  Form,
  Input,
  Container,
  Row,
  Col
} from "reactstrap";
import axios from 'axios';
// core components
import UserHeader from "components/Headers/UserHeader.jsx";
import Dropzone from 'react-dropzone';
import request from 'superagent';
import Map from 'components/Map';
import LocationSearchInput from 'components/LocationSearchInput';
import {
  Marker
} from 'google-maps-react';
const CLOUDINARY_UPLOAD_PRESET = 'lynk-hacks';
const CLOUDINARY_UPLOAD_URL = 'https://api.cloudinary.com/v1_1/ashiknesin/upload';
class Profile extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      uploadedFileCloudinaryUrl: '',
      formData:{},
      locationCenter:null
    };
  }
  onImageDrop(files) {
    this.setState({
      uploadedFile: files[0]
    });

    this.handleImageUpload(files[0]);
  }

  handleImageUpload(file) {
    let upload = request.post(CLOUDINARY_UPLOAD_URL)
                        .field('upload_preset', CLOUDINARY_UPLOAD_PRESET)
                        .field('file', file);

    upload.end((err, response) => {
      if (err) {
        console.error(err);
      }

      if (response.body.secure_url !== '') {
        this.setState({
          uploadedFileCloudinaryUrl: response.body.secure_url
        });
      }
    });
  }
  handleChange = (event) => {
    const formData = {
      ...this.state.formData,
      [event.target.name]:event.target.value
    }
    this.setState({formData})

  }

  handleFormSubmit = async e => {
    e.preventDefault();
    const payload = {
      ...this.state.formData,
      img_url:this.state.uploadedFileCloudinaryUrl,
      latLng:this.state.locationCenter,
      address:this.state.address,
      date:new Date()
    }
    try {
      const resp = await axios.post(`https://nesin-heroku-flask.herokuapp.com/api/mark-unsafe-areas`,payload)
      alert(resp.data.msg)
    } catch (e) {

      alert(`Something went wrong. Please try again after sometime`)

    }
  }
  handleOnLocationSelect = async ({address,resultsPromise}) => {
    const center = await resultsPromise
    this.setState({locationCenter:center, address},()=>{
      console.log(this.state)
    })
  }
  // TODO:
  // 1. Lat, Lng

  render() {
    return (
      <>
        <UserHeader />
        {/* Page content */}
        <Container className="mt--7" fluid>
          <Row>
            <Col className="order-xl-2 mb-5 mb-xl-0" xl="5">
              <Card className="card-profile shadow">

                <CardBody className="pt-0 pt-md-4">
                  <Map
                    minHeight={"400px"}
                    center={this.state.locationCenter}

                  >
                    {this.state.locationCenter && (
                      <Marker
                        position={this.state.locationCenter}
                      />
                    )}
                  </Map>
                </CardBody>
              </Card>
            </Col>
            <Col className="order-xl-1" xl="7">
              <Card className="bg-secondary shadow">
                <CardHeader className="bg-white border-0">
                  <Row className="align-items-center">
                    <Col xs="8">
                      <h3 className="mb-0">Unsafe Area Details</h3>
                    </Col>

                  </Row>
                </CardHeader>
                <CardBody>
                  <Form>
                    <h6 className="heading-small text-muted mb-4">
                      Photo
                    </h6>
                    <div className="pl-lg-4">
                      <Row>
                      <Dropzone
  onDrop={this.onImageDrop.bind(this)}
  accept="image/*"
  multiple={false}>
    {({getRootProps, getInputProps}) => {
      return (
        <div
          {...getRootProps()}
        >
          <input {...getInputProps()} />
          {
          <p>Try dropping some files here, or click to select files to upload.</p>
          }
        </div>
      )
  }}
</Dropzone>

<div>
        {this.state.uploadedFileCloudinaryUrl === '' ? null :
        <div>
          <p>{this.state.uploadedFile.name}</p>
          <img src={this.state.uploadedFileCloudinaryUrl} className="img-fluid" />
        </div>}
      </div>

                      </Row>
                    </div>
                    <hr className="my-4" />
                    {/* Address */}
                    <h6 className="heading-small text-muted mb-4">
                      Location information
                    </h6>
                    <div className="pl-lg-4">
                      <Row>
                        <Col md="12">
                          <FormGroup>
                            <LocationSearchInput onSelect={this.handleOnLocationSelect}/>
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row>
                      <Col lg="12">
                          <FormGroup>

                            <Input
                              className="form-control-alternative"
                              id="input-landmark"
                              placeholder="Landmark"
                              type="text"
                              name="landmark"
                              onChange={this.handleChange}

                            />
                          </FormGroup>
                        </Col>

                      </Row>
                    </div>
                    <hr className="my-4" />
                    {/* Description */}
                    <h6 className="heading-small text-muted mb-4">Personal Infomartion
                    </h6>
                    <Row className="pl-lg-4">
                    <Col lg="12">
                          <FormGroup>

                            <Input
                              className="form-control-alternative"
                              id="input-name"
                              placeholder="Name"
                              type="text"
                              name="name"
                              onChange={this.handleChange}

                            />
                          </FormGroup>
                        </Col>
                        <Col lg="12">
                          <FormGroup>

                            <Input
                              className="form-control-alternative"
                              id="input-landmark"
                              placeholder="Mobile No"
                              type="text"
                              name="mobile_no"
                              onChange={this.handleChange}

                            />
                          </FormGroup>
                        </Col>
                        </Row>

                    <Col className="text-right" xs="12">
                      <Button
                        color="primary"
                        onClick={this.handleFormSubmit}
                        size="sm"
                      >
                        Submit
                      </Button>
                    </Col>
                  </Form>
                </CardBody>
              </Card>
            </Col>
          </Row>
        </Container>
      </>
    );
  }
}

export default Profile;
