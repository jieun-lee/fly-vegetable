package ui;

import model.FVGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * FlyVegetable Game
 * by Regina Lee
 *
 * Move the vegetable up and down using the arrow keys
 * Avoid all obstacles along the way
 * Also avoid hitting the top and bottom of the screen
 * Try to beat your high score!
 *
 */

/**
 * The main window in which the game is played
 */
public class FlyVegetable extends JFrame {


    private static final int INTERVAL = 20;
    private GamePanel gp;
    private ScorePanel sp;
    private FVGame game;
    private Timer t;

    // constructor
    private FlyVegetable() {
        super("Fly Vegetable");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game = new FVGame();
        gp = new GamePanel(game);
        sp = new ScorePanel(game);
        add(gp);
        add(sp, BorderLayout.SOUTH);

        pack();
        setVisible(true);

        addKeyListener(new KeyHandler());
        addTimer();
        t.start();
    }


    // handle key presses
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            game.keyPressed(e.getKeyCode());
        }
    }


    // activates the timer
    private void addTimer() {
        t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!game.isGameOver())
                    game.update();
                else {
                    game.setHighScore(game.getScore());
                    sp.updateHighScore();
                }
                gp.repaint();
                sp.update();
            }
        });
    }


    // run the Fly Vegetable Game
    public static void main(String[] args) {
        new FlyVegetable();
    }

}
