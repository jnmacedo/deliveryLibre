//main server app

//paquetes requeridos
var express         = require("express"),
    app             = express(),
	  http = require('http').Server(app),
    bodyParser      = require("body-parser"), 
    methodOverride  = require("method-override"),
    mongoose        = require('mongoose'),
    io = require('socket.io')(http) 

// Middlewares
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(methodOverride());


//conexion a bd
mongoose.connect('mongodb://admin:admin@ds055680.mongolab.com:55680/deliverylibre', function(err, res){
  //mongodb://<dbuser>:<dbpassword>@ds055680.mongolab.com:55680/deliverylibre
//mongoose.connect('mongodb://localhost/ml', function(err, res){
  if(err)	
	  console.log(err)
  else
	console.log('Connect to db')
}) 

//controladores
var logInCtlr = require('./controllers/login'),
  userCtlr = require('./controllers/user'),
  convCtlr = require('./controllers/conversation'), 
  chatCtlr = require('./controllers/chat')

// API routes
var mlRouter = express.Router()

mlRouter.get('/inicio', logInCtlr.getAuthURL) 
mlRouter.get('/code', logInCtlr.getToken)
mlRouter.post('/addUser', userCtlr.addUser)
mlRouter.get('/convBuyer', convCtlr.findByBuyer)
mlRouter.get('/convSeller', convCtlr.findBySeller) 
mlRouter.get('/chat', chatCtlr.indexHTML)
mlRouter.get('/css', chatCtlr.css)
app.use(mlRouter) 

io.on('connection', function(socket){
  socket.on('msg client', function(msg){
    
    io.emit('msg server', msg)
  })
})

app.set('port', (process.env.PORT || 5000))

http.listen(app.get('port'), function() {

	console.log("Node app is running at :" + app.get('port'))

})