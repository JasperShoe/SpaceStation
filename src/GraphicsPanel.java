import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicsPanel extends JPanel {

    //=====instance fields=====

    private Player player;

    //=====constructor=====

    public GraphicsPanel(){

        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setFocusable(true);

        player = new Player(100, 100);
        player.setSpeed(4);
        player.setImg(Images.player_front);
        addKeyListener(player);

        Timer update = new Timer(1000 / 60, new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {

                repaint();

            }//end actionPerformed

        });//end timer

        update.start();

    }//end GraphicsPanel

    //=====methods=====

    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        player.update(g2);

    }//end paintComponent

}//end class