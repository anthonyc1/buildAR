const mongoose = require('mongoose');

const instructionSchema = mongoose.Schema({
  msg: {
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

const stepSchema = mongoose.Schema({
  id: {
    type: Number,
    required: true
  },
  steps: [instructionSchema]
});

module.exports = mongoose.model('Step', stepSchema);
