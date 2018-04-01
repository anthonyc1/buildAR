const mongoose = require('mongoose');
const UserLog = require('../models/UserLog');

mongoose.connect(process.env.DB_URI);

/**
* UPDATE user log
* @param {string} username account name of user
* @returns {object}
*/
module.exports = (username, context, callback) => {
  console.log(Date.now());
  const user = new UserLog({
    username: username,
    date: Date.now()
  });

  user.save((err, user) => {
    if (err) {
      return callback(err);
    }

    callback(null, user);
  });
};
