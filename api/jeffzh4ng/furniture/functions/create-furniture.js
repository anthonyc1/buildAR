const mongoose = require('mongoose');
const Furniture = require('../models/Furniture.js');

mongoose.connect(process.env.DB_URI);

/**
* POST furniture data
* @param {number} id id of furniture
* @param {string} name name of furniture
* @param {string} obj_link link to obj file of furniture
* @param {string} img_link link to img of furniture
* @param {string} texture_link link to texture file of furniture
* @returns {object}
*/
module.exports = (id, name, obj_link, img_link, texture_link, context, callback) => {
  const furniture = new Furniture({
    barcode: id,
    name: name,
    obj_link: obj_link,
    img_link: img_link,
    texture_link: texture_link
  });

  furniture.save((err, furniture) => {
    if (err) {
      return callback(err);
    }

    return callback(null, JSON.parse(JSON.stringify(furniture)));
  });
};
