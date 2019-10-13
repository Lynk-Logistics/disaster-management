const express = require('express');
const router = express.Router();

const RecoveredPeople = require('./schemas/recoveredpeople');


router.get('/getAll', (req, res) => {
    RecoveredPeople.find().then(recoveredpeoples => {
        if (recoveredpeoples) {
            const response = {
                data: recoveredpeoples,
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