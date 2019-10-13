const router = require('express').Router();
var restorePointController = require("@controller/restorePointController")

router.post("/createrestorepoint", restorePointController.createNewRestorePoint)
router.post("/getnearbyrestorepoint", restorePointController.getRestorePointsNearbyRest)
router.post("/getnearbydetails", restorePointController.getNearbyDetails)

module.exports = {
    router: router
}