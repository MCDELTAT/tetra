//variable inside render, 0 is to right, 1 is to left. 
var movementDirection = 1;

if (cube.position.x <= -5){
		movementDirection = 0;
		console.log("The direction of movement is right")
	} else if (cube.position.x >= 5){
		movementDirection = 1;
	}

	//oscillate back and forth across screen
  if (movementDirection == 0){
  	cube.position.x += 0.05;
  } else if (movementDirection == 1){
  	cube.position.x -= 0.05;
  }*/