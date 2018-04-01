const mongoose = require('mongoose');

const userLogSchema = mongoose.Schema({
  username: {
    type: String,
    required: true
  },
  date: {
    type: Date,
    required: true
  }
});

module.exports = mongoose.model('UserLog', userLogSchema);
