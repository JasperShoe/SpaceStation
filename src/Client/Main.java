package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class Main extends JFrame {
    public static Main frame;
    public static Menu menu;
    public static GraphicsPanel graphics;
    final Taskbar taskbar = Taskbar.getTaskbar();
    final ImageIcon ICON = new ImageIcon(Images.list.get("logo"));

    public Main(){
        super("Space Station");
        setSize(800, 800 + 22);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        setIconImage(ICON.getImage());

        try {
            taskbar.setIconImage(ICON.getImage());
        } catch (final UnsupportedOperationException e) {
            System.out.println("The os does not support: 'taskbar.setIconImage'");
        } catch (final SecurityException e) {
            System.out.println("There was a security exception for: 'taskbar.setIconImage'");
        }

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
//            GraphicsPanel.textModule.init();
        } else {
            frame.remove(graphics);
            frame.add(menu);
        }
    }
}