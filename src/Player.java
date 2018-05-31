import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Sprite implements KeyListener {

    private boolean moveLeft, moveRight, moveUp, moveDown;

    private int direction;
    //0 is left
    //1 is right
    //2 is up
    //3 is down

    public Player(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void update(Graphics2D g2) {

        if (moveUp) {
            setImg(Images.player_back);
            move(2);
        }
        if (moveDown) {
            setImg(Images.player_front);
            move(3);
        }
        if (moveLeft) {

            setImg(Images.player_left);
            move(0);
        } if (moveRight) {
            setImg(Images.player_right);
            move(1);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_A) {
            moveLeft = true;
        } else if (keyCode == KeyEvent.VK_D) {
            moveRight = true;
        } else if (keyCode == KeyEvent.VK_W) {
            moveUp = true;
        } else if (keyCode == KeyEvent.VK_S) {
            moveDown = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_A) {

            moveLeft = false;

        } else if (keyCode == KeyEvent.VK_D) {

            moveRight = false;

        } else if (keyCode == KeyEvent.VK_W) {

            moveUp = false;

        } else if (keyCode == KeyEvent.VK_S) {

            moveDown = false;

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

}