const router = require('express').Router();
var offerGoodsRequestController = require("@controller/offerGoodsRequestController")

router.post("/new", offerGoodsRequestController.newOfferGoodsRequest)
router.post("/nearbyrequests", offerGoodsRequestController.getOfferGoodsRequestNearbyRest)
module.exports = {
    router: router
}