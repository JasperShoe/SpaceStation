package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

public class Main extends JFrame {
    public static Main frame;
    public static Menu menu;

    public static GraphicsPanel graphics;

    public Main(){
        super("Space Station");
        setSize(800, 800 + 22);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        menu = new Menu();
        graphics = new GraphicsPanel();
        add(menu);

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
       frame = new Main();
    }

    public static void switchPanels(){
        if(menu.getPlayGame()){
            frame.remove(menu);
            frame.add(graphics);
        } else {
            frame.remove(graphics);
            frame.add(menu);
        }
    }
}