package model;

import ui.GamePanel;

import java.awt.*;
import java.util.Random;

/**
 * Represents an obstacle
 */
public class Obstacle {

    public static final int SPEED = 5;
    public static final Color COLOR = new Color(215, 212, 212);
    public static final int WIDTH = 70;
    public static final int HEIGHT = 230;
    public static final int[] HEIGHTS = {0, -30, -60, -90, -120, 250, 280, 310, 340, 370};

    private int pos_x;
    private int pos_y;
    private boolean trailing;
    private boolean scored;

    // makes a new obstacle with a random y-position out of the ones specified
    public Obstacle() {

        trailing = true;
        scored = false;

        pos_x = GamePanel.WIDTH;

        int selector = new Random().nextInt(HEIGHTS.length);
        pos_y = HEIGHTS[selector];
    }


    // draws the obstacle with the following properties
    // will change the shape later to make it more knife-like
    public void drawObstacle(Graphics g) {
        g.setColor(COLOR);
        g.fillRect(pos_x, pos_y, WIDTH, HEIGHT);
    }


    // moves the obstacle to the left
    public void moveObstacle() {
        pos_x = pos_x - SPEED;
    }


    // checks if the obstacle is past the point for the next obstacle to appear
    public boolean readyForNext() {
        return (pos_x <= GamePanel.WIDTH - WIDTH*4);
    }

    // returns if the obstacle is trailing or not (the last obstacle of the list)
    public boolean isTrailing() {
        return trailing;
    }

    // changes the state of the obstacle to not trailing
    public void changeTrailing() {
        trailing = false;
    }


    // checks if the obstacle is out of the screen
    public boolean outOfBounds() {
        return (pos_x <= -WIDTH);
    }


    // returns true if obstacle is within the range of the vegetable
    public boolean isWithinRange() {
        int front = pos_x;
        int back = pos_x + WIDTH;

        return (checkBound(front) || checkBound(back));
    }

    // returns true if given integer is within the range of the vegetable
    public boolean checkBound(int within) {
        int leftBound = Vegetable.POS_X;
        int rightBound = Vegetable.POS_X + Vegetable.WIDTH;

        return ((leftBound <= within) && (rightBound >= within));
    }


    // returns true if the obstacle has passed the vegetable
    public boolean pastVegetable() {
        return ((pos_x + WIDTH) < Vegetable.POS_X);
    }

    // returns true if the obstacle has contributed to a point in the score
    public boolean isScored() {
        return scored;
    }

    // sets the scored value to true
    public void setScored() {
        scored = true;
    }


    // returns the x-position of the obstacle
    public int getX() { return pos_x; }

    // returns the y-position of the obstacle
    public int getY() { return pos_y; }



}
