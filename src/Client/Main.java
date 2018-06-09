package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

public class Main extends JFrame {
    private GraphicsPanel graphics = new GraphicsPanel();

    public Main(){
        super("Space Station");
        setSize(800, 800 + 22);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        add(graphics);
        setVisible(true);

        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Images.list.get("cursor"), new Point(0,0), "cursor"));
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });

    }

    public static void main(String[] args) {
        new Main();
    }
}