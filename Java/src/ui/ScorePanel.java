package ui;

import model.FVGame;

import javax.swing.*;
import java.awt.*;

/**
 * The panel in which the score is recorded
 */
public class ScorePanel extends JPanel {

    public static final int WIDTH = GamePanel.WIDTH;
    public static final int HEIGHT = 55;
    public static final Color BACKGROUND = new Color(252, 255, 179);
    private static final int LBL_WIDTH = 250;
    private static final int LBL_HEIGHT = 50;
    private static final String HIGHSCORE_TEXT = "High Score: ";
    private static final String SCORE_TEXT = "Score: ";

    private FVGame game;
    private JLabel highScoreLabel;
    private JLabel scoreLabel;

    // the constructor
    public ScorePanel(FVGame fv) {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND);

        game = fv;

        highScoreLabel = new JLabel(HIGHSCORE_TEXT + game.getHighScore());
        highScoreLabel.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        highScoreLabel.setFont(new Font("Arial", 0, 20));
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        highScoreLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        scoreLabel = new JLabel(SCORE_TEXT + game.getScore());
        scoreLabel.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        scoreLabel.setFont(new Font("Arial", 0, 20));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        add(highScoreLabel);
        add(scoreLabel);
    }


    // updates the current score JLabel
    public void update() {
        scoreLabel.setText(SCORE_TEXT + game.getScore());
        repaint();
    }

    // updates the high score JLabel
    public void updateHighScore() {
        highScoreLabel.setText(HIGHSCORE_TEXT + game.getHighScore());
        repaint();
    }

}
