// Flying Vegetable Game
// by: Jieun Lee

var gameCanvas = document.getElementById("gameCanvas");
var scoreCanvas = document.getElementById("scoreCanvas");
var gCtx = gameCanvas.getContext("2d");
var sCtx = scoreCanvas.getContext("2d");

var screenWidth = gameCanvas.width;
var screenHeight = gameCanvas.height;
var scoreBoardHeight = scoreCanvas.height;

document.addEventListener("keydown", keyDownHandler, false);

var score = 0;
var highScore = 0;
var isGameOver = false;

///////////////////////////////////////////////////////////////////////////////////

// VEGETABLE
// represents the vegetable

var vegetable = {
	x: 150,
	y: screenHeight/2,
	width: 110,
	height: 35,
	color: "#BCF246",
	speed: 10,
	dir: 1,	// 1 is down, -1 is up

	draw: function() {
		gCtx.beginPath();
		gCtx.rect(this.x, this.y, this.width, this.height);
		gCtx.fillStyle = this.color;
		gCtx.fill();
		gCtx.closePath();
	},

	checkWithinBounds: function() {
		if (this.y <= 0 || this.y+this.height >= screenHeight) {
			isGameOver = true;
		}
	},

	move: function() {
		this.y += this.dir*this.speed;
	},

	setDirection: function(dir) {
		this.dir = dir;
	}
};

var initializeVegetable = function() {
	vegetable.y = screenHeight/2;
	vegetable.dir = 1;
	vegetable.speed = 10;
};

///////////////////////////////////////////////////////////////////////////////////

// OBSTACLE
// represents an obstacle

var obstacles;
var numObstacles = 5;
var frontObstacle = 0;	// farthest left of visible obstacles
var lastObstacle = 0;	// farthest right of visible obstacles
var rangeObstacle = 0;	// obstacle within range of colliding

var obsWidth = 70;
var obsHeight = 230;
var obsColor = "#D7D4D4";
var obsSpeed = 7;

var obstacleY = [0, -30, -60, -90, -120, 250, 280, 310, 340, 370];

var generateY = function() {
	return obstacleY[Math.floor((Math.random()*10))];
};

function Obstacle() {
	this.x = screenWidth;
	this.y = generateY();
	this.isMoving = false;
	this.isInRange = false;

	this.draw = function() {
		gCtx.beginPath();
		gCtx.rect(this.x, this.y, obsWidth, obsHeight);
		gCtx.fillStyle = obsColor;
		gCtx.fill();
		gCtx.closePath();
	};

	this.move = function() {
		this.x -= obsSpeed;
	};

	this.checkRangeStatus = function() {
		if (!this.isInRange) {
			if (vegetable.x + vegetable.width > this.x) {
				this.isInRange = true;
			}
		}
		if (this.isInRange) {
			// if it is still in range, check for collisions
			if (vegetable.x < this.x + obsWidth) {
				if (this.y > 0) {
					// obstacle at bottom
					if (vegetable.y + vegetable.height > this.y) {
						isGameOver = true;
					}
				}
				else {
					// obstacle at top
					if (vegetable.y < this.y + obsHeight) {
						isGameOver = true;
					}
				}
			}
			// if it is not in range anymore, change the range status
			// also update score and change range obstacle
			else {
				this.isInRange = false;
				score++;
				rangeObstacle++;
				rangeObstacle %= 5;
				// if score is a multiple of 10, speed up obstacle
				if (score % 10 == 0) {
					obsSpeed++;
					vegetable.speed++;
				}
			}
		}
	};

	this.resetObstacle = function() {
		this.x = screenWidth;
		this.y = generateY();
		this.isMoving = true;
	};
}

var makeObstacles = function() {
	obstacles = [];	// make a new array for obstacles
	obsSpeed = 7;
	frontObstacle = 0;
	lastObstacle = 0;
	rangeObstacle = 0;
	for(i=0; i<numObstacles; i++) {
		obstacles.push(new Obstacle());
	}
	obstacles[0].isMoving = true;
};

var deactivateFrontObstacle = function() {
	if ((obstacles[frontObstacle].x + obsWidth) < 0) {
		obstacles[frontObstacle].isMoving = false;
		frontObstacle++;
		frontObstacle %= 5;
	}
};

var activateNextObstacle = function() {
	if (obstacles[lastObstacle].x < (screenWidth - obsWidth*3.5)) {
		lastObstacle++;
		lastObstacle %= 5;
		obstacles[lastObstacle].resetObstacle();
	}
};

var checkForCollisions = function() {
	obstacles[rangeObstacle].checkRangeStatus();
};

var moveObstacles = function() {
	for(i=0; i<numObstacles; i++) {
		if (obstacles[i].isMoving) {
			obstacles[i].x -= obsSpeed;
		}
	}
};

var drawObstacles = function() {
	for(i=0; i<numObstacles; i++) {
		if (obstacles[i].isMoving) {
			obstacles[i].draw();
		}
	}
};

///////////////////////////////////////////////////////////////////////////////////

// SCORE
// organizes the scoreboard

var scoreX = (screenWidth/5);
var highScoreX = (screenWidth/5)*3;
var alignY = scoreBoardHeight/2 + 5;

function drawScores() {
	sCtx.font = "bold 16px verdana";
	sCtx.fillStyle = "#000000";
	sCtx.fillText("Score: " + score, scoreX, alignY);
	sCtx.fillText("High Score: " + highScore, highScoreX, alignY);
}

///////////////////////////////////////////////////////////////////////////////////

// GAME SETUP
// organization of the entire game

function gameSetup() {
	initializeVegetable();
	makeObstacles();
}

function update() {
	gCtx.clearRect(0, 0, screenWidth, screenHeight);
	sCtx.clearRect(0, 0, screenWidth, scoreBoardHeight);

	// update vegetable
	vegetable.move();
	vegetable.draw();
	vegetable.checkWithinBounds();

	// update obstacles
	deactivateFrontObstacle();
	activateNextObstacle();
	checkForCollisions();
	moveObstacles();
	drawObstacles();

	// update scores
	drawScores();
}

function gameOverState() {
	// TODO
	// for now, press space to restart
}

function restartGame() {
	if (score > highScore) {
		highScore = score;
	}
	score = 0;
	gameSetup();
	isGameOver = false;
}

///////////////////////////////////////////////////////////////////////////////////

// GAME PLAY
// components that are directly run

function keyDownHandler(e) {
	if (e.keyCode == 38) {
		// 38 = up arrow key
		vegetable.setDirection(-1);
	}
	else if (e.keyCode == 40) {
		// 40 = down arrow key
		vegetable.setDirection(1);
	}
	else if (e.keyCode == 32) {
		// 32 = spacebar
		if (isGameOver) {
			restartGame();
		}
	}
}

function playGame() {
	if (!isGameOver) {
		update();
	}
	else {
		gameOverState();
	}
}

gameSetup();
setInterval(playGame, 30);