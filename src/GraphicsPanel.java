import javax.swing.*;
import java.awt.*;

public class GraphicsPanel extends JPanel {
    public GraphicsPanel(){
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setFocusable(true);
    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
    }
}
