const router = require('express').Router();
var volunteerController = require("@controller/volunteerController")


router.post("/new", volunteerController.createNewVolunteer)
router.patch("/updatecurrentlocation", volunteerController.updateCurrentLocation)
router.post("/nearbyvolunteers", volunteerController.getVolunteersNearMeRest)

module.exports = {
    router: router
}