if ( ! Detector.webgl ) Detector.addGetWebGLMessage();
var container;
 /*******************************Need for camera controls************************************************/ 
var camera, cameraControls, scene, renderer, mesh;
var group;

var clock = new THREE.Clock();

init();
animate();
 /*******************************Need for camera controls************************************************/   

function init() {
    
        // renderer

        renderer = new THREE.WebGLRenderer({antialias: true, alpha: true});
        renderer.setSize(window.innerWidth, window.innerHeight);

        container = document.getElementById('container');
        container.appendChild(renderer.domElement);
       /*******************************Need for camera controls************************************************/ 		
        camera = new THREE.PerspectiveCamera( 75, window.innerWidth / window.innerHeight, 0.1, 10000 );
        camera.position.z = 75;

        cameraControls = new THREE.TrackballControls(camera, renderer.domElement);
        cameraControls.target.set(0, 0, 0);
 /*******************************Need for camera controls************************************************/ 
        scene = new THREE.Scene();

        // lights

        light = new THREE.DirectionalLight( 0xffffff );
        light.position.set( 1, 1, 1 );
        scene.add( light );

        light = new THREE.DirectionalLight( 0x002288 );
        light.position.set( -1, -1, -1 );
        scene.add( light );

        light = new THREE.AmbientLight( 0x222222 );
        scene.add( light );
        
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
species1Btn.style.position = "fixed"
species1Btn.style.top =  (screen.height/7)+'px'
species1Btn.style.left = (screen.width/2.75)+'px'
document.body.appendChild(species1Btn);

        group = new THREE.Object3D();
         
        //load mesh 
        var loader = new THREE.JSONLoader();
        loader.load('models/cube.js', modelLoadedCallback);

        window.addEventListener( 'resize', onWindowResize, false );

}

function modelLoadedCallback(geometry) {

        mesh = new THREE.Mesh( geometry, material );
        group.add(mesh);
        scene.add( group );

}

function onWindowResize() {

        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();

        renderer.setSize( window.innerWidth, window.innerHeight );

        render();

}
 /*******************************Need for camera controls************************************************/ 
function animate() {
    
        var delta = clock.getDelta();

        requestAnimationFrame(animate);
        
        cameraControls.update(delta);
        
        renderer.render(scene, camera);
        
}
 /*******************************Need for camera controls************************************************/ 