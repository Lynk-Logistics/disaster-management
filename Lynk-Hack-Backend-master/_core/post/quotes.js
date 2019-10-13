var path = require('path');
var connection = require(path.join(__dirname, '..', 'post', 'JS', 'connection.js'));
module.exports = {
    quotes: function (req, res, wfXML) {
     connection.quotes(req.body).then(function(data){
        if(req.client && req.client._peername)
        {
            if(!req.client._peername.address)
            {req.client._peername["address"]="";}
            if(!req.client._peername.port)
            {req.client._peername["port"]="";}
            if(!req.client._peername.family)
            {req.client._peername["family"]="";}
        req.body["ipaddress"]=req.client._peername.address+":"+req.client._peername.port+" "+req.client._peername.family;
     }
         if(data.status==200)
        {
            res.status(200).json(data.data).end();
        }else
        {
            res.status(400).json({"message":"something went wrong."}).end();
        }
        return;
     });
    }
}