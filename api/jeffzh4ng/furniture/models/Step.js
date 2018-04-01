const mongoose = require('mongoose');

const instructionSchema = mongoose.Schema({
  title: {
    type: String,
    required: true
  },
  data: {
    type: String,
    required: true
  },
  objLink: {
    type: String,
    required: true
  },
  imgLink: {
    type: String,
    required: true
  },
  textureLink: {
    type: String,
    required: true
  }
});

const stepSchema = mongoose.Schema({
  barcode: {
    type: Number,
    required: true
  },
  steps: [instructionSchema]
});

module.exports = mongoose.model('Step', stepSchema);
