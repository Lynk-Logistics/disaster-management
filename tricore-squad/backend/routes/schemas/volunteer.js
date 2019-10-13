const mongoose = require('mongoose');

const volunteerSchema = mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    mobileNo: {
        type: Number,
        required: true
    },
    address: {
        type: String,
        required: true
    },
    aadhaar: {
        type: String
    },
    location_lat: {
        type: String,
        required: true
    },
    location_long: {
        type: String,
        required: true
    },
    date: {
        type: Date,
        required: true,
        default: Date.now()
    }
   
});

module.exports = mongoose.model('Volunteer', volunteerSchema);