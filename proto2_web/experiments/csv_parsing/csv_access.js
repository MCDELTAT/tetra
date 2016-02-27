/*var data = {};
var baseElementName = "sphere";
var counter = 1;
for (var i=1; i<=10; i++){
	var fnKey = baseElementName.concat(i.toString());
	data[fnKey] = i;
}

console.log("Contents of Data object are: ", data.sphere1);
console.log("Contents of Data object are: ", data.sphere7);*/

var fs = require('fs');
var csv = require('fast-csv');
var stream = fs.createReadStream('tetra_short.csv');

var csvStream = csv()
	.on("data", function(data){
		console.log(data);
	})
	.on("end", function(){
		console.log("done");
	});

stream.pipe(csvStream);	