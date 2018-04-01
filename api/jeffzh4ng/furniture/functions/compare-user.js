const mongoose = require('mongoose');
const UserLog = require('../models/UserLog');
const lib = require('lib')({ token: process.env.token });
const sms = lib.messagebird.sms['@0.1.3'];

mongoose.connect(process.env.DB_URI);

/**
* COMPARES if user is AFK > 5 mins
* @param {string} username account name of user
* @param {number} step the step the user is currently on
* @returns {any}
*/
module.exports = (username, step, context, callback) => {
  UserLog.findOne({ username: username }, (err, user) => {
    if (err) {
      return err;
    }

    const d = new Date();
    const currentMinute = d.getMinutes();
    const oldMinute = user.date.getMinutes();

    if (currentMinute > oldMinute + 2) {
      sms.create({
        recipient: process.env.number,
        body: "Hey! It seems like you're stuck on step" + step.toString()
               + "Here are some additional resources to help: bitly[dot]com/98K8eH"
      }, function(err) {
        if (err) return callback(err);
        return callback(null, "sent!");
      });
    } else {
      return callback(null, "not sent");
    }
  });

};
