//scene and camera
var cameraControls;
var group;
var scene = new THREE.Scene(); 
var camera = new THREE.PerspectiveCamera(75, window.innerWidth/window.innerHeight, 0.1, 1000);
var cameraControls;
var clock = new THREE.Clock();

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
cameraControls.minDistance = 10.5;
cameraControls.maxDistance = 135.0;
camera.position.z = 110.5;
camera.position.x = 110.5;
camera.rotation.y = 0.529269912; //PI/8
cameraControls.noPan = true;

cameraControls.staticMoving = false;

function scale(x, y ,z){
  if(x >= y && x >= z){
  	graph.scale.x += x;
  	graph.scale.y += x;
  	graph.scale.z += x;
  }
  else if(y >= x && y >= z){
  	graph.scale.x += y;
  	graph.scale.y += y;
  	graph.scale.z += y;
  }
  else if(z >= y && z >= x){
  	graph.scale.x += z;
  	graph.scale.y += z;
  	graph.scale.z += z;
  }
  //graph.scale.x += x;
  //graph.scale.y += y;
  //graph.scale.z += z;
  console.log("x=:",x);
  console.log("y=:",y);
  console.log("z=:",z);
  //cameraControls.minDistance = 110.5;
  cameraControls.maxDistance *=x*2;
  //camera.position.z =  z/7;
  camera.position.x *=x  ;
}
console.log(camera.quaternion);

//--> none of the camera reset code below works. If you console.log(camera) you
//can see all properties at start. try resetting from that.
//determine the default camera position so we can reset to it.
var cameraDefaults = {
	"aspect": 1.6801242236024845,
	"zoom": 1,
	"position": 0,
	"rotation": 0,
	"xPos": 48.5,
	"yPos": 0,
	"zPos": 48.5,
	"xRot": 0,
	"yRot": 0.7853981462831774,
	"zRot": 0,
	"xQuat": 0, //fill in the XQuats
	"yQuat": camera.quaternion._y,
	"zQuat": 0,
	"wQuat": camera.quaternion._w
};

function setCameraDefaults (){
	cameraDefaults.position = camera.position;
	cameraDefaults.rotation = camera.rotation;
}


function resetCamera(){

console.log("inside reset camera ",cameraDefaults.position);
    camera.aspect = 1.68;
    camera.position.z = 110.5;
    camera.position.x = 110.5;
    camera.position.y = 0;
    camera.rotation.x = 0;
    camera.rotation.y = 0; //PI/8
    camera.rotation.z = 0;
    //camera.position = 0;
    //camera.rotation = 0;
    camera.quaternion._x = 0;
    camera.quaternion._y = 0;
    camera.quaternion._z = 0;
    //camera.position = cameraDefaults.position;
    //camera.rotation = cameraDefaults.rotation;
    /*camera.quaternion._x = cameraDefaults.xQuat;
    camera.quaternion._y = cameraDefaults.yQuat;
    camera.quaternion._z = cameraDefaults.zQuat;
    camera.quaternion._w = cameraDefaults.wQuat;*/
}
var mytext = "Hello!";
var text3d = new THREE.TextGeometry(mytext, {
    font: "hevletiker"
});
text3d.text = mytext; // storing this for later use...
text3d.computeBoundingBox();
var textMaterial = new THREE.MeshBasicMaterial({ color: 0xb0bca7, overdraw: true });
meshArray.push(new THREE.Mesh( text3d, textMaterial ));
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
		//note that this causes a one-off error. For each group after 1, there will be 1*(n-1) points not included in the graph
		visibilityRange.push(((visibilityRange[(visibilityRange.length)-1])+speciesLengths[i-1])) //add the length of species to the last entry in the array
	}
}

//function to change the visibility of the points
//speciesNumber is an integer that selects which data points from visibilityRange[] to use.
function changeVisible(speciesNumber){
	console.log("The visibility has been changed.")
	console.log("The visibility ranges are: ",visibilityRange);
	var startRange = visibilityRange[speciesNumber];
	var stopRange = visibilityRange[speciesNumber+1];
	var visibilityState = scene.getObjectById(startRange,true).visible;
	//debug-->actual needed values: blue 4 to 202, red 203 to 402, green 403 to 932
	console.log("The start of blue is "+startRange+" the stop range is: "+(stopRange-speciesNumber));
	if (visibilityState==true){
			//compensate for the above one-off error in the loop stop condition
			for (var i=startRange; i<=(stopRange-1); i++){
				scene.getObjectById(startRange,true).visible = false;
				startRange++;
			}
	} else if (visibilityState==false){
		for (var i=startRange; i<=(stopRange-1); i++){
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

var hiddenList = document.getElementById("spList");
//create a button to toggle on and off species 1
var species1Btn = document.createElement("BUTTON");
var t1 = document.createTextNode("Species 1 Visibility");
species1Btn.appendChild(t1);
hiddenList.appendChild(species1Btn);
//add line breaks to space buttons out
hiddenList.appendChild(document.createElement("br"));
hiddenList.appendChild(document.createElement("br"));

//add the event listener
species1Btn.addEventListener("click", function(){changeVisible(0);});

//create a button to toggle on and off species 2
var species2Btn = document.createElement("BUTTON");
var t2 = document.createTextNode("Species 2 Visibility");
species2Btn.appendChild(t2);
hiddenList.appendChild(species2Btn);
hiddenList.appendChild(document.createElement("br"));
hiddenList.appendChild(document.createElement("br"));
//add the event listener

species2Btn.addEventListener("click", function(){changeVisible(1);});

//create a button to toggle on and off species 2
var species3Btn = document.createElement("BUTTON");
var t3 = document.createTextNode("Species 3 Visibility");
species3Btn.appendChild(t3);
hiddenList.appendChild(species3Btn);
//add the event listener
species3Btn.addEventListener("click", function(){changeVisible(2);});

//set the header button to reset camera function
var camReset = document.getElementById("cameraReset")
camReset.addEventListener("click", resetCamera);

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

