const express = require('express');
const router = express.Router();

const Sponsor = require('./schemas/sponsor');

// Add Sponsor
router.post('/add', (req, res) => {
    const sponsor = new Sponsor({
        name: req.body.name,
        mobileNo: req.body.mobileNo,
        location: req.body.location
    });
    sponsor.save().then((savedSponsor) => {
        const response = {
            status: 1001,
            message: 'Sponsor details saved Successfully',
            data: savedSponsor
        }
        if (savedSponsor) return res.status(201).json(response);
        else return res.status(404).send('Not Found');
    }, err => {
        console.log(err);
        return res.status(500).send('Error Occured');
    });
});

router.get('/check-exists', (req, res) => {
    Sponsor.findOne({mobileNo: req.query.mobileNo}).then(existSponsor => {
        if (existSponsor) {
            console.log(existSponsor);
            const response = {
                status: 1003,
                message: 'User Exists',
                data: existSponsor
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
    Sponsor.find().then(sponsors => {
        if (sponsors) {
            const response = {
                data: sponsors,
                message: 'Sponsors Details Retrieved',
                status: 1001
            }
            return res.json(response);
        } else {
            const response = {
                data: null,
                message: 'No sponsors available',
                status: 1004
            }
            return res.json(response);
        }
    })
});

module.exports = router;