const express = require('express');
const app = express();
var cors = require('cors');
const url_redirect = require('url');

var User = require('../models/user');
var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://ram:madhumitha123@ds333238.mlab.com:33238/lyfline";

/*mongo shell
mongo ds333238.mlab.com:33238/lyfline -u ram -p madhumitha123

db.doctors.insert({name:"Mukesh",lat:"13.049055",long:"80.267574",adress:"18/13, Azad Nagar, Royapettah, Chennai, Tamil Nadu 600014",phone:"9025725112",password:"123",pincode:"600014",status:"0"})

*/

/*
- login get-post
- register get-post
- home
- ...
- logout
*/
app.use(cors());

app.get('/checkdoc',(req,res) =>{
    var uname = req.query.name;
    var location = req.query.location;
    var docspl = req.query.docspl;
    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("lyfline");  
        dbo.collection("users").update({name:uname},{$set:{status:1}}), function(err, res) {
            if (err) throw err;          
        }
        dbo.collection("doctors").update({name:"Mukesh"},{$set:{status:1}}), function(err, res) {
            if (err) throw err;          
        }
        
    });
    res.send(uname);
});

app.get('/approvalstatus',(req,res) =>{
    var status = req.query.status;    
    var uname = req.query.name;
    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("lyfline");  
        dbo.collection("users").update({name:uname},{$set:{status:0}}), function(err, res) {
            if (err) throw err;          
        }        
    });
    res.send(uname);
});

app.post('/userdetails',(req,res) =>{
    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("lyfline");                 
        dbo.collection("users").find({status:1}).toArray(function(err, result) {
            if (err) throw err;
            res.render('userdetails',{
                amount:result
            });            
        });
    });
});

app.post('/docdetails',(req,res) =>{
    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("lyfline");                 
        dbo.collection("doctors").find({status:1}).toArray(function(err, result) {
            if (err) throw err;
            res.render('docdetails',{
                amount:result
            });            
        });
    });
});

app.post('/allzero',(req,res) =>{
    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("lyfline");            
        dbo.collection("users").update({status:1},{$set:{status:0}}), function(err, res) {
            if (err) throw err;          
        }                  
        dbo.collection("doctors").update({name:"Mukesh"},{$set:{status:0}}), function(err, res) {
            if (err) throw err;          
        }      
    });
    res.send('done');
});


app.get('/', (req,res) =>{   
    var passedVariable = req.query.valid;
        req.sucess_msg = passedVariable;
    if(passedVariable){
        res.render('login',{
            title:"login",
            query:passedVariable
        });
    }
    else{
        req.sucess_msg = passedVariable;
        res.render('login',{
            title:"login",
            query:"nil"
        });
    }   
});

 
app.post('/',(req, res) =>{
    var username = req.body.username;
	var password = req.body.password;
    
    User.findOne({username: username, password: password}, function(err, user) {
      if(err) return next(err);
      if(!user) {
          res.render('login',{
            title:"login",
            query:"error"
        });
      }
        else{
                req.session.user = user;
            res.send("hello");
//                res.redirect(url_redirect.format({
//                   pathname:"/daily-usage",
//                   query: {
//                      title:"daily-usage"
//                    }
//                 }));
            }

      
   });
    
});



app.get('/register',(req,res) =>{
    res.render('register');
});


app.post('/register',(req,res,next) =>{
   var username = req.body.username;
	var email = req.body.email;
	var phone = req.body.phone;
	var password = req.body.password;
	var password2 = req.body.c_password;
	req.checkBody('c_password', 'Passwords do not match').equals(req.body.password);
    
    
    var errors = req.validationErrors();
    
    if(errors)
    {        
        res.render('register', { value : errors});
    }
    else
    {       
        var newUser = new User({
		phone: phone,
		email: email,
		username: username,
		password: password
		});
        
        User.create(newUser, function(err, newUser) {
        if(err) return next(err);
            
        });
        
        var string = "You are registered and can now login";
        res.redirect('/?query=' + string);
    }
    
});


app.get('/logout', function (req, res) {
  req.session.destroy();
  var string = "you have sucessfully logged out";
  res.redirect('/?valid=' + string);
});

module.exports = app;