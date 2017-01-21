//scene and camera
var group, controls, container;
var scene = new THREE.Scene(); 
var camera = new THREE.PerspectiveCamera(60, window.innerWidth/window.innerHeight, 1, 1000);

//set up the renderer
var renderer = new THREE.WebGLRenderer( { antialias: false } );
renderer.setSize(window.innerWidth, window.innerHeight);
container = document.getElementById( 'container' );
container.appendChild( renderer.domElement );

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

//add orbit controls to the scene.
controls = new THREE.OrbitControls( camera );
controls.addEventListener( 'change', render );

camera.position.z = 110.5;
camera.position.x = 110.5;
camera.rotation.y = 0.529269912; //PI/8

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
  controls.maxDistance *=x*2;
  //camera.position.z =  z/7;
  camera.position.x *=x  ;
}

function onWindowResize() {
  camera.aspect = window.innerWidth / window.innerHeight;
  camera.updateProjectionMatrix();

  renderer.setSize( window.innerWidth, window.innerHeight );

  render();
  controls.reset();
}

function resetCamera(){
  controls.reset();
  camera.position.z = 110.5;
  camera.position.x = 110.5;
  camera.rotation.y = 0.529269912; //PI/8
}

//function to generate spheres at coordinates
//three.js Objects all have an incremental id
//in this case, the spheres are id:4 onward (scene=1,cam=2,grid=3)
var sphGeometry = new THREE.SphereGeometry(0.25,32,32); 
var speciesMat1 = new THREE.MeshBasicMaterial({color: 0x84dbfc});
var speciesMat2 = new THREE.MeshBasicMaterial({color: 0xfa515f});
var speciesMat3 = new THREE.MeshBasicMaterial({color: 0x6bf35c});
var speciesMaterials = [speciesMat1,speciesMat2,speciesMat3];
var pointsStartIndex = 8;
var tempObject;
function createSphere(species,xCoor,yCoor,zCoor){
	scene.add(new THREE.Mesh(sphGeometry,speciesMaterials[species]));
	tempObject = scene.getObjectById( pointsStartIndex );
	tempObject.position.set(xCoor/10,yCoor/10,zCoor/10);
	pointsStartIndex++; //increment the var so next point can be id'ed correctly
}

//function to generate the id numbers of the species in the graph (so they can be toggled on/off)
//No input parameters, no returns. It adds a start and stop range to to visibilityRange[].
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

//draw the graph points. 
//Parameters: Species (int) will determine color, length (int) = # of points 
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

//Add some buttons to the DOM to toggle on off the species.
var hiddenList = document.getElementById("spList");
//create a button to toggle on and off species 1
var species1Btn = document.createElement("BUTTON");
var t1 = document.createTextNode("Aqui");
species1Btn.appendChild(t1);
hiddenList.appendChild(species1Btn);
var sp1btn = hiddenList.childNodes[1];
sp1btn.id = "btn1";
//add line breaks to space buttons out
hiddenList.appendChild(document.createElement("br"));
hiddenList.appendChild(document.createElement("br"));

//add the event listener
species1Btn.addEventListener("click", function(){changeVisible(0);});

//create a button to toggle on and off species 2
var species2Btn = document.createElement("BUTTON");
var t2 = document.createTextNode("Cren");
species2Btn.appendChild(t2);
hiddenList.appendChild(species2Btn);
var sp2btn = hiddenList.childNodes[4];
sp2btn.id = "btn2";
hiddenList.appendChild(document.createElement("br"));
hiddenList.appendChild(document.createElement("br"));
//add the event listener

species2Btn.addEventListener("click", function(){changeVisible(1);});

//create a button to toggle on and off species 2
var species3Btn = document.createElement("BUTTON");
var t3 = document.createTextNode("Unknown");
species3Btn.appendChild(t3);
hiddenList.appendChild(species3Btn);
var sp3btn = hiddenList.childNodes[7];
sp3btn.id = "btn3";
//add the event listener
species3Btn.addEventListener("click", function(){changeVisible(2);});

//set the header button to reset camera function
var camReset = document.getElementById("cameraReset")
camReset.addEventListener("click", resetCamera);

//THREE JS internal function to request the delta changes to frame and render.
function render() {
	renderer.render( scene, camera );
	//camera.position.y += .001;
}

//THREE JS internal function to draw the frame changes to WebGL context.
function animate() {
        requestAnimationFrame(animate);
        controls.update();       
}

render();
animate();

