var Conversation = require('../model/conversation.js'),
    User = require('../model/user.js'),
    meli = require('mercadolibre'),
    config = require('../config.js').config,
    url = require('url'),
    userCtlr = require('./user.js')

//constructores de paquetes
var meliObject = new meli.Meli(config.clint_id, config.secretkey, '', '')

//GET Obtener conversaciones por comprador
exports.findBySeller = function(req, res) {

	var client_id = 0,
		tokenRecibido = req.param('token'),
		mode="seller"	

	console.log("conversation tokenRecibido: " + tokenRecibido)

	userCtlr.clientIdByToken(tokenRecibido, function(clientId){

		console.log("clienteObtenido: " + clientId)

		searchOrder(clientId, tokenRecibido, mode, res)	
	})

}

//GET Obtener conversaciones por vendedor
exports.findByBuyer = function(req, res) {

	var client_id = 0,
		tokenRecibido = req.param('token'),
		mode="buyer"	


	console.log("conversation tokenRecibido: " + tokenRecibido)

	userCtlr.clientIdByToken(tokenRecibido, function(clientId){

		console.log("clienteObtenido: " + clientId)

		searchOrder(clientId, tokenRecibido, mode, res)	
	})

}


function searchOrder(clientId, tokenRecibido, mode, res){

	console.log("recibe searOrder client "+clientId+" token "+tokenRecibido)

    var params = {buyer:clientId, access_token:tokenRecibido},
        seen = false,
        path = config.pathURLOrders

    if(mode=="seller")
    	params = {seller:clientId, access_token:tokenRecibido}        

    console.log("sear order param "+params)

   	
    meliObject.get(path, params, function(err, body){

        var jsonString = (JSON.stringify(body)),
            jsonObj = JSON.parse(jsonString)      

        console.log("json obtenido buyer "+jsonString)

        var paging = jsonObj["paging"],
            total = paging["total"]

        if(total > 0){

		    for (var key in jsonObj) {

		    	console.log("json obtenido buyer.key "+key)

		        if (jsonObj.hasOwnProperty(key)) {
		            if(key=="results" && seen==false){
						seen = true
		            	var items = jsonObj[key]

		            	console.log("json obtenido buyer.results entro")
		            	
		            	loopConv(items.length-1, items, res, mode, clientId)
		            }
		        }
		    }
		} 
		else
		{
			console.log("sin registros")
			res.send({successs:true,list:[]})
		}
    })  
}

function addConv(cursor, items, mode, callback) {
    var item = items[cursor], 
    id_compra = item["id"]

    console.log("sear order ideCompra"+id_compra)

    //Me fijo si ya existe la conversacion
    Conversation.findOne({ 'id_compra' :  id_compra }, function(err, compra) {

            console.log("entro a la funcion que se fija si existe la compra")
            
            if (err)
                console.log("hubo un error al traer la conversacion nueva: " + err)

            else if (!compra){
                //No existe la compra, la agrego  

                console.log("No existe la compra, la voy a agregar")

                var buyer = item["buyer"], 
                seller = item["seller"]

                //Creo un nuevo objeto conversacion para guardarlo
                var newConv = new Conversation()

                newConv.id_compra = id_compra
                newConv.status_compra = item["status"]
                newConv.currency_id = item["currency_id"]
                newConv.total_amount = item["total_amount"]
                newConv.date_created = item["date_created"]  
                newConv.id_buyer = buyer["id"]
                newConv.nickname_buyer = buyer["nickname"]
                newConv.id_seller = seller["id"]
                newConv.nickname_seller = seller["nickname"]
                newConv.type = mode
                
                var order_items = item["order_items"];
                var producto = order_items[0];
                var itemTitulo = producto["item"];
                var titulo = itemTitulo["title"];
                newConv.product_name = titulo;

                newConv.save(function(err){
                    if(err){
                        console.log("hubo error:" + err)
                    }
                    console.log("Guardo correctamente la conversacion: " + id_compra)
	                cursor--
	    			callback(cursor)                 
                })
            }else{
                compra.status_compra = item["status"]

                compra.save(function(err){
                    if(err){
                        console.log("hubo error:" + err)
                    }
                    console.log("Modifico correctamente la conversacion: " + compra.status_compra)
	                cursor--
	    			callback(cursor)                     
                })
            }
     })
}
  
function loopConv(cursor, items, res, mode, clientId) {
	console.log("CURSORRR!!!!!!!!!!!!!!!!!!!" + cursor)
    addConv(cursor, items , mode, function(cursor) {
    if (cursor >= 0) loopConv(cursor, items, res, mode, clientId)

    if (cursor < 0) searchConv(clientId, mode, res)
  })
}

function searchConv(clientId, mode, res){

	if(mode=="buyer"){
	    Conversation.find({"id_buyer" : clientId}, function(err, compras){
	        if(err){
	            console.log("error al traer las conversaciones: "+err)
	        }
	        else{
	        	console.log("Fui a buscar las compras de este pibe, son estas: " + compras)
	        	res.status(200)
	        	res.send({successs:true,list:compras})
	        } 

	    })
	}
	else if(mode == "seller"){
	    Conversation.find({"id_seller" : clientId}, function(err, compras){
	        if(err){
	            console.log("error al traer las conversaciones: "+err)
	        }else{
	        	console.log("Fui a buscar las ventas de este pibe, son estas: " + compras)
	        	res.status(200)
	        	res.send({successs:true,list:compras})
	        }

	    })
	}

}
