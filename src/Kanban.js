import React from "react";
import { Ticket } from "./DetailedView";

class Kanban extends React.Component {
  render() {
    const style = {
      padding: "30px",
      paddingTop: "5px"
    };

    return (
      <div style={style}>
        <KanbanBoard data={this.props.data} />
      </div>
    );
  }
}

/*
 * The Kanban Board React component
 */
class KanbanBoard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoading: true,
      projects: [],
      draggedOverCol: "",
      data: props.data
    };
    this.handleOnDragEnter = this.handleOnDragEnter.bind(this);
    this.handleOnDragEnd = this.handleOnDragEnd.bind(this);
    this.getData = this.getData.bind(this);
    this.columns = [
      { name: "Open", stage: 1 },
      { name: "Dispatched", stage: 2 },
      { name: "Completed", stage: 3 }
    ];
    this.columnMapping = {
      1: "Open",
      2: "Dispatched",
      3: "Completed"
    };
  }

  getData() {
    fetch("http://localhost:4000/list/issues")
      .then(response => response.json())
      .then(t => this.setState({ projects: t, isLoading: false }));
  }
  componentDidMount() {
    this.getData();
    setInterval(this.getData, 5000);
  }

  //this is called when a Kanban card is dragged over a column (called by column)
  handleOnDragEnter(e, stageValue) {
    this.setState({ draggedOverCol: this.columnMapping[stageValue] });
    console.log(this.state.draggedOverCol);
  }

  //this is called when a Kanban card dropped over a column (called by card)
  handleOnDragEnd(e, project) {
    console.log(project);
    const updatedProjects = this.state.projects.slice(0);
    const modifiedProject = updatedProjects.find(projectObject => {
      return projectObject.id === project.id;
    });
    modifiedProject.status = this.state.draggedOverCol;

    fetch("http://localhost:4000/issue", {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        id: modifiedProject.id,
        status: this.state.draggedOverCol
      })
    }).catch(error => console.log(error));

    this.setState({ projects: updatedProjects });
  }

  render() {
    if (this.state.isLoading) {
      return <h3>Loading...</h3>;
    }

    return (
      <div>
        {this.columns.map(column => {
          return (
            <KanbanColumn
              name={column.name}
              stage={column.stage}
              projects={this.state.projects.filter(project => {
                if (
                  this.props.data.length > 3 &&
                  this.props.data !== "Search"
                ) {
                  return (
                    project.status === column.name &&
                    project.message
                      .toLowerCase()
                      .includes(this.props.data.toLowerCase())
                  );
                }
                return project.status === column.name;
              })}
              onDragEnter={this.handleOnDragEnter}
              onDragEnd={this.handleOnDragEnd}
              key={column.stage}
            />
          );
        })}
      </div>
    );
  }
}

/*
 * The Kanban Board Column React component
 */
class KanbanColumn extends React.Component {
  constructor(props) {
    super(props);
    this.state = { mouseIsHovering: false };
  }

  componentWillReceiveProps(nextProps) {
    this.state = { mouseIsHovering: false };
  }

  generateKanbanCards() {
    return this.props.projects.slice(0).map(project => {
      return (
        <KanbanCard
          project={project}
          key={project.phone}
          onDragEnd={this.props.onDragEnd}
        />
      );
    });
  }

  render() {
    const columnStyle = {
      display: "inline-block",
      verticalAlign: "top",
      marginRight: "5px",
      marginBottom: "5px",
      paddingLeft: "10px",
      paddingRight: "10px",
      paddingTop: "0px",
      width: "350px",
      borderRadius: 5,
      backgroundColor: this.state.mouseIsHovering ? "#ccc" : "#eee"
    };
    return (
      <div
        style={columnStyle}
        onDragEnter={e => {
          this.setState({ mouseIsHovering: true });
          this.props.onDragEnter(e, this.props.stage);
        }}
        onDragExit={e => {
          this.setState({ mouseIsHovering: false });
        }}
      >
        <h4>
          {this.props.name} ({this.props.projects.length})
        </h4>
        {this.generateKanbanCards()}
        <br />
      </div>
    );
  }
}

/*
 * The Kanban Board Card component
 */
class KanbanCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      collapsed: true,
      tickets: tickets
    };
  }

  render() {
    const cardStyle = {
      paddingLeft: "0px",
      paddingTop: "5px",
      paddingBottom: "5px",
      marginLeft: "0px",
      marginRight: "5px",
      marginBottom: "5px",
      borderRadius: 5,
      background: "#fff",
      boxShadow:
        "0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23)"
    };

    return (
      <div
        style={cardStyle}
        draggable={true}
        onDragEnd={e => {
          this.props.onDragEnd(e, this.props.project);
        }}
      >
        <Ticket body={this.props.project} />
      </div>
    );
  }
}

/*
 * Projects to be displayed on Kanban Board
 */
let tickets = [
  {
    status: "Dispatched",
    type: "Supplies",
    message:
      "Help! Need supplies at lorem impsum nagar. We've been starving for 48 hours now and nobody has reached us.",
    address: "3, madambakkam main road, Chennai",
    phoneNo: "9008080882",
    project_stage: 1
  },
  {
    status: "Open",
    type: "Stranded",
    message:
      "Stranded inside Ceebruhs Boulevard. Water for 20 feet. First floor is compeltely underwater. We're in the fourth floor with no way to get out ",
    address:
      "OMR Service Rd, PTK nagar, Thiruvanmiyur, Chennai, Tamil Nadu 600096",
    phoneNo: "9008444444",
    project_stage: 2
  },
  {
    status: "Completed",
    type: "Stranded",
    message:
      "Need help checking on my family. My family (Amit Kumar Doe and Ananta Doo) in Perungudi has not been reachable for the past 20 hours. Please check on them",
    address:
      "26, Old Mahabalipuram Rd, Srinivasa Nagar, Kandancavadi, Perungudi, Chennai, Tamil Nadu 600096",
    phoneNo: "8479814445",
    project_stage: 3
  }
];

export default Kanban;
