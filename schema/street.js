const mongoose = require("mongoose")
const Schema = mongoose.Schema;
mongoose.Promise = global.Promise;
const polygonSchema = require("@schema/polygonSchema")

var connection = require("@config/connection")

var streetSchema = new Schema({

    name: {
        type: String
    },
    location: {
        type: polygonSchema
        //rectangle
    },
    zone: {
        type: Schema.Types.ObjectId,
        ref: 'zone'
    },
    unsafe: {
        type: Boolean,
        default: false
    },
    severity: {
        type: Number,
        enum: [0, 1, 2, 3]
    }

})


var getModel = () => {
    return connection.model("street", streetSchema)
}


module.exports = {
    model: getModel()
}