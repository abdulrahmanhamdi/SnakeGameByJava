package SnakeGame;

import java.awt.event.KeyEvent;

public class Controller {
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
            leftDirection = true;
            upDirection = false;
            downDirection = false;
        }
        if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
            rightDirection = true;
            upDirection = false;
            downDirection = false;
        }
        if ((key == KeyEvent.VK_UP) && (!downDirection)) {
            upDirection = true;
            rightDirection = false;
            leftDirection = false;
        }
        if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
            downDirection = true;
            rightDirection = false;
            leftDirection = false;
        }
    }

    public boolean isLeftDirection() {
        return leftDirection;
    }

    public boolean isRightDirection() {
        return rightDirection;
    }

    public boolean isUpDirection() {
        return upDirection;
    }

    public boolean isDownDirection() {
        return downDirection;
    }

    public void setInitialState(){
        leftDirection = false;
        rightDirection = true;
        upDirection = false;
        downDirection = false;
    }
}