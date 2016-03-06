//node require statements
var fs = require('fs');
var csv = require('fast-csv');
var stream;
var fileInput = 'bd002ae93234bfaa06b9b935d9082df4';

var parsedData = {};
var baseElementName = "contig";
var i = 1; //counter for line output

parseData(fileInput);

function parseData(inputFile){
	stream = fs.createReadStream(inputFile);
	var csvStream = csv()
	//add each line of CSV to parsedData object
	.on("data", function(data){
		//TO-DO: check data integrity here.
		if(data != "Contig,Organism,Size,dim1,dim2,dim3"){ //skip first line
			if (i<=2500){
				var fnKey = baseElementName.concat((i-2).toString());
				parsedData[fnKey] = data;
			}
		}
		i++;
	})
	.on("end", function(){
		console.log("done");
		//call the parsing functions in here to remain in scope.
		//console.log(parsedData);
		getMinMax();
		createSpeciesObjects();
	});

stream.pipe(csvStream);	
}
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

var speciesArray = []; //array that will contain the different species
//function takes in parsedData from above, and splits the different species into
//different objects. These objects are then stored in an array.
function createSpeciesObjects(){
	var speciesName1 = "";
	var speciesCount = -1;
	var i = 1;
	//loop through parsedData Object, for each line, add to corresponding species obj.
	for (var property in parsedData){
		if (parsedData.hasOwnProperty(property)){
			var tempArray = parsedData[property];
			var fnKey = baseElementName.concat((i).toString());
			//if the species name changes, create a new object for it.
			if (speciesName1 != tempArray[1]){
				speciesName1 = tempArray[1]; 
				speciesArray.push(new Object())
				speciesCount++; //start the count at zero.
				console.log(typeof speciesName1);
			}
			//speciesArray[speciesCount] is object, as above, add data entry to that species object.
			speciesArray[speciesCount][fnKey] = tempArray;
			i++;
		}
	}
	console.log("The contents of array are: \n",speciesArray[2]); //method access single species obj.
	console.log("The value of speciesCount is: ",(speciesCount+1));
}