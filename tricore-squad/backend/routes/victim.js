const express = require('express');
const router = express.Router();

const Victim = require('./schemas/victim');

// Add Victim
router.post('/add', (req, res) => {
    const victim = new Victim(req.body);
    victim.save().then((savedVictim) => {
        const response = {
            status: 1001,
            message: 'Victim details saved Successfully',
            data: savedVictim
        }
        if (savedVictim) return res.status(201).json(response);
        else return res.status(404).send('Not Found');
    }, err => {
        console.log(err);
        return res.status(500).send('Error Occured');
    });
});

router.get('/check-exists', (req, res) => {
    Victim.findOne({mobileNo: req.query.mobileNo}).then(existVictim => {
        if (existVictim) {
            console.log(existVictim);
            const response = {
                status: 1003,
                message: 'User Exists',
                data: existVictim
            }
            return res.status(200).json(response);
        } else {
            console.log('here1');
            const response = {
                status: 1004,
                message: 'User doesn\'t exists',
                data: null
            }
            return res.status(200).json(response);
        }
    }, err => {
        console.log(err);
        return res.status(500).send('Error Occured');
    });
});

router.get('/getAll', (req, res) => {
    Victim.find().then(victims => {
        if (victims) {
            const response = {
                data: victims,
                message: 'Victims Details Retrieved',
                status: 1001
            }
            return res.json(response);
        } else {
            const response = {
                data: null,
                message: 'No victims available',
                status: 1004
            }
            return res.json(response);
        }
    })
});

module.exports = router;