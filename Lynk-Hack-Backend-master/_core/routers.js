var bodyParser = require('body-parser')
    , express = require('express')
    , router = express.Router()
    ;
    

// There will not be any authorisation because the request is going to be limited to Whitelisted IP

router.use(bodyParser.urlencoded({ limit: '50mb', extended: true }))		        // parse application/x-www-form-urlencoded
router.use(bodyParser.text({ limit: '50mb', type: 'application/xml' }))		        // parse application/xml
router.use(bodyParser.json({ limit: 1024 * 1024 * 20, type: 'application/json' }))  // parse application/json

//adding it to set default directory for loading interface page
router.use(express.static('public'));
router.use(bodyParser.json());
/**
 * handle all api calls, GET/POST/PUT/DELETE/...
 */
router.post('/api/*', function(req, res){
    	var pageName = req.url.toLowerCase();
    	var endPointName = pageName.replace(/^\/api\/([^\/\?]+)\/?.*$/gi, '$1');
        var reqMethod = req.method.toLowerCase();
        
    	try {
           
            var api=require('../_core/'+reqMethod+'/'+endPointName+'.js');
            api[endPointName](req,res);
            //console.log("success");
    	
    	}
    	catch(e){
    	//console.log("failed");
    		res.status(404).json({status:{code: 404, message: "You have used an incorrect request methos (GET instead of POST?) or the requested resource does not exist"}}).end();
    	}
    	
            //logRequests(req, 'success');
       
     });
    

/**
 * handle all other requests here, just say not found!!!
 */
router.all('*', function (req, res) {
    res.status(200).json({ status: { code: 404, message: "You have used an incorrect request methos (GET instead of POST?) or the requested resource does not exist" } }).end();
});

module.exports = router