const router = require('express').Router();
var goodsRequestController = require("@controller/goodsRequestController")

router.post("/new", goodsRequestController.newGoodsRequest)
router.post("/nearbyrequests", goodsRequestController.getGoodsRequestNearbyRest)

module.exports = {
    router: router
}