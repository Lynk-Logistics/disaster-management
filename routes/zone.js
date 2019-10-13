const router = require('express').Router();
var zoneController = require("@controller/zoneController")

router.get("/getallzones", zoneController.getAllZones)

router.post("/getzonedetails", zoneController.getZoneData)


module.exports = {
    router: router
}