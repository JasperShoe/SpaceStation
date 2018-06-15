package Client;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import Characters.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GUI extends JPanel {
    private int numGuns, pad, scale, w, h, x, y, wX, wY, wW, wH, bX, gunIndex, currentGun;

    private double healthBarGreenW, reloadAnimationH, reloadTimeCount;

    private boolean invOpen, stOpen;

    private JPanel mainPanel, gunSlotPanel, reloadAnimation;

    private JLabel healthBar, healthBarRed, healthBarGreen;

    private JButton openInventory, openSkillTree, primaryGunSlot;

    private MatteBorder border = new MatteBorder(1, 1, 1, 1, new Color(0 ,0 ,0));

    private Player player;

    private int[] translate;

    private int[] start;

    private ArrayList<JButton> buttons, gunSlots;

    private Font font = new Font("Trebuchet MS", Font.BOLD, 10);

    private Timer reloadTimeClock;

    public GUI(Player player){
        setLayout(null);
        setOpaque(false);
        setFocusable(true);

        start = new int[]{x, y};
        this.player = player;

        numGuns = player.getInventory().size();
        gunSlots = new ArrayList<>();
        currentGun = 0;
        reloadAnimationH = 0;

        invOpen = false;
        stOpen = false;

        buttons = new ArrayList<>();
        openInventory = new JButton("INV");
        openInventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!invOpen) {
                    setOpaque(true);
                    invOpen = true;
                    stOpen = false;
                    gunSlotPanel.setOpaque(true);
                    gunSlotPanel.setVisible(true);
                } else {
                    setOpaque(false);
                    invOpen = false;
                    gunSlotPanel.setOpaque(false);
                    gunSlotPanel.setVisible(false);
                }
            }
        });
        openSkillTree = new JButton("ST");
        openSkillTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!stOpen) {
                    setOpaque(false);
                    stOpen = true;
                    invOpen = false;
                    gunSlotPanel.setVisible(false);
                } else {
                    setOpaque(false);
                    stOpen = false;
                }
            }
        });

        buttons.add(openInventory);
        buttons.add(openSkillTree);

        pad = 10;
        scale = 30;
        x = 0;
        y = 0;
        w = 200 + buttons.size()*scale + (2+buttons.size())*pad;
        h = (3+numGuns)*pad + (1+2*numGuns)*scale;
        setBounds(x, y, w, h);

        mainPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(Images.list.get("gui_main_panel"), null, 0, 0);
            }
        };
        mainPanel.setBounds(0, 0, w, 3*pad + 3*scale);
        mainPanel.setLayout(null);
        mainPanel.setOpaque(true);

        healthBar = new JLabel(Integer.toString(player.getHealth()), SwingConstants.CENTER);
        healthBar.setFont(font);
        healthBar.setBounds(pad, pad, w - buttons.size()*scale - (2+buttons.size())*pad, scale);
        healthBar.setBorder(border);

        healthBarRed = new JLabel();
        healthBarRed.setBounds(healthBar.getX(), healthBar.getY(), healthBar.getWidth(), healthBar.getHeight());
        healthBarRed.setOpaque(true);
        healthBarRed.setBackground(Color.RED);

        healthBarGreen = new JLabel();
        healthBarGreenW = healthBar.getWidth() * player.getHealth()/100;
        healthBarGreen.setBounds(healthBar.getX(), healthBar.getY(), (int) healthBarGreenW, healthBar.getHeight());
        healthBarGreen.setOpaque(true);
        healthBarGreen.setBackground(Color.GREEN);

        bX = healthBar.getWidth() + healthBar.getX() + pad;
        for(JButton button : buttons){
            button.setFont(font);
            button.setBorder(border);
            button.setBounds(bX, pad, scale, scale);
            mainPanel.add(button);
            bX += pad + button.getWidth();
        }

        reloadAnimation = new JPanel();
        reloadAnimation.setBackground(Color.GREEN);

        gunSlotPanel = new JPanel();
        gunSlotPanel.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK), BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 150,104))));
        gunSlotPanel.setBackground(new Color(172, 172, 172));
        gunSlotPanel.setBounds(0, mainPanel.getHeight(), w, h - mainPanel.getHeight());
        gunSlotPanel.setLayout(null);
        gunSlotPanel.setVisible(false);

        primaryGunSlot = new JButton();
        gunSlots.add(primaryGunSlot);

        for(int i = 1; i < numGuns; i++){
            gunSlots.add(new JButton());
        }

        wX = pad;
        wY = pad;
        wW = w-2*pad;
        wH = 2*scale;
        for(JButton g : gunSlots) {
            if(gunSlots.indexOf(g) == 0){
                g.setBorder(border);
                g.setBounds(wX, healthBar.getY() + healthBar.getHeight() + pad, wW, wH);
            } else {
                g.setOpaque(true);
                g.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK), BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 150,104))));
                g.setBackground(new Color(214, 214, 214));
                g.setBounds(wX, wY, wW, wH);
                wY += wH + pad;
            }
            g.setIcon(new ImageIcon(player.getInventory().get(gunSlots.indexOf(g)).getImg()));
            g.setFont(font);
            g.setText(player.getInventory().get(gunSlots.indexOf(g)).getClip() + "/" + player.getInventory().get(gunSlots.indexOf(g)).getClipSize());
            g.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gunSlots.indexOf(g) < player.getInventory().size()) {
                        currentGun = (currentGun + gunSlots.indexOf(g))%player.getInventory().size();
                        player.equipGun(player.getInventory().get(currentGun));
                    }
                }
            });
            g.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if ((e.getX() > gunSlotPanel.getX() + gunSlotPanel.getWidth() || e.getY() > gunSlotPanel.getY() + gunSlotPanel.getHeight())) {
                        if (gunSlots.indexOf(g) < player.getInventory().size() && player.getInventory().size() > 1) {
                            player.removeInventory(gunSlots.indexOf(g));
                            player.equipGun(player.getInventory().get(0));
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            if(gunSlots.indexOf(g) == 0) {
                mainPanel.add(g);
            } else {
                gunSlotPanel.add(g);
            }
        }
        add(gunSlotPanel);

        mainPanel.add(healthBar);
        mainPanel.add(healthBarGreen);
        mainPanel.add(healthBarRed);
        mainPanel.add(reloadAnimation);
        add(mainPanel);

        reloadTimeCount = 0;
        reloadTimeClock = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadTimeCount += reloadTimeClock.getDelay();
            }
        });
    }

    public void update(int[] translation){
        setTranslate(translation);

        setX(start[0] - translation[0]);
        setY(start[1] - translation[1]);

        gunIndex = player.getInventory().indexOf(player.getEquipped());
        currentGun = gunIndex;
        for(int i = 0; i < gunSlots.size(); i++){
            gunSlots.get(i).setIcon(new ImageIcon(player.getInventory().get(gunIndex).getImg()));
            gunSlots.get(i).setText(Integer.toString(player.getInventory().get(gunIndex).getClip()) + "/" + player.getInventory().get(gunIndex).getClipSize());

            if(i < player.getInventory().size()) {
                if (gunIndex >= player.getInventory().size() - 1) {
                    gunIndex = 0;
                } else {
                    gunIndex++;
                }
            } else {
                gunSlots.get(i).setIcon(null);
                gunSlots.get(i).setText(null);
            }
        }

        if(player.getEquipped().getReloadDelay().isRunning()) {
            if(!reloadTimeClock.isRunning()){
                reloadTimeCount = 0;
                reloadTimeClock.setDelay((player.getEquipped().getReloadTime())/10);
                reloadTimeClock.start();
            }
            reloadAnimationH = (primaryGunSlot.getHeight() * reloadTimeCount)/(player.getEquipped().getReloadTime());
        } else {
            reloadTimeCount = 0;
            reloadAnimationH = 0;
            reloadTimeClock.stop();
        }
        reloadAnimation.setBounds(primaryGunSlot.getX(), primaryGunSlot.getY() + primaryGunSlot.getHeight() - (int) reloadAnimationH, primaryGunSlot.getWidth(), (int) reloadAnimationH);

        healthBar.setText(Integer.toString(player.getHealth()));
        healthBarGreenW = healthBar.getWidth() * player.getHealth()/100;
        healthBarGreen.setBounds(healthBar.getX(), healthBar.getY(), (int)healthBarGreenW, healthBar.getHeight());
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setTranslate(int[] translate) {
        this.translate = translate;
    }
}
