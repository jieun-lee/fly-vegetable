package model;

import ui.GamePanel;

import java.awt.*;

/**
 * Represents a vegetable
 */
public class Vegetable {


    public static final int POS_X = 150;
    public static final int SPEED = 10;
    public static final int WIDTH = 110;
    public static final int HEIGHT = 35;

    private String type;
    // type is either "Celery", "Eggplant", or "Carrot"

    private int pos_y;
    private int direction;
    // direction is 0 for up, 1 for down, 2 for undefined

    // makes a vegetable with given colour, width, and height
    public Vegetable(String type) {

        this.type = type;
        pos_y = (GamePanel.HEIGHT)/2;
        direction = 1;

    }


    // draws the corresponding vegetable
    public void drawVegetable(Graphics g) {
        if (type.equals("Celery"))
            drawCelery(g);
        if (type.equals("Eggplant"))
            drawEggplant(g);
        if(type.equals("Carrot"))
            drawCarrot(g);
    }

    // draw a celery
    private void drawCelery(Graphics g) {
        g.setColor(new Color(188, 242, 70));
        g.fillRect(POS_X, pos_y, WIDTH, HEIGHT);
    }

    // draw an eggplant
    private void drawEggplant(Graphics g) {
        g.setColor(new Color(210, 125, 255));
        g.fillOval(POS_X, pos_y, WIDTH, HEIGHT);
    }

    // draw a carrot
    private void drawCarrot (Graphics g) {
        g.setColor(new Color(255, 171, 66));
        int[] xpts = {POS_X, POS_X, POS_X+WIDTH};
        int[] ypts = {pos_y, pos_y+HEIGHT, (HEIGHT/2)+pos_y};
        Polygon poly = new Polygon(xpts, ypts, 3);
        g.fillPolygon(poly);
    }


    public Rectangle vegetableBounds() {
        return new Rectangle(POS_X, pos_y, WIDTH, HEIGHT);
    }

    public Polygon carrotBounds() {
        int[] xpts = {POS_X, POS_X, POS_X+WIDTH};
        int[] ypts = {pos_y, pos_y+HEIGHT, (HEIGHT/2)+pos_y};
        return new Polygon(xpts, ypts, 3);
    }





    // moves the vegetable up or down depending on the direction
    public void moveVegetable() {

        if (direction == 0 && pos_y >= 0)
            moveUp();

        if (direction == 1 && (pos_y <= GamePanel.HEIGHT - HEIGHT))
            moveDown();
    }

    // moves the vegetable up
    private void moveUp() {
        pos_y = pos_y - SPEED;
    }

    // moves the vegetable down
    private void moveDown() {
        pos_y = pos_y + SPEED;
    }


    // change the direction of the vegetable to up
    public void faceUp() {
        direction = 0;
    }

    // change the direction of the vegetable to down
    public void faceDown() {
        direction = 1;
    }


    // returns true if the vegetable is either on the top or bottom edge of the screen
    public boolean onEdge() {
        return ((pos_y <= 0) || (pos_y >= GamePanel.HEIGHT - HEIGHT));
    }


    // returns the y-position of the vegetable
    public int getY() {
        return pos_y;
    }

    public String getType() { return type; }


}
