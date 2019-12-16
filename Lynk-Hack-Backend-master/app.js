var compression = require('compression')
    , express = require('express')
    , expressStatus = require('express-status-monitor')
    , helmet = require('helmet')
    , routers = require('./_core/routers.js');


var app = express();				// call the express framwork
app.use(expressStatus());			// status monitor
app.enable('trust proxy');			// to get the actual ip address behind aws elb proxy

//to define port from an argument
var port = 3004;
if ((process.argv.length > 2) && (process.argv[2])) {
    port = process.argv[2] ? process.argv[2] : port;
}

app.use(helmet());                  // to set the http headers
app.use(compression());             // New call to compress content
app.use(routers);                   // call the controllers

// app exit normally
process.on('exit', function () {
    console.log('app exit');
    process.exit();
});

// catch ctrl+c event and exit normally
process.on('SIGINT', function () {
    //console.log('Ctrl-C...');
    process.exit();
});

// TODO: IMPORTANT: Need to update this section
//catch uncaught exceptions, trace, then exit normally
process.on('uncaughtException', function (e) {
    console.log('Uncaught Exception...');
    console.log(e.stack);
    process.exit();
});

// start the server and listen to the port
app.listen(port, function () {
    console.log("\033c");
    console.log('                                                  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+');
    console.log('                                                  |  b i b l i o  s e r v i c e |');
    console.log('                                                  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+');
    console.log('');
    console.log('listening on port      : ' + port);
    console.log('server is ready ...');
    console.log('environment', process.env.NODE_ENV);
})
