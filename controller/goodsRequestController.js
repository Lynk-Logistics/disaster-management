var mongoose = require("mongoose");
var goodsRequestModel = require("@schema/goodsRequest").model
var controller = {}
var assert = require('assert')


controller.newGoodsRequest = async (req, res) => {
    try {
        var { name, coordinates, mobile, deviceToken, goods } = req.body
        var goodsRequest = await goodsRequestModel.create({
            name, location: { type: "Point", coordinates }, mobile, deviceToken, goods
        })
        return res.status(200).json({ error: null, data: goodsRequest })
    } catch (error) {
        console.log(error)
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.send({ "error": error });
    }
}
var getGoodsRequestNearby = (coordinates, radius) => {
    return goodsRequestModel.aggregate([
        {
            $geoNear: {
                near: { type: "Point", coordinates: coordinates },
                distanceField: "distance",
                maxDistance: radius
            }
        }
    ])
}

controller.getGoodsRequestNearby = getGoodsRequestNearby

controller.getGoodsRequestNearbyRest = async (req, res) => {
    try {
        assert(req.body.coordinates, "Coordinates not found");
        var nearbyGoodsRequest = await goodsRequestModel.aggregate([{
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
        return res.status(200).json({ error: null, data: nearbyGoodsRequest })
    } catch (error) {
        console.log(error);
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.send({ "error": error });
    }
}

module.exports = controller