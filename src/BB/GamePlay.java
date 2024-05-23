package BB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private final Timer timer;
    private final int delay = 5;
    private int playerX = 310;
    private int ballpointsX = 120;
    private int ballpointsY = 350;
    private int ballXDir = -5;
    private int ballYDir = -11;
    private MapGenerator mapGenerator;

    public GamePlay() {
        mapGenerator = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(this.delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {
        //the border of the game
        graphics.setColor(Color.white);
        graphics.fillRect(1, 1, 692, 592);

        //draw the bricks
        mapGenerator.draw((Graphics2D) graphics);

        graphics.setColor(Color.blue);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(691, 0, 3, 592);
        //the paddle
        graphics.setColor(Color.orange);
        graphics.fillRect(playerX, 550, 100, 8);
        //the ball
        graphics.setColor(Color.BLACK);
        graphics.fillOval(ballpointsX, ballpointsY, 20, 20);
        //the score bord
        graphics.setColor(Color.black);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString(STR."\{score}", 590, 30);

        if (totalBricks <= 0) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            //Game over message
            graphics.setColor(Color.red);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString(STR."You Won, Score: \{score}", 190, 300);
            ////new game message
            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter to Restart", 230, 350);
        }

        if (ballpointsY > 570) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            //Game over message
            graphics.setColor(Color.red);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString(STR."Game over, Score: \{score}", 190, 300);
            ////new game message
            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter to Restart", 230, 350);


        }

        graphics.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            //Ball - pedal interaction
            if (new Rectangle(ballpointsX, ballpointsY, 20, 30).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYDir = -ballYDir;
            }

            for (int i = 0; i < mapGenerator.map.length; i++) {
                for (int j = 0; j < mapGenerator.map[0].length; j++) {
                    if (mapGenerator.map[i][j] > 0) {
                        int brickX = j * mapGenerator.brickWidth + 80;
                        int brickY = i * mapGenerator.brickHeight + 50;
                        int brickWidth = mapGenerator.brickWidth;
                        int brickHeight = mapGenerator.brickHeight;

                        Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballpointsX, ballpointsY, 20, 20);

                        if (ballRect.intersects(rectangle)) {
                            mapGenerator.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballpointsX + 19 <= rectangle.x || ballpointsX + 1 >= rectangle.x + rectangle.width) {
                                ballXDir = -ballXDir;
                            } else {
                                ballYDir = -ballYDir;
                            }
                        }


                    }
                }
            }

            ballpointsX += ballXDir;
            ballpointsY += ballYDir;

            if (ballpointsX < 0) {
                ballXDir = -ballXDir;
            }

            if (ballpointsY < 0) {
                ballYDir = -ballYDir;
            }
            if (ballpointsX > 670) {
                ballXDir = -ballXDir;
            }

        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballXDir = -5;
                ballYDir = -11;
                ballpointsX = 120;
                ballpointsY =350;
                score = 0;
                totalBricks = 21;
                mapGenerator = new MapGenerator(3, 7);
                repaint();


            }
        }

    }

    private void moveLeft() {
        play = true;
        playerX -= 40;
    }

    private void moveRight() {
        play = true;
        playerX += 40;

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
