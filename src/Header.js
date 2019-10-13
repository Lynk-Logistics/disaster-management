import React from "react";
import AppBar from "@material-ui/core/AppBar";
import { Toolbar, Typography } from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles(theme => ({
  title: {
    flexGrow: 1,
    display: 'none',
    [theme.breakpoints.up('sm')]: {
      display: 'block',
    },
  }
}));

export default function Header() {
    const classes = useStyles();
    var style = {
        backgroundColor: "red",
        width: "100%",
        height: 40
    };

    return (
        <AppBar position="static">
        <Toolbar>
            <Typography className={classes.title} variant="h6" noWrap>
                Catamaran
            </Typography>
        </Toolbar>
        </AppBar>
    );
}