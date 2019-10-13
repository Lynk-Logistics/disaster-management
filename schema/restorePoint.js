const mongoose = require("mongoose")
const Schema = mongoose.Schema;
mongoose.Promise = global.Promise;

var connection = require("@config/connection")

var restorePointSchema = new Schema({

    name: {
        type: String
    },
    location: {
        type: {
            type: String,
            enum: ["Point"]
        },
        coordinates: [Number]

    },
    mobile: {
        type: String
    },
    active: {
        type: Boolean,
        default: true
    },
    capacity: {
        type: Number,
        default: 0
    },
    occupantCount: {
        type: Number,
        default: 0
    },
    address: {
        type: String
    }

})

restorePointSchema.index({ location: "2dsphere" })

var getModel = () => {
    return connection.model("restorepoint", restorePointSchema)
}


module.exports = {
    model: getModel()
}