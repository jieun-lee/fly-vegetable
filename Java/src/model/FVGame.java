package model;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A FlyVegetable Game
 */
public class FVGame {

    private int score;
    private int highScore;
    private Vegetable vegetable;
    private List<Obstacle> obstacles;
    private boolean gameOver;


    // the constructor
    public FVGame() {
        highScore = 0;
        setUp();
    }


    private void setUp() {
        score = 0;
        gameOver = false;

        makeVegetable();

        obstacles = new ArrayList<Obstacle>();
        makeObstacle();
    }

    // make the vegetable specified by the string
    // the string is either "Celery", "Eggplant", or "Carrot"
    // when other vegetables are developed, this may have choices depending on a key press
    public void makeVegetable() {
        vegetable = new Vegetable("Celery");
    }

    // make an obstacle with a random y variable
    public void makeObstacle() {
        Obstacle obs = new Obstacle();
        obstacles.add(obs);
    }


    // handles key presses
    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_UP)
            vegetable.faceUp();
        if (keyCode == KeyEvent.VK_DOWN)
            vegetable.faceDown();
        if ((keyCode == KeyEvent.VK_R) && isGameOver())
            setUp();
    }

    // updates the FVGame
    public void update() {

        vegetable.moveVegetable();

        if (vegetable.onEdge())
            gameOver = true;

        updateObstacles();
    }

    // updates the obstacles on one update
    private void updateObstacles() {

        removeLeadingObstacle();

        boolean needNewObstacle = false;

        for (Obstacle o: obstacles) {
            o.moveObstacle();

            if (o.isTrailing() && o.readyForNext()) {
                o.changeTrailing();
                needNewObstacle = true;
            }

            if (o.isWithinRange())
                checkCollision(o);

            if (!o.isScored() && o.pastVegetable()) {
                score = score + 1;
                o.setScored();
            }
        }

        if (needNewObstacle)
            makeObstacle();
    }

    // removes the leading obstacle if it is out of bounds
    private void removeLeadingObstacle() {
        Obstacle leadingObstacle = obstacles.get(0);
        if (leadingObstacle.outOfBounds())
            obstacles.remove(leadingObstacle);
    }

    // checks if the obstacle and vegetable have collided
    private void checkCollision(Obstacle o) {
        Rectangle obstacleBoundingRect = new Rectangle(o.getX(), o.getY(), Obstacle.WIDTH, Obstacle.HEIGHT);

        String type = vegetable.getType();
        Rectangle vegetableBoundingRect = new Rectangle();
        Polygon carrotBoundingRect = new Polygon();

        if (type.equals("Carrot"))
            carrotBoundingRect = vegetable.carrotBounds();

        else vegetableBoundingRect = vegetable.vegetableBounds();

        if (type.equals("Carrot") && carrotBoundingRect.intersects(obstacleBoundingRect))
            gameOver = true;

        else if (vegetableBoundingRect.intersects(obstacleBoundingRect))
            gameOver = true;
    }


    // draws the game
    public void drawGame(Graphics g) {
        drawVegetable(g);
        drawObstacles(g);
    }

    // draws the vegetable
    private void drawVegetable(Graphics g) {
        vegetable.drawVegetable(g);
    }

    // draws all the obstacles
    private void drawObstacles(Graphics g) {
        for (Obstacle o: obstacles)
            o.drawObstacle(g);
    }


    // returns the score of the current game
    public int getScore() {
        return score;
    }

    // returns the high score of all games played
    public int getHighScore() { return highScore; }

    // sets the high score to the final score if it is higher than the previous high score
    public void setHighScore(int finalScore) {
        if (finalScore > highScore)
            highScore = finalScore;
    }


    // returns true if the game has ended
    public boolean isGameOver() {
        return gameOver;
    }
}
