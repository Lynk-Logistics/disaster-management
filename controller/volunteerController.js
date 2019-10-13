var mongoose = require("mongoose");
var volunteerModel = require("@schema/volunteer").model
var controller = {}
var assert = require('assert')

controller.createNewVolunteer = async (req, res) => {
    try {
        assert(req.body.mobile || req.body.coordinates || req.body.deviceToken, "Bad Request")
        var params = {
            name: req.body.name,
            location: {
                type: "Point",
                coordinates: req.body.coordinates
            },
            mobile: req.body.mobile,
            deviceToken: req.body.deviceToken
        }

        var newvolunteer = await volunteerModel.create(params)
        return res.status(200).json({ error: null, data: newvolunteer })

    } catch (error) {
        console.log(error);
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.json({ "error": error });
    }
}

controller.updateCurrentLocation = async (req, res) => {
    try {
        assert(req.body.coordinates, "Coordinates required")
        var updateDoc = await volunteerModel.updateOne({ phoneNumber: req.body.phoneNumber }, {
            $set: {
                "location.coordinate": req.body.coordinates
            }
        })
        return res.status(200).json({ error: null, data: updateDoc })
    } catch (error) {
        console.log(error);
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.json({ "error": error });
    }
}

var getVolunteersNearMe = (coordinates, radius) => {
    return volunteerModel.aggregate([{
        $geoNear: {
            near: { type: "Point", coordinates: coordinates },
            distanceField: "distance",
            maxDistance: radius
        }
    }])
}

controller.getVolunteersNearMe = getVolunteersNearMe
controller.getVolunteersNearMeRest = async (req, res) => {
    try {
        assert(req.body.coordinates, "Coordinates not found");
        var nearbyvolunteers = await volunteerModel.aggregate([{
            $geoNear: {
                near: { type: "Point", coordinates: req.body.coordinates },
                distanceField: "distance",
                maxDistance: req.body.radius ? req.body.radius : 1000
            }
        }
            , {
            $match: {
                active: true
            }
        }
        ])
        return res.status(200).json({ error: null, data: nearbyvolunteers })
    } catch (error) {
        console.log(error);
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.send({ "error": error });
    }
}


controller.get


module.exports = controller