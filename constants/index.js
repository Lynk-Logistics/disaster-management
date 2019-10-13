let basePath =  "https://lynk-aid-backend.herokuapp.com/"

module.exports = {
    request_rescue: basePath + "victim/upsertrescuerequest",
    offer_accomodation: basePath + "restorepoint/createrestorepoint",
    request_goods: basePath + "goodsrequest/new",
    offer_goods:  basePath + "offergoodsrequest/new",
    nearby_details: basePath + "restorepoint/getnearbydetails", 
    app_color: "#4caf50"
}