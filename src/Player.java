import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Sprite implements KeyListener {

    //=====instance fields=====

    private boolean moveLeft, moveRight, moveUp, moveDown;

    //=====constructor=====

    public Player(int x, int y){

        super(x, y);

    }//end Player

    //=====methods=====

    public void update(Graphics2D g2){

        if(moveLeft){

            move(0);
        } else if(moveRight){

            move(1);

        } else if(moveUp){

            setImg(Images.player_back);

            move(2);

        } else if(moveDown){

            setImg(Images.player_front);
            move(3);

        }//end if chain

        draw(g2);

    }//end update

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_A){

            moveLeft = true;

        } else if(keyCode == KeyEvent.VK_D){

            moveRight = true;

        } else if(keyCode == KeyEvent.VK_W){

            moveUp = true;

        } else if(keyCode == KeyEvent.VK_S){

            moveDown = true;

        }//end if chain

    }//end keyPressed

    @Override
    public void keyReleased(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_A){

            moveLeft = false;

        } else if(keyCode == KeyEvent.VK_D){

            moveRight = false;

        } else if(keyCode == KeyEvent.VK_W){

            moveUp = false;

        } else if(keyCode == KeyEvent.VK_S){

            moveDown = false;

        }//end if chain

    }//end keyReleased

    @Override
    public void keyTyped(KeyEvent e) {

    }//end keyTyped

}//end class