var mongoose = require('mongoose'),
    Schema = mongoose.Schema

var userSchema = new Schema(
  {
    clientId: {type: String},
    token: {type: String},
    deviceId: {type: String}
  })

module.exports = mongoose.model('User', userSchema)