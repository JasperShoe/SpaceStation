import javax.swing.*;

public class Main extends JFrame {

    private GraphicsPanel graphics = new GraphicsPanel();

    public Main(){

        super("Space Station");
        setSize(800, 800 + 22);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        add(graphics);
        setVisible(true);

    }//end Main

    public static void main(String[] args) {

        new Main();

    }//end psvm

}//end class