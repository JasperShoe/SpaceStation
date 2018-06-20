package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by student on 6/19/18.
 */
public class TextModule extends JPanel {

    private JTextField text;
    private ArrayList<JButton> options;
    private JPanel parent;
    public TextModule(JPanel parent, int w, int h){
        this.parent = parent;
        int height = 350;
        setBounds(w / 10, h - height - 20, w * 4 / 5, height);
        setLayout(null);
        setOpaque(true);
        setFocusable(true);
        setVisible(false);
        setBackground(new Color(0, 1, 26, 177));

        text = new JTextField("LLLLLL");
        text.setForeground(Color.white);
        text.setEditable(false);
        text.setBackground(new Color(0,0,0,0));
        text.setBorder(BorderFactory.createEmptyBorder());
        text.setBounds(getWidth()/10, getHeight()/10, getWidth() * 4 / 5, getHeight() * 2 / 5);
        add(text);
        options = new ArrayList<>();

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                clear();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void init(){
        int height = 350;
        setBounds(0, parent.getHeight() - height, parent.getWidth(), height);
    }

    public void setText(String content){
        text.setText(content);
    }

    public void addOptions(String[] optionSet, ActionListener[] listeners){
        for (int i = 0; i < optionSet.length; i++) {
            JButton jb = new JButton(optionSet[i]);
            jb.setBounds(getWidth()/10, getHeight() * 3 / 5 + 100 * i, text.getWidth(), 50);
            jb.addActionListener(listeners[i]);
            options.add(jb);
        }
    }


    public void clear(){
        text.setText("");
        for (int i = options.size(); i >0 ; i--) {
            remove(options.get(i-1));
        }
        setVisible(false);
    }

}
