const mongoose = require('mongoose');
const UserLog = require('../models/UserLog');
const lib = require('lib')({ token: process.env.token });
const sms = lib.messagebird.sms['@0.1.3'];

mongoose.connect(process.env.DB_URI);

/**
* COMPARES if user is AFK > 5 mins
* @param {string} username account name of user
* @returns {any}
*/
module.exports = (username, context, callback) => {
  UserLog.findOne({ username: username }, (err, user) => {
    if (err) {
      return err;
    }

    const d = new Date();
    const currentMinute = d.getMinutes();
    const oldMinute = user.date.getMinutes();

    if (currentMinute > oldMinute + 2) {
      sms.create({
        recipient: "13474797101", // (required)
        body: "hello world" // (required)
      }, function(err) {
        if (err) return callback(err);
        return callback(null, "sent!");
      });
    } else {
      return callback(null, "user still active, no sms sent");
    }
  });

};
