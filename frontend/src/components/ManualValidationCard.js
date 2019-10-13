import React from 'react';
import {
  Card, CardImg, CardText, CardBody,
  CardTitle, CardSubtitle, Button, Progress
} from 'reactstrap';

const ManualValidationCard = (props) => {
  const getResizedImgUrl = url => {
    const splittedUrl = url.split("upload/")
    const newUrl = `${splittedUrl[0]}upload/w_400,h_400/${splittedUrl[1]}`
    return newUrl


  }
    const renderTable = (name, value) => (
            <div className="d-flex align-items-center">
                <p className="mt-3">{name}</p>
                <span className="m-3">{value}%</span>
                <div style={{minWidth:'150px'}}>
                  <Progress
                    max="100"
                    value={value}
                    barClassName={(props.cvRating && props.cvRating <= 50) ? "bg-danger": "bg-success"}
                    style={{ marginTop:'1rem',}}
                  />
                </div>
              </div>)
  return (
    <div>
      <Card>

        <a href={props.img_url} target="_blank">
            <CardImg top width="100%" src={getResizedImgUrl(props.img_url)}   rel="noopener noreferrer" />
        </a>
        <CardBody>
          <CardTitle><h2>{props.address}</h2></CardTitle>
           <CardSubtitle>{props.landmark}</CardSubtitle>
          <hr class="my-3"></hr>
          <CardText>
          <p>{props.name} / {props.mobile_no}</p>

          </CardText>

          <hr class="my-3"></hr>
          <h4>Cloud Vision Insights</h4>
          {props.cloud_vision_data.slice(0,5).map( x => renderTable(x.label, x.score))}


          <div className="d-flex align-items-center ml-5 mt-4">
                  <Button
                        color="primary"
                        onClick={props.onAcceptClick}

          >Accept</Button>
          <Button
                                onClick={props.onRejectClick}

          >Reject</Button>

          </div>

        </CardBody>
      </Card>
    </div>
  );
};

export default ManualValidationCard;