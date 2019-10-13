const mongoose = require("mongoose")
const Schema = mongoose.Schema;
mongoose.Promise = global.Promise;

var connection = require("@config/connection")

var goodsRequestSchema = new Schema({
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
    status: {
        type: String,
        enum: ["active", "assigned", "closed"],
        default: "active"
    },
    deviceToken: {
        type: String
    },
    goods: [{
        name: {
            type: String
        },
        count: {
            type: Number
        }
    }]
}, {
    timestamps: true
})

goodsRequestSchema.index({ location: "2dsphere" })

var getModel = () => {
    return connection.model("goodsrequest", goodsRequestSchema)
}


module.exports = {
    model: getModel()
}