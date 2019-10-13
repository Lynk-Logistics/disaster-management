var mongoose = require("mongoose");
var victimModel = require("@schema/victim").model
var controller = {}
var assert = require('assert')
var firebase = require("@common/firebase")

controller.getRescueRequestWithinZone = (zone) => {
    return victimModel.find({

        "activeRequest.present": true


    }).where('location').within().geometry(zone.location)
}

var getRescueRequestCountWithinZone = (zone) => {
    return victimModel.find({

        "activeRequest.present": true


    }).where('location').within().geometry(zone.location).count()

}

controller.upsertRescueRequest = async (req, res) => {
    try {

        var rescueRequest = await victimModel.findOne({ deviceToken: req.body.deviceToken }).exec()
        var params = {}
        if (rescueRequest) {
            if (req.body.deviceToken) params.deviceToken = req.body.deviceToken
            if (req.body.mobile) params.mobile = req.body.mobile
            params.activeRequest = rescueRequest.activeRequest
            if (req.body.status) params.activeRequest.status = req.body.status;
            if (req.body.count) params.activeRequest.count = req.body.count;
            if (req.body.name) params.name = req.body.name;
            if (req.body.medicalSupport) params.medicalSupport = true

            var rescueRequestUpdate = await victimModel.findByIdAndUpdate(rescueRequest._id, {
                $set: params
            }).lean()
                .exec()
            return res.status(200).json({ error: null, data: rescueRequestUpdate })
        } else {
            assert(req.body.coordinates, "Location has to be enabled")
            var newVictim = await victimModel.create({
                name: req.body.name,
                location: {
                    type: "Point",
                    coordinates: req.body.coordinates,
                },
                activeRequest: {
                    present: true,
                    createdAt: new Date(),
                    status: "active",
                    count: req.body.count,
                    medicalSupport: req.body.medicalSupport
                },
                mobile: req.body.mobile,
                deviceToken: req.body.deviceToken
            })
            return res.status(200).json({ error: null, data: newVictim })
        }

    } catch (error) {
        console.log(error);
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.send({ "error": error });
    }
}

var getRescueRequestsNearMe = (coordinates, radius) => {
    return victimModel.aggregate([{
        $geoNear: {
            near: { type: "Point", coordinates: coordinates },
            distanceField: "distance",
            maxDistance: radius
        }
    }])
}

controller.getRequestsStatus = async (req, res) => {
    try {
        assert(req.body.mobile || req.body.requestId || req.body.deviceToken, "Bad request")

        var requestDetails = await victimModel.findOne(req.body).lean().exec()
        return res.status(200).json({ error: null, data: requestDetails })

    } catch (error) {
        console.log(error);
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.send({ "error": error });
    }

}

controller.getRescueRequestsNearMeRest = async (req, res) => {
    try {
        assert(req.body.coordinates, "Coordinates not found");
        var nearbyRescueRequest = await getRescueRequestsNearMe(req.body.coordinates, req.body.radius ? req.body.radius : 1000)
        return res.status(200).json({ error: null, data: nearbyRescueRequest })
    } catch (error) {
        console.log(error);
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.send({ "error": error });
    }
}

controller.getRescueRequestsNearMe = getRescueRequestsNearMe;
controller.getRescueRequestCountWithinZone = getRescueRequestCountWithinZone


victimModel.watch({ fullDocument: 'updateLookup' })
    .on('change', data => {
        if (data.operation == "insert") {
            var voluteerController = require("@controller/volunteerController");
            voluteerController.getVolunteersNearMe(data.fullDocument.location.coordinates, "3000")
                .then(volunteers => {
                    volunteers.forEach(volunteer => {

                        var pushdata = "EMERGENCY!! Help needed"


                        firebase.pushNotification(pushdata, volunteer.deviceToken)
                    })
                })
                .catch(error => {
                    console.log(error)
                })
        } else if (data.operation == "update") {
            if (data.fullDocument.activeRequest.status == "assigned") {

                var pushdata = "Don't worry. Volunteers are heading to you location"


                firebase.pushNotification(pushdata, data.fullDocument.deviceToken)
            }
        }

    })

module.exports = controller 