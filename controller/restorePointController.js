var mongoose = require("mongoose");
var restorePointModel = require("@schema/restorePoint").model
var controller = {}
var assert = require('assert')

var getRestorePointsWithZone = (zone) => {
    return restorePointModel.where('location').within().geometry(zone.location)

}
controller.getRestorePointsWithZone = getRestorePointsWithZone

controller.createNewRestorePoint = async (req, res) => {
    try {
        assert(req.body.coordinates, "Coordinates not found")
        var params = {}
        params.name = req.body.name;
        params.location = {
            type: "Point",
            coordinates: req.body.coordinates
        }
        params.mobile = req.body.mobile
        params.capacity = req.body.capacity
        params.occupantCount = req.body.occupantCount ? req.body.occupantCount : 0
        var restorePoint = await restorePointModel.create(params);
        return res.status(200).json({ error: null, data: restorePoint })
    } catch (error) {
        console.log(error);
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.send({ "error": error });
    }
}

var getRestorePointsNearby = (coordinates, radius) => {
    return restorePointModel.aggregate([{
        $geoNear: {
            near: { type: "Point", coordinates: coordinates },
            distanceField: "distance",
            maxDistance: radius
        }
    }])
}
controller.getActiveRestorePointsNearby = getRestorePointsNearby;
controller.getRestorePointsNearbyRest = async (req, res) => {
    try {
        assert(req.body.coordinates, "Coordinates not found");
        var nearbyActiveRestorePoints = await restorePointModel.aggregate([{
            $geoNear: {
                near: { type: "Point", coordinates: req.body.coordinates },
                distanceField: "distance",
                maxDistance: req.body.radius ? req.body.radius : 1000
            }
        }, {
            $match: {
                active: true
            }
        }])
        return res.status(200).json({ error: null, data: nearbyActiveRestorePoints })
    } catch (error) {
        console.log(error);
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.send({ "error": error });
    }
}

controller.getNearbyDetails = async (req, res) => {
    try {
        assert(req.body.coordinates, "Coordinates not found");
        var victimController = require("@controller/victimController")
        var offerGoodsRequestController = require("@controller/offerGoodsRequestController");
        var goodsRequestController = require("@controller/goodsRequestController");
        var volunteerController = require("@controller/volunteerController");
        var nearbyVolunteers = await volunteerController.getVolunteersNearMe(req.body.coordinates, req.body.radius ? req.body.radius : 1000);
        var nearbyRestorations = await getRestorePointsNearby(req.body.coordinates, req.body.radius ? req.body.radius : 1000)
        var nearbyRescueRequests = await victimController.getRescueRequestsNearMe(req.body.coordinates, req.body.radius ? req.body.radius : 1000)
        var nearbyOfferGoodsRequests = await offerGoodsRequestController.getOfferGoodsRequestNearby(req.body.coordinates, req.body.radius ? req.body.radius : 1000)
        var nearbyGoodsRequests = await goodsRequestController.getGoodsRequestNearby(req.body.coordinates, req.body.radius ? req.body.radius : 1000)

        return res.status(200).json({ error: null, data: { rescueRequests: nearbyRescueRequests, restorePoints: nearbyRestorations, offerGoodsRequest: nearbyOfferGoodsRequests, goodsRequest: nearbyGoodsRequests, volunteers: nearbyVolunteers } })

    } catch (error) {
        console.log(error)
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.send({ "error": error });
    }
}

module.exports = controller