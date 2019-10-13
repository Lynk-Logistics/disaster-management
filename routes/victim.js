const router = require('express').Router();
var victimController = require("@controller/victimController")

router.post("/getrescuerequestnearby", victimController.getRescueRequestsNearMeRest)

router.post("/upsertrescuerequest", victimController.upsertRescueRequest)

router.post("/getactiverescuerequeststatus", victimController.getRequestsStatus)


module.exports = {
    router: router
}