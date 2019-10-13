var path = require('path');
var connection = require(path.join(__dirname, '..', 'post', 'JS', 'connection.js'));
module.exports = {
    getvolunteerdetails: function (req, res, wfXML) {
      connection.getvolunteerdetails(req.body).then(function(data){
         if(data.status==200)
        {
            res.status(200).json(data).end();
        }else
        {
            let msg="";
            if(data.error)
            {
                msg=data.error;
            }

            res.status(400).json({"message":msg}).end();
        }
        return;
     });
    }
}