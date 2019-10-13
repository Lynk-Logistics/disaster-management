var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var exphbs = require('express-handlebars');
var expressValidator = require('express-validator');
var flash = require('connect-flash');
var session = require('express-session');
var mongo = require('mongodb');
var mongoose = require('mongoose');


mongoose.connect('mongodb://ram:madhumitha123@ds125241.mlab.com:25241/app_temp');
var db = mongoose.connection;
var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://ram:madhumitha123@ds125241.mlab.com:25241/app_temp";

var routes = require('./routes/index');
var users = require('./routes/index');

var app = express();

app.use(express.static('public'));        
app.set('view engine','ejs');
    
    
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());


app.use(session({
    secret: 'secret',
    saveUninitialized: true,
    resave: true
}));


app.use(expressValidator({
  errorFormatter: function(param, msg, value) {
      var namespace = param.split('.')
      , root    = namespace.shift()
      , formParam = root;

    while(namespace.length) {
      formParam += '[' + namespace.shift() + ']';
    }
    return {
      param : formParam,
      msg   : msg,
      value : value
    };
  }
}));



app.use(flash());

app.use(function (req, res, next) { 
  res.locals.success_msg = req.success_msg || null;
  res.locals.error_msg = req.flash('error_msg');
  res.locals.error = req.flash('error');
  res.locals.user = req.user || null;
  next();
});

app.use(function (req, res, next) {
  res.locals.req = req;
  res.locals.res = res;
  next();
});


app.use('/', routes);


app.set('port', (process.env.PORT || 3000));
app.listen(app.get('port'), function(){
	console.log('Server started on port '+app.get('port'));
});