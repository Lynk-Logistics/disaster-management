const mongoose = require("mongoose")
const Schema = mongoose.Schema;
mongoose.Promise = global.Promise;

var connection = require("@config/connection")


var volunteerSchema = new Schema({
    name: {
        type: String
    },
    location: {
        type: {
            type: String,
            enum: ["Point"]
        },
        coordinates: [{
            type: Number
        }]
    },
    mobile: {
        type: String
    },
    active: {
        type: Boolean,
        default: true
    },
    deviceToken: {
        type: String
    }

}, {
    timestamps: true
})

volunteerSchema.index({ location: "2dsphere" })


var getModel = () => {
    return connection.model("volunteer", volunteerSchema)
}


module.exports = {
    model: getModel()
}