const mongoose = require('mongoose');

const sponsorSchema = mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    mobileNo: {
        type: String,
        required: true
    },
    address: {
        type: String,
        required: true
    },
    location: {
        type: Object       
    },
    offering: [{
        type: String,
        required: true
    }],
    description: {
        type: String
    },
    status: {
        type: String,
        required: true
    },
    date: {
        type: String,
        required: true
    }
   
});

module.exports = mongoose.model('Sponsor', sponsorSchema);