//scene and camera
var scene = new THREE.Scene(); 
var camera = new THREE.PerspectiveCamera(75, window.innerWidth/window.innerHeight, 0.1, 1000);

//set up the renderer
var renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

//set up the cube, apply face visibility to half of the faces
var geometry = new THREE.CubeGeometry(50,50,50);

//color: 0xeef5e1,

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
		   opacity: 0
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
		   opacity: 0
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

//function to change the visibility of the points
//start and stop are created upon file parse (where the species changes)
function changeVisible(startRange, stopRange){
	console.log("The visibility has been changed.")
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

function getSpeciesLength(speciesID){
	var length = 0;
	var length = Object.keys(speciesArray[speciesID]).length;

	console.log("The length of the selected species is: ",length);
	return length;
}

function drawPoints (species, length){
	//get the first parameter name of each speciesObject
	var startIndex = Object.keys(speciesArray[species])[0]
	console.log("The start index of "+species+" is: ",startIndex);
	console.log("The range of drawing is from "+startIndex+" to "+(+startIndex+ +length));
	for (var i=startIndex; i<(+startIndex + +length); i++){
		createSphere(species,speciesArray[species][i].dim1,speciesArray[species][i].dim2,speciesArray[species][i].dim3);
	}
}

//create a button to toggle on and off the species
var species1Btn = document.createElement("BUTTON");
var t1 = document.createTextNode("CLICK ME: SP1");
species1Btn.appendChild(t1);
document.body.appendChild(species1Btn);
//add the event listener
species1Btn.addEventListener("click", function(){changeVisible(4,8); });

function render() {
	requestAnimationFrame(render);
	renderer.render(scene,camera);
	//camera.position.y += .001;
}
render();
