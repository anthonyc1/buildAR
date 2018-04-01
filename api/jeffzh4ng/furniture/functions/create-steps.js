const mongoose = require('mongoose');
const Step = require('../models/step');

mongoose.connect(process.env.DB_URI);

/**
* POST steps for furniture
* @param {number} id id of furniture
* @param {array} steps list of steps
* @returns {object}
*/
module.exports = (id, steps, context, callback) => {
  const step = new Step({
    id: id,
    steps: steps
  });

  step.save((err, step) => {
    if (err) {
      return callback(err);
    }

    callback(null, JSON.parse(JSON.stringify(step)));
  });
};
