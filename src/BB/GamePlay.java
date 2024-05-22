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
    private int totalBricks =21;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX =120;
    private int ballposY =350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    public GamePlay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(this.delay,this);
        timer.start();
    }
    public void paint(Graphics graphics){
        //the border of the game
        graphics.setColor(Color.white);
        graphics.fillRect(1,1,692,592);

        graphics.setColor(Color.blue);
        graphics.fillRect(0,0,3,592);
        graphics.fillRect(0,0,692,3);
        graphics.fillRect(691,0,3,592);
        //the paddle
        graphics.setColor(Color.orange);
        graphics.fillRect(playerX,550,100,8);
        //the ball
        graphics.setColor(Color.BLACK);
        graphics.fillOval(ballposX,ballposY,20,20);

        graphics.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            //Ball - pedal interaction
            if(new Rectangle(ballposX,ballposY,20,30).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir = -ballYdir;
            }
            ballposX+=ballXdir;
            ballposY+=ballYdir;

            if(ballposX<0){
                ballXdir = -ballXdir;
            }

            if(ballposY<0){
                ballYdir = -ballYdir;
            }
            if(ballposX>670){
                ballXdir = -ballXdir;
            }

        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX>=600){
                playerX =600;
            }else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX<10){
                playerX =10;
            }else {
                moveLeft();
            }
        }

    }

    private void moveLeft() {
        play = true;
        playerX-=40;
    }

    private void moveRight() {
        play = true;
        playerX+=40;

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
