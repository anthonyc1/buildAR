const mongoose = require('mongoose');

const furnitureSchema = mongoose.Schema({
  barcode: {
    type: Number,
    required: true
  },
  name: {
    type: String,
    required: true
  },
  obj_link: {
    type: String,
    required: true
  },
  img_link: {
    type: String,
    required: true
  },
  texture_link: {
    type: String,
    required: true
  }
});

module.exports = mongoose.model('Furniture', furnitureSchema);
