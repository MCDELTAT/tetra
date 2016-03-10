/*
var camera;
var scene;
var renderer;
var mesh;
  
init();
animate();
  
function init() {
  
    scene = new THREE.Scene();
    camera = new THREE.PerspectiveCamera(75, window.innerWidth/window.innerHeight, 0.1, 1000);


    var light = new THREE.DirectionalLight( 0xffffff );
    light.position.set( 0, 1, 1 ).normalize();
    scene.add(light);
  
    var geometry = new THREE.BoxGeometry( 50, 50, 50);
    var material = new THREE.MeshBasicMaterial( {
     map: THREE.ImageUtils.loadTexture('textures/pps.png'),
     side: THREE.Doubleside,
     transparent: false, 
     opacity: 1000
 	});

    var faceCount = geometry.faces.length;
	for (var i=0; i<(faceCount); i++){
		if (i==2||i==3||i==6||i==7||i==10||i==11){
			geometry.faces[i].materialIndex = 0;
		} else{
			geometry.faces[i].materialIndex = 1;
		}
	}

    mesh = new THREE.Mesh(geometry, material );
    mesh.position.z = -50;
    scene.add( mesh );

    camera.position.z = 48.5;
	camera.position.x = 48.5;
	camera.rotation.y = 0.529269912; //PI/8

 
    renderer = new THREE.WebGLRenderer();
    renderer.setSize( window.innerWidth, window.innerHeight );
    document.body.appendChild( renderer.domElement );
  
    window.addEventListener( 'resize', onWindowResize, false );
  
    render();
}
  
function animate() {
    mesh.rotation.x += .04;
    mesh.rotation.y += .02;
  
    render();

    // stop animation by commenting this out
    requestAnimationFrame( animate );
}
  
function render() {
    renderer.render( scene, camera );
}
  
function onWindowResize() {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize( window.innerWidth, window.innerHeight );
    render();
}

*/

//scene and camera
var scene = new THREE.Scene(); 
var camera = new THREE.PerspectiveCamera(75, window.innerWidth/window.innerHeight, 0.1, 1000);

//set up the renderer
var renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

//set up the cube, apply face visibility to half of the faces
var geometry = new THREE.CubeGeometry(50,50,50);
materials = [
	new THREE.MeshBasicMaterial({color: 0xeef5e1, side: THREE.DoubleSide}),
	new THREE.MeshBasicMaterial({transparent: true, opacity: 0})
];


var material = new THREE.MeshBasicMaterial( {
map: THREE.ImageUtils.loadTexture('textures/pps.png'),
//color: 0xeef5e1,
//side: THREE.Doubleside,
transparent: true, 
opacity: 1
});


var materials = [
       new THREE.MeshBasicMaterial({ //right
           map: THREE.ImageUtils.loadTexture('textures/pps.png'),
           transparent: true, 
           side: THREE.DoubleSide,
		   opacity: 0 //appears when one, transparent when invisible
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









/*

//

//graph consists of faces 2,3,6,7,10,11
var faceCount = geometry.faces.length;
for (var i=0; i<(faceCount); i++){
	if (i==2||i==3||i==6||i==7||i==10||i==11){
		geometry.faces[i].materialIndex = 0;
	} else{
		geometry.faces[i].materialIndex = 1;
	}
}

var cube = new THREE.Mesh(geometry, material);
scene.add(cube);
*/


camera.position.z = 48.5;
camera.position.x = 48.5;
camera.rotation.y = 0.529269912; //PI/8



//function to generate spheres at coordinates
//three.js Objects all have an incremental id
//in this case, the spheres are id:4 onward (scene=1,cam=2,grid=3)
var sphGeometry = new THREE.SphereGeometry(0.25,32,32); 
var speciesMat1 = new THREE.MeshBasicMaterial({color: 0x84dbfc});
var pointsStartIndex = 4;
var tempObject;
function createSphere(xCoor,yCoor,zCoor){
	scene.add(new THREE.Mesh(sphGeometry,speciesMat1));
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

//actual: get length of parsed species object, loop n.
var species1 = {
	contig1: {
		xCoor: -152.71487,
		yCoor: 198.21319,
		zCoor: 25.004952
	},
	contig2: {
		xCoor: -232.2761,
		yCoor: 147.22785,
		zCoor: -4.7544378
	},
	contig3: {
		xCoor: -203.38871,
		yCoor: 98.04291,
		zCoor: 26.501083
	},
	contig4: {
		xCoor: -188.01657,
		yCoor: 130.2182,
		zCoor: 46.68083
	},
	contig5: {
		xCoor: -182.42861,
		yCoor: 160.0382,
		zCoor: 35.121653
	}
};

createSphere(species1.contig1.xCoor,species1.contig1.yCoor,species1.contig1.zCoor);
createSphere(species1.contig2.xCoor,species1.contig2.yCoor,species1.contig2.zCoor);
createSphere(species1.contig3.xCoor,species1.contig3.yCoor,species1.contig3.zCoor);
createSphere(species1.contig4.xCoor,species1.contig4.yCoor,species1.contig4.zCoor);
createSphere(species1.contig5.xCoor,species1.contig5.yCoor,species1.contig5.zCoor);

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
}
render();