import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Sprite implements KeyListener {
    private boolean moveLeft, moveRight, moveUp, moveDown;

    public Player(int x, int y) {
        super(x, y);
    }

    public void update(Graphics2D g2) {
        if (moveLeft) {
            setImg(Images.player_left);
            move(0);
        } else if (moveRight) {
            setImg(Images.player_right);
            move(1);
        } else if (moveUp) {
            setImg(Images.player_back);
            move(2);
        } else if (moveDown) {
            setImg(Images.player_front);
            move(3);
        }

        draw(g2);
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
}