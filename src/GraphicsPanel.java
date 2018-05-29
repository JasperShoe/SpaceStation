import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicsPanel extends JPanel {
    public static Player player;

    public GraphicsPanel(){
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setFocusable(true);

        player = new Player(100, 100);
        player.setSpeed(10);
        player.setImg(Images.playerImg);

        Timer update = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        update.start();
    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
    }
}