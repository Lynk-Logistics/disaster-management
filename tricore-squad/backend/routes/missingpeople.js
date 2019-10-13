const express = require('express');
const router = express.Router();

const Missingpeople = require('./schemas/missingpeople');


router.get('/getAll', (req, res) => {
    Missingpeople.find().then(missingpeoples => {
        if (missingpeoples) {
            const response = {
                data: missingpeoples,
                message: 'missing People Details Retrieved',
                status: 1001
            }
            return res.json(response);
        } else {
            const response = {
                data: null,
                message: 'No missing People available',
                status: 1004
            }
            return res.json(response);
        }
    })
});

module.exports = router;