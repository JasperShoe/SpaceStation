import javax.swing.*;
import java.awt.event.ActionEvent;

public class Player extends Sprite {
    private InputMap inputMap;

    private ActionMap actionMap;

    private Action moveLeft = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            move(0);
        }
    };

    private Action moveRight = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            move(1);
        }
    };

    private Action moveUp = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            move(2);
        }
    };

    private Action moveDown = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            move(3);
        }
    };

    public Player(int x, int y){
        super(x, y);

        //Inputs
        inputMap = Main.graphics.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("A"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("D"), "moveRight");
        inputMap.put(KeyStroke.getKeyStroke("W"), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke("S"), "moveDown");


        //Actions
        actionMap = Main.graphics.getActionMap();
        actionMap.put("moveLeft", moveLeft);
        actionMap.put("moveRight", moveRight);
        actionMap.put("moveUp", moveUp);
        actionMap.put("moveDown", moveDown);
    }
}