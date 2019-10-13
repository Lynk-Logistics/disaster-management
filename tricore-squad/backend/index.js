// mongodb+srv://lynkdb:admin123@lynkdb-l7zcx.mongodb.net/test

const express = require('express');
const bp = require('body-parser');
const path = require('path');
const cors = require('cors');
const mongoose = require('mongoose');
mongoose.set('useCreateIndex', true);


// Custom modules
const victimsRoute = require('./routes/victim');
const volunteersRoute = require('./routes/volunteer');
const sponsorsRoute = require('./routes/sponsor');
const missingpeoplesRoute = require('./routes/missingpeople');
const recoveredpeoplesRoute = require('./routes/recoveredpeople');

// const volunteersRoute = require('./routes/volunteer');
// const sponsorsRoute = require('./routes/sponsor');


const app = express();



app.use(bp.urlencoded({ extended: false }));
app.use(bp.json());

var corsOptions = {
    origin: '*',
    optionsSuccessStatus: 200, // some legacy browsers (IE11, various SmartTVs) choke on 204
    exposedHeaders: ['x-auth-token']
}

app.use(cors(corsOptions));

app.use('/', express.static(path.join(__dirname, 'dist')));


app.use('/api/victim', victimsRoute);
app.use('/api/volunteer', volunteersRoute);
app.use('/api/sponsor', sponsorsRoute);
app.use('/api/missingpeople', missingpeoplesRoute);
app.use('/api/recoveredpeople', recoveredpeoplesRoute);


app.get('/*', (req, res) => {
    res.sendFile(path.join(__dirname + '/dist/index.html'));
});


const port = process.env.PORT || 3000;
mongoose.connect('mongodb+srv://lynkdb:admin123@lynkdb-l7zcx.mongodb.net/tricoresquad', { useNewUrlParser: true, useUnifiedTopology: true }).then(() => {
    app.listen(port, () => {
        console.log(`Server started and running!`)
    });
}).catch(err => console.log(err));