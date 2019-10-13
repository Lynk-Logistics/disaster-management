const mongoose = require('mongoose');

const missingpeopleSchema = mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    gender: {
        type: String,
        required: true
    },
    age: {
        type: String,
        required: true
    },
    address: {
        type: String,
        required: true
    },
    description: {
        type: String
    },
    photo: {
        type: String
    },
    contactName: {
        type: String,
        required: true
    },
    contactMobileNumber: {
        type: String,
        required: true
    },    
    date: {
        type: String,
        required: true
    }
   
});

module.exports = mongoose.model('Missingpeople', missingpeopleSchema);