var mongoose = require('mongoose'),
    Schema = mongoose.Schema

var convSchema = new Schema(
  {
    id_compra: {type: Number}, 
    status_compra: {type: String}, 
    currency_id: {type: String},
    total_amount: {type: Number},
    date_created: {type: Date, default: Date.now},
    date_updated: {type: Date, default: Date.now},
    id_buyer: {type: Number},
    nickname_buyer: {type: String}, 
    id_seller: {type: Number},
    nickname_seller: {type: String},
    product_name: {type: String},
    type: {type: String}
  })

module.exports = mongoose.model('Conversation', convSchema)