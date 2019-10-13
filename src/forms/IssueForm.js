import React, { useState, useCallback } from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import RadioGroup from '@material-ui/core/RadioGroup';
import Radio from '@material-ui/core/Radio';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormLabel from '@material-ui/core/FormLabel';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import Snackbar from '@material-ui/core/Snackbar';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import { navigate } from 'hookrouter';
import Header from '../Header';

const useStyles = makeStyles(theme => ({
  '@global': {
    body: {
      backgroundColor: theme.palette.common.white,
    },
  },
  paper: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  form: {
    width: '100%',
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export default function IssueForm() {
  const classes = useStyles();
  const [issue, setIssue] = useState({issueType: "supplies", message: "", address: "", phone: ""});
  const forceUpdate = useCallback(() => setIssue({}), []);
  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState('');
  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
  };
  const handleChange = event => {
    setIssue({...issue, [event.target.name]: event.target.value });
  };

  const handleSubmit = event => {
    event.preventDefault();
    
    fetch("http://localhost:4000/issue", {
      method: "POST",
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(issue)
    })
    .then((response) => {
      setMessage('Issue created. Please check your whatsapp for more information.');
      setOpen(true);
      forceUpdate();
      setTimeout(() => navigate('/success'), 500);
    })
    .catch((error) => {
      setMessage('Error while creating Issue. Please try again in sometime.');
      setOpen(true);
    });
  }

  return (
    <div>
      <Header />
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <div className={classes.paper}>
          <Typography component="h1" variant="h5">
            Issue
          </Typography>
          <form className={classes.form} onSubmit={handleSubmit}>
            <FormLabel component="legend">Issue Type</FormLabel>
            <RadioGroup name="issueType" value={issue.issueType} onChange={handleChange}>
              <FormControlLabel value="supplies" control={<Radio />} label="Supplies" />
              <FormControlLabel value="stranded" control={<Radio />} label="Stranded" />
            </RadioGroup>
            <FormLabel component="legend">Message</FormLabel>
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              id="message"
              label="Message"
              name="message"
              value={issue.message}
              onChange={handleChange}
              autoFocus
              multiline
            />
            <FormLabel component="legend">Address</FormLabel>
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="address"
              value={issue.address}
              label="Address"
              id="address"
              onChange={handleChange}
              multiline
            />
            <FormLabel component="legend">Contact Number</FormLabel>
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="phone"
              value={issue.phone}
              label="Phone"
              id="phone"
              onChange={handleChange}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
            >
              Submit
            </Button>
          </form>
        </div>
      </Container>
      <Snackbar
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
        open={open}
        autoHideDuration={6000}
        onClose={handleClose}
        ContentProps={{
          'aria-describedby': 'message-id',
        }}
        message={<span id="message-id">{ message }</span>}
        action={[
          <IconButton
            key="close"
            aria-label="close"
            color="inherit"
            className={classes.close}
            onClick={handleClose}
          >
            <CloseIcon />
          </IconButton>,
        ]}
      />
    </div>
  );
}
