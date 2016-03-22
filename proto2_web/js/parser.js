var lineCount;

//Function to verify my file is not corrupt
function verifyObject(){
	lineCount = data.data.length;
	console.log("Line count is: ",lineCount);
}	

var graphMaxima = {
	"xMin": 0,
	"yMin": 0,
	"zMin": 0,
	"xMax": 0,
	"yMax": 0,
	"zMax": 0
};

//get the maxima/minima of each axis (dim)
//No input paramters, no returns. Modifies graphMaxima[]
function getMinMax() {
	for (var i=0; i<lineCount; i++){
		//if the current lines value is less than the min, it is the new min.
		if (data.data[i].dim1 < graphMaxima["xMin"]){
			graphMaxima["xMin"] = data.data[i].dim1;
			//console.log("New X-Minimum: ", graphMaxima["xMin"]);
		}
		if (data.data[i].dim1 > graphMaxima["xMax"]){
			graphMaxima["xMax"] = data.data[i].dim1;
			//console.log("New X-Maximum ", graphMaxima["xMax"]);
		}
		//compare the Y values to find min & max.
		if (data.data[i].dim2 < graphMaxima["yMin"]){
			graphMaxima["yMin"] = data.data[i].dim2;
			//console.log("New Y-Minimum: ", graphMaxima["yMin"]);
		}
		if (data.data[i].dim2 > graphMaxima["yMax"]){
			graphMaxima["yMax"] = data.data[i].dim2;
			//console.log("New Y-Maximum ", graphMaxima["yMax"]);
		}
		//compare the Z values to find min & max.
		if (data.data[i].dim3 < graphMaxima["zMin"]){
			graphMaxima["zMin"] = data.data[i].dim3;
			//console.log("New Z-Minimum: ", graphMaxima["zMin"]);
		}
		if (data.data[i].dim3 > graphMaxima["zMax"]){
			graphMaxima["zMax"] = data.data[i].dim3;
			//console.log("New Z-Minimum: ", graphMaxima["zMax"]);
		}
	}
	console.log("The X-Minimum is: ",graphMaxima["xMin"]);
	console.log("The X-Maximum is: ",graphMaxima["xMax"]);
	console.log("The Y-Minimum is: ",graphMaxima["yMin"]);
	console.log("The Y-Maximum is: ",graphMaxima["yMax"]);
	console.log("The Z-Minimum is: ",graphMaxima["zMin"]);
	console.log("The Z-Maximum is: ",graphMaxima["zMax"]);

	return graphMaxima;
}

var speciesArray = []; //array that will contain the different species
//function takes in data from above, and splits the different species into
//different objects. These objects are then stored in an array.
function createSpeciesObjects(){
	var speciesName1 = "";
	var speciesCount = -1;
	//loop through parsedData Object, for each line, add to corresponding species obj.
	for (var i=0; i<lineCount; i++){
		//if the species name changes, create a new object for it.
		if (speciesName1 != data.data[i].Organism){
			speciesName1 = data.data[i].Organism; 
			speciesArray.push(new Object())
			speciesCount++; //start the count at zero.
		}
		//speciesArray[speciesCount] is object, as above, add data entry to that species object.
		speciesArray[speciesCount][i] = data.data[i];
	}
	console.log("The contents of array are: \n",speciesArray[1]); //method access single species obj.
	console.log("The contents of array are: \n",speciesArray[2]); //method access single species obj.
	console.log("The value of speciesCount is: ",(speciesCount+1));

	return speciesArray;
}