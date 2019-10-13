const express = require('express');
const router = express.Router();

const Volunteer = require('./schemas/volunteer');

// Add Volunteer
router.post('/add', (req, res) => {
    const volunteer = new Volunteer(req.body);
    volunteer.save().then((volunteer) => {
        const response = {
            status: 1001,
            message: 'Volunteer details saved Successfully',
            data: volunteer
        }
        if (volunteer) return res.status(201).json(response);
        else return res.status(404).send('Not Found');
    }, err => {
        console.log(err);
        return res.status(500).send('Error Occured');
    });
});

router.get('/check-exists', (req, res) => {
    Volunteer.findOne({mobileNo: req.query.mobileNo}).then(existVolunteer => {
        if (existVolunteer) {
            console.log(existVolunteer);
            const response = {
                status: 1003,
                message: 'User Exists',
                data: existVolunteer
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
    Volunteer.find().then(volunteer => {
        if (volunteer) {
            const response = {
                data: volunteer,
                message: 'Volunteer Details Retrieved',
                status: 1001
            }
            return res.json(response);
        } else {
            const response = {
                data: null,
                message: 'No Volunteer available',
                status: 1004
            }
            return res.json(response);
        }
    })
});

module.exports = router;