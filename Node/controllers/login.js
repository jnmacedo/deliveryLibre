var meli = require('mercadolibre')
var config = require('../config.js').config

//constructores de paquetes
var meliObject = new meli.Meli(config.clint_id, config.secretkey, '', '')

var getAuthURL = function(req, res){
	var urlStr = meliObject.getAuthURL() 
	var jsonRes = {
		successs:true,
		url: urlStr
	}
	res.status(200).jsonp(jsonRes);
}

var getToken = function(req, res){
  var redirect_uri = config.redirect_uri
  var code = req.param('code')

  meliObject.authorize(code, redirect_uri, function(err, body){
      res.status(200).jsonp(body)
  }) 
}

exports.getToken = getToken
exports.getAuthURL = getAuthURL