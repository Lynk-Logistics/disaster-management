import React from "react";
import Button from "@material-ui/core/Button";
import "./App.css";
import Header from "./Header";
import { Container, Badge } from "@material-ui/core";
import PhoneIcon from "@material-ui/icons/Phone";
import RoomIcon from "@material-ui/icons/Room";
import { blockParams } from "handlebars";

function DetailedView(props) {
  var issueId = props.id;
  var issueBody = {
    status: "Dispatched",
    type: "Supplies",
    message: "Fire!! Fire!! There is a lot of things burining",
    address: "OMR",
    number: "9123455667"
  };
  return (
    <div>
      <Header></Header>
      <Container maxWidth="sm" style={{ marginTop: 40 }}>
        <Ticket id={issueId} body={issueBody} />
        {/* <ShareOnWhatsapp message="Fire!! Fire!!" /> */}
        <VolunteerCard />
      </Container>
    </div>
  );
}

function Ticket(props) {
  var styles = {
    padding: 5,
    paddingLeft: 10,
    color: "#444"
  };

  var statusColor = {
    padding: 3,
    backgroundColor: "#eee",
    float: "right"
  };

  var messageBubble = {
    display: "block",
    backgroundColor: "#f0f0ff",
    margin: 5,
    padding: 10,
    borderRadius: 5
  };

  var mapsLink =
    "https://www.google.com/maps/search/?api=1&query=" + props.body.address;
  return (
    <Badge badgeContent={props.body.issueType} color="primary">
      <div style={styles}>
        <div
          style={{
            display: "flex",
            alignItems: "center"
          }}
        >
          <PhoneIcon />
          <span>{props.body.phone}</span>
        </div>

        {/* <span style={statusColor}> {props.body.status}</span> */}
        {/* <span style={statusColor}> {props.body.type} </span> */}
        {/* //TODO: LIMIT height */}

        <div style={messageBubble}>{props.body.message}</div>

        <div
          style={{
            display: "flex",
            alignItems: "center"
          }}
        >
          <Button href={mapsLink}>
            <RoomIcon />
          </Button>
          <span> {props.body.address} </span>
          {/* //TODO: Add font awesome */}
        </div>
      </div>
    </Badge>
  );
}

function ShareOnWhatsapp(props) {
  var whatsappLink = "whatsapp://send?text=" + props.message;
  return <Button href={whatsappLink}> Share on whatsapp </Button>;
}

function VolunteerCard() {
  return (
    <div>
      <h3>Volunteer Details</h3>
      Volunteer name <br />
      82918391823
    </div>
  );
}

export { DetailedView, Ticket };
