const mongoose = require('mongoose');
const Step = require('../models/step');

mongoose.connect(process.env.DB_URI);

/**
* GET instructions for furniture
* @param {number} id id of furniture
* @returns {object}
*/
module.exports = (id, context, callback) => {
  Step.findOne({ id: id }, (err, step) => {
    if (err) {
      return callback(err);
    }

    callback(null, step);
  })
};
