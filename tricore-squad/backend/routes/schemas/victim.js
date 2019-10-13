const mongoose = require('mongoose');

const Schema = mongoose.Schema;

let commentSchema = new Schema({
    commentedBy: {
        type: Schema.Types.ObjectId,
        required: true,
        refPath: 'byRole'
    },
    updatedOn: {
        type: Date,
        required: true,
        default: Date.now()
    },
    comment: {
        type: String,
        required: true
    },
    byRole: {
        type: String,
        required: true,
        enum: ['Volunteer', 'Sponsor', 'Victim']
    }
});

const victimSchema = new Schema({
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
    location_lat: {
        type: String,
        required: true
    },
    location_long: {
        type: String,
        required: true
    },
    needs: [{
        type: String,
        required: true
    }],
    headCount: {
        type: Number,
        required: true,
        default: 1
    },
    description: {
        type: String
    },
    status: {
        type: String,
        required: true
    },
    comments: [commentSchema],
    date: {
        type: Date,
        required: true,
        default: Date.now()
    }
   
});

module.exports = mongoose.model('Victim', victimSchema);