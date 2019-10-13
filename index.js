require('module-alias/register');
const express = require('express');
const app = express();
const cors = require('cors');

const morgan = require('morgan')
require('dotenv').config()
var bodyParser = require('body-parser');

var zone = require("@routes/zone")
var victim = require("@routes/victim")
var restorepoint = require("@routes/restorepoint")
var goodsrequest = require("@routes/goodsrequest")
var offergoodsrequest = require("@routes/offergoodsrequest")
var volunteer = require("@routes/volunteer")

app.use(bodyParser.raw({ limit: "100mb" }));
app.use(bodyParser.json({ limit: '100mb' }));
app.use(bodyParser.urlencoded({ limit: '100mb', extended: true }));


app.use(function (req, res, next) {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader("Content-Type", "application/json");
  res.setHeader("Content-Type", "text/html");
  res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  res.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
  next();
});

app.use(cors());
app.use(morgan('dev'));
app.use("/zone", zone.router)
app.use("/victim", victim.router)
app.use("/restorepoint", restorepoint.router)
app.use("/goodsrequest", goodsrequest.router)
app.use("/offergoodsrequest", offergoodsrequest.router)
app.use("/volunteer", volunteer.router)


app.get('/', function (req, res) {
  res.send('Hello World!');
});

app.set('port', process.env.PORT || 3000);
app.listen(app.get('port'), () => console.log('App listening on port', app.get('port')));


module.exports = app;