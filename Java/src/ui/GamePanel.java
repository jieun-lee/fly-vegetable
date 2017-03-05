package ui;

import model.FVGame;

import javax.swing.*;
import java.awt.*;

/**
 * The panel in which the game takes place
 */
public class GamePanel extends JPanel {


    public static final int WIDTH = 720;
    public static final int HEIGHT = 480;
    public static final Color BACKGROUND = new Color(255, 255, 255);
    private FVGame game;

    // constructor
    public GamePanel(FVGame fv) {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND);

        game = fv;
    }


    // directs to the FVGame to paint it
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.drawGame(g);

        if (game.isGameOver())
            gameOver(g);
    }


    // draws the game over state
    private void gameOver(Graphics g) {

        int finalScore = game.getScore();
        String scoreString = "Final Score: " + finalScore;

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", 0, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString("Game Over!", g, fm, HEIGHT/2 - 30);
        centreString(scoreString, g, fm, HEIGHT/2);
        centreString("Press R to Replay", g, fm, HEIGHT/2 + 30);
    }

    // draws the given string in the center of the screen
    private void centreString(String str, Graphics g, FontMetrics fm, int yPos) {
        int width = fm.stringWidth(str);
        g.drawString(str, (WIDTH - width)/2, yPos);
    }


}
