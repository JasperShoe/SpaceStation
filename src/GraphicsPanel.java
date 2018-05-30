import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicsPanel extends JPanel {
    private Player player;

    public GraphicsPanel(){
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setFocusable(true);

        player = new Player(400-16, 400-16);
        player.setSpeed(4);
        player.setImg(Images.player_front);
        addKeyListener(player);

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
        g2.translate(player.getTransX(), player.getTransY());
        player.update(g2);
    }
}