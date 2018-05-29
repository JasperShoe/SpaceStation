import javax.swing.*;
import java.awt.event.ActionEvent;

public class Main extends JFrame {
    private GraphicsPanel graphics = new GraphicsPanel();

    private InputMap inputMap;

    private ActionMap actionMap;

    private Action moveLeft = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            graphics.player.move(0);
        }
    };

    private Action moveRight = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            graphics.player.move(1);
        }
    };

    private Action moveUp = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            graphics.player.move(2);
        }
    };

    private Action moveDown = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            graphics.player.move(3);
        }
    };

    public Main(){
        super("Space Station");
        setSize(800, 800 + 22);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        add(graphics);
        setVisible(true);

        //Inputs
        inputMap = graphics.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("A"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("D"), "moveRight");
        inputMap.put(KeyStroke.getKeyStroke("W"), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke("S"), "moveDown");


        //Actions
        actionMap = graphics.getActionMap();
        actionMap.put("moveLeft", moveLeft);
        actionMap.put("moveRight", moveRight);
        actionMap.put("moveUp", moveUp);
        actionMap.put("moveDown", moveDown);
    }

    public static void main(String[] args) {
        new Main();
    }
}