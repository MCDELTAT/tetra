var express = require('express');
var multer = require('multer'),
		bodyParser = require('body-parser'),
		path = require('path');

var app = new express();
app.use(bodyParser.json());

//view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

//route for GET request
app.get('/', function(req,res){
	res.render('index');
});

app.post('/', multer({dest: './uploads/',limits: {fileSize: 1000000, files: 1}}).single('upl'), function(req,res){
	console.log(req.body); //form fields
	console.log(req.file.filename); //form files
	res.status(204).end();
});

var port = 3000;
app.listen(port, function(){
	console.log('listening on port '+port);
});		