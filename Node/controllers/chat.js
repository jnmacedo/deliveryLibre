exports.indexHTML = function(req, res) {

	
	res.sendFile(__dirname + '/Index.html')

}

exports.css = function(req, res) {

	res.sendFile(__dirname + '/style.css')

} 