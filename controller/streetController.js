var mongoose = require("mongoose");
var streetModel = require("@schema/street").model
var controller = {}
var assert = require('assert')
controller.markStreetAsUnSafe = async (req, res) => {
    try {
        assert(req.body.streetId, "Street Id not found")
        await streetModel.findByIdAndUpdate(streetId, {
            $set: {
                unsafe: true
            }
        }).exec();
        return res.status(200).json({ msg: "Updated successfully" })
    } catch (error) {
        console.log(error);
        error.status = error.status || 500
        if (error instanceof assert.AssertionError) error.status = 400;
        return res.send({ "error": error });
    }
}



module.exports = controller 