const mongoose = require('mongoose');
const Furniture = require('../models/Furniture');

mongoose.connect(process.env.DB_URI);

/**
* GET furniture data
* @param {number} id id of furniture
* @returns {object}
*/
module.exports = (id, context, callback) => {
  Furniture.findOne({ barcode: id }, (err, furniture) => {
    if (err) {
      return callback(err);
    }
    return callback(null, JSON.parse(JSON.stringify(furniture)));
  });
};
