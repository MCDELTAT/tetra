//scene and camera
var cameraControls;
var group;
var scene = new THREE.Scene(); 
var camera = new THREE.PerspectiveCamera(75, window.innerWidth/window.innerHeight, 0.1, 1000);
var cameraControls;
var clock = new THREE.Clock();

camera.position.z = 75;

//set up the renderer
var renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

//set up the cube, apply face visibility to half of the faces
var geometry = new THREE.CubeGeometry(50,50,50);

//the grids for each of the cube faces. Three have opacity=1 to hide them.
var materials = [
       new THREE.MeshBasicMaterial({ //right
           map: THREE.ImageUtils.loadTexture('textures/pps.png'),
           transparent: true, 
           side: THREE.DoubleSide,
		   opacity: 0
       }),
       new THREE.MeshBasicMaterial({
           map: THREE.ImageUtils.loadTexture('textures/pps.png'),
           transparent: false, 
           side: THREE.DoubleSide,

		   opacity: 1
       }),
       new THREE.MeshBasicMaterial({
           map: THREE.ImageUtils.loadTexture('textures/pps.png'),
           transparent: true, 
           side: THREE.DoubleSide,
		   opacity: 0
       }),
       new THREE.MeshBasicMaterial({
           map: THREE.ImageUtils.loadTexture('textures/pps.png'),
           transparent: false, 
           side: THREE.DoubleSide,

		   opacity: 1
       }),
       new THREE.MeshBasicMaterial({ //left
           //map: THREE.ImageUtils.loadTexture('textures/pps.png'),
           transparent: true,
           side: THREE.DoubleSide,
           opacity: 0
       }),
       new THREE.MeshBasicMaterial({
           map: THREE.ImageUtils.loadTexture('textures/pps.png'),
           transparent: false, 
           side: THREE.DoubleSide,
		   opacity: 1
       })
    ];

    graph = new THREE.Mesh(geometry, 
    	new THREE.MeshFaceMaterial(materials)) ;

    scene.add(graph);

//add trackball controls to the scene.
cameraControls = new THREE.TrackballControls(camera, renderer.domElement);
cameraControls.target.set(0, 0, 0);
camera.position.z = 48.5;
camera.position.x = 48.5;
camera.rotation.y = 0.529269912; //PI/8

//function to generate spheres at coordinates
//three.js Objects all have an incremental id
//in this case, the spheres are id:4 onward (scene=1,cam=2,grid=3)
var sphGeometry = new THREE.SphereGeometry(0.25,32,32); 
var speciesMat1 = new THREE.MeshBasicMaterial({color: 0x84dbfc});
var speciesMat2 = new THREE.MeshBasicMaterial({color: 0xfa515f});
var speciesMat3 = new THREE.MeshBasicMaterial({color: 0x6bf35c});
var speciesMaterials = [speciesMat1,speciesMat2,speciesMat3];
var pointsStartIndex = 4;
var tempObject;
function createSphere(species,xCoor,yCoor,zCoor){
	scene.add(new THREE.Mesh(sphGeometry,speciesMaterials[species]));
	tempObject = scene.getObjectById(pointsStartIndex,true);
	tempObject.position.set(xCoor/10,yCoor/10,zCoor/10);
	pointsStartIndex++; //increment the var so next point can be id'ed correctly
}

var visibilityRange = [];
visibilityRange.push(4); //push starting index value into the array
function getVisibilityRange(){
	for (var i=1; i<=speciesArray.length; i++){
		//console.log("Adding previous value and species length is: ",speciesLengths[i-1])
		visibilityRange.push(((visibilityRange[(visibilityRange.length)-1])+speciesLengths[i-1])-1) //add the length of species to the last entry in the array
	}
}

//function to change the visibility of the points
//speciesNumber is an integer that selects which data points from visibilityRange[] to use.
function changeVisible(speciesNumber){
	console.log("The visibility has been changed.")
	getVisibilityRange(); //generate the ranges for the visibility function.
	//console.log("The visibility ranges are: ",visibilityRange);
	var startRange = visibilityRange[speciesNumber];
	var stopRange = visibilityRange[speciesNumber+1];
	var visibilityState = scene.getObjectById(startRange,true).visible;
	if (visibilityState==true){
			for (var i=startRange; i<=stopRange; i++){
				scene.getObjectById(startRange,true).visible = false;
				startRange++;
			}
	} else if (visibilityState==false){
		for (var i=startRange; i<=stopRange; i++){
			scene.getObjectById(startRange,true).visible = true;
			startRange++;
		}
	}
}

var speciesLengths = [];
//Get the number of graph points in a species from SpeciesArray
function getSpeciesLength(){
	for (var i=0; i<speciesArray.length; i++){
		var length = 0;
		var length = Object.keys(speciesArray[i]).length;
		speciesLengths.push(length);
		console.log("The length of the selected species is: ",length);
	}
	console.log("The lengths of the species are: ",speciesLengths);
}

//draw the graph points. Species will determine color, length= # of points 
function drawPoints (species, length){
	//get the first parameter name of each speciesObject
	var startIndex = Object.keys(speciesArray[species])[0];
	console.log("The start index of "+species+" is: ",startIndex);
	console.log("Adding startIndex and length is: ",(+startIndex+ length));
	console.log("The range of drawing is from "+startIndex+" to "+(+startIndex+ +length));
	for (var i=startIndex; i<(+startIndex + +length); i++){
		createSphere(species,speciesArray[species][i].dim1,speciesArray[species][i].dim2,speciesArray[species][i].dim3);
	}
}

//create a button to toggle on and off species 1
var species1Btn = document.createElement("BUTTON");
var t1 = document.createTextNode("CLICK ME: SP1");
species1Btn.appendChild(t1);
document.body.appendChild(species1Btn);
//add the event listener
species1Btn.addEventListener("click", function(){changeVisible(0);});

//create a button to toggle on and off species 2
var species2Btn = document.createElement("BUTTON");
var t2 = document.createTextNode("CLICK ME: SP2");
species2Btn.appendChild(t2);
document.body.appendChild(species2Btn);
//add the event listener
species2Btn.addEventListener("click", function(){changeVisible(1);});

//create a button to toggle on and off species 2
var species3Btn = document.createElement("BUTTON");
var t3 = document.createTextNode("CLICK ME: SP3");
species3Btn.appendChild(t3);
document.body.appendChild(species3Btn);
//add the event listener
species3Btn.addEventListener("click", function(){changeVisible(2);});

function render() {
	requestAnimationFrame(render);
	renderer.render(scene,camera);
	//camera.position.y += .001;
}

function animate() {
    
        var delta = clock.getDelta();

        requestAnimationFrame(animate);
        
        cameraControls.update(delta);
        
        renderer.render(scene, camera);
        
}

render();
animate();

