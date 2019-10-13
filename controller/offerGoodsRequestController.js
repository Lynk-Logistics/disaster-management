var mongoose = require("mongoose");
var offerGoodsRequestModel = require("@schema/offerGoodsRequest").model
var controller = {}
var assert = require('assert')


controller.newOfferGoodsRequest = async (req, res) => {
    try {
        var { name, coordinates, mobile, deviceToken, goods } = req.body
        var offerGoodsRequest = await offerGoodsRequestModel.create({
            name, location: { type: "Point", coordinates }, mobile, deviceToken, goods
        })
        return res.status(200).json({ error: null, data: offerGoodsRequest })
    } catch (error) {
        console.log(error)
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.send({ "error": error });
    }
}

var getOfferGoodsRequestNearby = (coordinates, radius) => {
    return offerGoodsRequestModel.aggregate([
        {
            $geoNear: {
                near: { type: "Point", coordinates: coordinates },
                distanceField: "distance",
                maxDistance: radius
            }
        }
    ])
}

controller.getOfferGoodsRequestNearby = getOfferGoodsRequestNearby

controller.getOfferGoodsRequestNearbyRest = async (req, res) => {
    try {
        assert(req.body.coordinates, "Coordinates not found");
        var nearbyOfferGoodsRequest = await offerGoodsRequestModel.aggregate([{
            $geoNear: {
                near: { type: "Point", coordinates: req.body.coordinates },
                distanceField: "distance",
                maxDistance: req.body.radius ? req.body.radius : 1000
            }
        }, {
            $match: {
                $or: [{
                    status: "active"
                }, {
                    status: "assigned"
                }]
            }
        }])
        return res.status(200).json({ error: null, data: nearbyOfferGoodsRequest })
    } catch (error) {
        console.log(error);
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.send({ "error": error });
    }
}
module.exports = controller