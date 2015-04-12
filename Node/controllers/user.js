var User = require('../model/user.js')

//POST Insertar un usuario
exports.addUser = function(req, res){ 

  var userAux = new User(
    {
      clientId: req.body.clientId,
      token: req.body.token,
      deviceId: req.body.deviceId
    } 
  ) 

   userAux.save(function(err) {
      if(!err) {
        console.log("usuario creado")
        return res.send({ status: 'OK', userAux:userAux })
      } else {
        console.log(err)
        if(err.name == 'ValidationError') {
          res.statusCode = 400
          res.send({ error: 'Validation error' })
        } else {
          res.statusCode = 500
          res.send({ error: 'Server error' })
        }
        console.log('Internal error(%d): %s',res.statusCode,err.message)
      }
    })
}

//clientId por token
exports.clientIdByToken = function(token, callback){ 

    console.log("clientIdByToken tokenRecibido: " + token)
    var clientId = 0

    User.findOne({"token" : token}, function(err, user) {
        if (err){
            //Error
            console.log("error al buscar el usuario con el token: " + err)
        }
        else if (!user){ 
            console.log("No existe el usuario")             
        }
        else{     
            console.log("user.clientid lo muestra "+user.clientId)      
            clientId = user.clientId
        } 
        callback(clientId)
    })
}