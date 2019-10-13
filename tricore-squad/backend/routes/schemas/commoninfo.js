const mongoose = require('mongoose');

const commoninfoSchema = mongoose.Schema({
    infoHeaders: [{
        type: String
    }],
    updatedDate: {
        type: String,
        required: true
    },
    infoObject: {
        type: Object,
        required: true
    }
   
});

module.exports = mongoose.model('Commoninfo', commoninfoSchema);