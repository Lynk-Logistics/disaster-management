const mongoose = require("mongoose")
const Schema = mongoose.Schema;
mongoose.Promise = global.Promise;
const polygonSchema = require("@schema/polygonSchema")

var connection = require("@config/connection")

var zoneSchema = new Schema({

    name: {
        type: String
    },
    location: {
        type: polygonSchema
        //hexagon
    },
    severity: {
        type: Number,
        enum: [0, 1, 2, 3]
    },
    unsafe: {
        type: Boolean,
        default: false
    }

}, {
    timestamps: true
})


var getModel = () => {
    return connection.model("zone", zoneSchema)
}


module.exports = {
    model: getModel()
}