package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Menu extends JPanel {
    private JPanel buttonsPanel;
    private JLabel title;
    private JButton play;
    private Font titleFont = new Font("Trebuchet MS", Font.BOLD, 100), buttonFont = new Font("Trebuchet MS", Font.BOLD, 50);
    private GridBagConstraints gbc = new GridBagConstraints();
    private ArrayList<JComponent> components = new ArrayList<>();
    private boolean playGame = false;

    public Menu(){
        setSize(WIDTH, HEIGHT);
        setLayout(new GridBagLayout());
        setFocusable(true);

        title = new JLabel("Space Station");
        title.setFont(titleFont);
        components.add(title);

        play = new JButton("Play");
        play.setFont(buttonFont);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playGame = true;
                Main.switchPanels();
            }
        });
        components.add(play);

        gbc.insets = new Insets(0, 20, 20, 20);
        gbc.fill = gbc.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        for(JComponent component : components){
            gbc.gridy += 1;
            add(component, gbc);
        }
    }

    public boolean getPlayGame(){
        return playGame;
    }
}