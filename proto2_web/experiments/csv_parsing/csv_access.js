/*
for (var i=1; i<=10; i++){
	var fnKey = baseElementName.concat(i.toString());
	data[fnKey] = i;
}

console.log("Contents of Data object are: ", data.sphere1);
console.log("Contents of Data object are: ", data.sphere7);*/

var fs = require('fs');
var csv = require('fast-csv');
var stream = fs.createReadStream('tetra_short.csv');

var parsedData = {};
var baseElementName = "contig";
var i = 1; //counter for line output

var csvStream = csv()
	//print out only the first 20 elements.
	.on("data", function(data){
		if (i<=300){
			var fnKey = baseElementName.concat(i.toString());
			parsedData[fnKey] = data;
		}
		i++;
	})
	.on("end", function(){
		console.log("done");
		//call the parsing functions in here to remain in scope.
		getMinMax();
	});

stream.pipe(csvStream);	

//now that parsedData is an object with all the data from csv in it,
//do some file additional parsing before sending it to graph
var graphMaxima = {
	"xMin": 0,
	"yMin": 0,
	"zMin": 0,
	"xMax": 0,
	"yMax": 0,
	"zMax": 0
};
//get the maxima/minima of each axis (dim)
function getMinMax(){
	for (var property in parsedData){
		if (parsedData.hasOwnProperty(property)){
			var tempArray = parsedData[property];
			//if the current lines value is less than the min, it is the new min.
			//console.log("Comparing the values "+parseFloat(tempArray[3])+" & "+parseFloat(graphMaxima["xMin"])+".");
			if (parseFloat(tempArray[3]) < parseFloat(graphMaxima["xMin"])){
				graphMaxima["xMin"] = tempArray[3];
				console.log("New X-Minimum: ", tempArray[3]);
			} 
			if (parseFloat(tempArray[3]) > parseFloat(graphMaxima["xMax"])){
				graphMaxima["xMax"] = tempArray[3];
				console.log("New X-Maximum: ", tempArray[3]);
			}
			//compare the Y values to find min & max.
			if (parseFloat(tempArray[4]) < parseFloat(graphMaxima["yMin"])){
				graphMaxima["yMin"] = tempArray[4];
				console.log("New Y-Minimum: ", tempArray[4]);
			} 
			if (parseFloat(tempArray[4]) > parseFloat(graphMaxima["yMax"])){
				graphMaxima["yMax"] = tempArray[4];
				console.log("New Y-Maximum: ", tempArray[4]);
			}
			//compare the Z values to find min & max.
			if (parseFloat(tempArray[5]) < parseFloat(graphMaxima["zMin"])){
				graphMaxima["zMin"] = tempArray[5];
				console.log("New Z-Minimum: ", tempArray[5]);
			} 
			if (parseFloat(tempArray[5]) > parseFloat(graphMaxima["zMax"])){
				graphMaxima["zMax"] = tempArray[5];
				console.log("New Z-Maximum: ", tempArray[5]);
			}
		}
	}
	console.log("The X-Minimum is: ",graphMaxima["xMin"]);
	console.log("The X-Maximum is: ",graphMaxima["xMax"]);
	console.log("The Y-Minimum is: ",graphMaxima["yMin"]);
	console.log("The Y-Maximum is: ",graphMaxima["yMax"]);
	console.log("The Z-Minimum is: ",graphMaxima["zMin"]);
	console.log("The Z-Maximum is: ",graphMaxima["zMax"]);
}

//split into species and list number of entries


