const express = require('express');
const router = express.Router();

const commonInfo = require('./schemas/commoninfo');


router.get('/getAll', (req, res) => {
    commonInfo.find().then(commoninfo => {
        if (commoninfo) {
            const response = {
                data: commoninfo,
                message: 'commonInfo Details Retrieved',
                status: 1001
            }
            return res.json(response);
        } else {
            const response = {
                data: null,
                message: 'No commonInfo available',
                status: 1004
            }
            return res.json(response);
        }
    })
});

module.exports = router;