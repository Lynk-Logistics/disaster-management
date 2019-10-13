const mongoose = require("mongoose")
const Schema = mongoose.Schema;
mongoose.Promise = global.Promise;

var connection = require("@config/connection")


var victimSchema = new Schema({
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
    activeRequest: {
        present: {
            type: Boolean,
            default: false
        },
        createdAt: {
            type: Date
        },
        status: {
            type: String,
            default: "active",
            enum: ["active", "assigned", "closed"]
        },
        count: {
            type: Number,
            default: 1
        },
        takenBy: {
            type: Schema.Types.ObjectId,
            ref: 'volunteer'
        },
        medicalSupport: {
            type: Boolean
        }
    },

    mobile: {
        type: String
    },
    deviceToken: {
        type: String
    }
}, {
    timestamps: true
})

victimSchema.index({ location: "2dsphere" })

var getModel = () => {
    return connection.model("victim", victimSchema)
}


module.exports = {
    model: getModel()
}