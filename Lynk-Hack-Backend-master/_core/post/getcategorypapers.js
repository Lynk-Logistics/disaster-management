var path = require('path');
var exec = require('child_process').exec;
const fs = require('fs');
var connection = require(path.join(__dirname, '..', 'post', 'JS', 'connection.js'));
module.exports = {
    getcategorypapers: function (req, res, wfXML) {
       
        fs.writeFile("/Users/apple/quotesandfacts/_core/post/biblio.txt", req.body.text, function(err) {
            if(err) {
                return console.log(err);
            }
            execute("anystyle -f json parse /Users/apple/quotesandfacts/_core/post/biblio.txt", function(name){
                res.status(200).json(name).end();
            });
        }); 

    }
}
function execute(command, callback){
    exec(command, function(error, stdout, stderr){ callback(stdout); });
};