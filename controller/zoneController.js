var mongoose = require("mongoose");
var zoneModel = require("@schema/zone").model
var controller = {}
var assert = require('assert')
var victimController = require("@controller/victimController")
var restorePointController = require("@controller/restorePointController")

controller.getAllZones = async (req, res) => {
    try {
        var zones = await zoneModel.find({}).lean().exec()
        var zonesPromiseArray = zones.map(zone => {
            return new Promise(async (resolve, reject) => {
                try {
                    zone.rescueCount = await victimController.getRescueRequestCountWithinZone(zone)
                    resolve(zone)
                } catch (error) {
                    reject(error)
                }
            })
        })

        var response = await Promise.all(zonesPromiseArray)
        return res.status(200).json({ error: null, data: response })
    } catch (error) {
        console.log(error);
        return res.json({ error: error })
    }
}


controller.getZoneData = async (req, res) => {
    try {
        assert(req.body.zoneId, "Zone id not present")
        var zone = await zoneModel.findById(req.body.zoneId).lean().exec()
        zone.rescueRequests = await victimController.getRescueRequestWithinZone(zone);
        zone.restorePoints = await restorePointController.getRestorePointsWithZone(zone)
        delete zone.location;
        return res.status(200).json({ error: null, data: zone })

    } catch (error) {
        console.log(error);
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) {

            error.status = 400;
            error.message = "Zone id not found on the request"
        }
        return res.json({ "error": error });
    }
}


module.exports = controller 