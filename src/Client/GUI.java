package Client;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import Characters.Player;
import World.Floor;
import World.Pickup;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GUI extends JPanel {
    private int numGunSlots, pad, scale, w, h, x, y, gunSlotX, gunSlotY, gunSlotW, gunSlotH, buttonX, gunIndex, currentGun;

    private double healthBarGreenW, reloadAnimationH, reloadTimeCount;

    private boolean invOpen, stOpen;

    private JPanel mainPanel, gunSlotsPanel, reloadAnimation;

    private JLabel mainPanelOverlay, healthBar, healthBarRed, healthBarGreen;

    private JButton openInventory, openUpgrades, primaryGunSlot;

    private MatteBorder border = new MatteBorder(1, 1, 1, 1, new Color(0 ,0 ,0));

    private CompoundBorder compoundBorder = new CompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK), BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 150,104)));

    private Player player;

    private int[] translate;

    private int[] start;

    private ArrayList<JButton> buttons, gunSlots;

    private ArrayList<JLabel> gunSlotsUnderlay;

    private Font font = new Font("Trebuchet MS", Font.BOLD, 10);

    private Timer reloadTimeClock;

    private Floor floor;

    private Map map;

    public GUI(Player player, Floor floor){
        setLayout(null);
        setOpaque(false);
        setFocusable(true);

        start = new int[]{x, y};
        this.player = player;
        this.floor = floor;

        int mapW = 200, mapH = 200;
        map = new Map(player, floor, 800-mapW, 0, mapW, mapH);
        add(map);

        numGunSlots = 5;
        gunSlots = new ArrayList<>();
        gunSlotsUnderlay = new ArrayList<>();
        currentGun = 0;
        reloadAnimationH = 0;

        invOpen = false;
        stOpen = false;

        buttons = new ArrayList<>();
        openInventory = new JButton();
        openInventory.setIcon(new ImageIcon(Images.list.get("icon_inventory")));
        openInventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleInv();
            }
        });
        openUpgrades = new JButton();
        openUpgrades.setIcon(new ImageIcon(Images.list.get("icon_upgrades")));
        openUpgrades.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleInv();
            }
        });

        buttons.add(openInventory);
        buttons.add(openUpgrades);

        pad = 10;
        scale = 30;
        x = 0;
        y = 0;
        w = 200 + buttons.size()*scale + (2+buttons.size())*pad;
        h = (3+ numGunSlots)*pad + (1+2* numGunSlots)*scale;
        setBounds(x, y, 800, 800);

        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, w, 3*pad + 3*scale);
        mainPanel.setLayout(null);
        mainPanel.setOpaque(true);
        mainPanel.setBackground(new Color(214, 214, 214));

        mainPanelOverlay = new JLabel();
        mainPanelOverlay.setBounds(mainPanel.getBounds());
        mainPanelOverlay.setIcon(new ImageIcon(Images.list.get("gui_main_panel_overlay")));

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

        buttonX = healthBar.getWidth() + healthBar.getX() + pad;
        for(JButton button : buttons){
            button.setFont(font);
            button.setBorder(border);
            button.setBounds(buttonX, pad, scale, scale);
            mainPanel.add(button);
            buttonX += pad + button.getWidth();
        }

        reloadAnimation = new JPanel();
        reloadAnimation.setBackground(Color.GREEN);

        gunSlotsPanel = new JPanel();
        gunSlotsPanel.setBorder(compoundBorder);
        gunSlotsPanel.setOpaque(false);
        gunSlotsPanel.setBackground(new Color(172, 172, 172));
        gunSlotsPanel.setBounds(0, mainPanel.getHeight(), w, h - mainPanel.getHeight());
        gunSlotsPanel.setLayout(null);
        gunSlotsPanel.setVisible(false);

        primaryGunSlot = new JButton();
        gunSlots.add(primaryGunSlot);

        for(int i = 1; i < numGunSlots; i++){
            gunSlots.add(new JButton());
            gunSlotsUnderlay.add(new JLabel());
        }

        gunSlotX = pad;
        gunSlotY = pad;
        gunSlotW = w-2*pad;
        gunSlotH = 2*scale;
        for(JButton g : gunSlots) {
            g.setBorder(border);
            if(gunSlots.indexOf(g) == 0){
                g.setBounds(gunSlotX, healthBar.getY() + healthBar.getHeight() + pad, gunSlotW, gunSlotH);
            } else {
                g.setBounds(gunSlotX, gunSlotY, gunSlotW, gunSlotH);
                gunSlotY += gunSlotH + pad;
            }
            g.setPreferredSize(new Dimension(g.getBounds().width, g.getBounds().height));
            g.setBorderPainted(false);
            g.setFont(font);
            g.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int targetGun = (currentGun + gunSlots.indexOf(g))%player.getInventory().size();
                    if (gunSlots.indexOf(g) < player.getInventory().size()) {
                        player.equipGun(player.getInventory().get(targetGun));
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
                    if (!GraphicsPanel.cursor.intersects(getGunSlotsPanelBounds()) && gunSlots.indexOf(g) < player.getInventory().size()) {
                        int targetGun = (currentGun + gunSlots.indexOf(g))%player.getInventory().size();
                        if (player.getInventory().size() > 1 && targetGun != 0) {
                            Pickup drop = new Pickup(player.getInventory().get(targetGun), player.getFloor());
                            drop.init(GraphicsPanel.cursor.x, GraphicsPanel.cursor.y, player.getFloor());
                            player.removeInventory(targetGun);
                            targetGun = (currentGun + gunSlots.indexOf(g))%player.getInventory().size();
                            player.equipGun(player.getInventory().get(targetGun));
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
                gunSlotsPanel.add(g);
            }
        }

        for(JLabel l : gunSlotsUnderlay){
            l.setBounds(gunSlots.get(gunSlotsUnderlay.indexOf(l) + 1).getBounds());
            l.setIcon(new ImageIcon(Images.list.get("gui_gunslot_underlay")));
            l.setPreferredSize(new Dimension(gunSlots.get(gunSlotsUnderlay.indexOf(l) + 1).getBounds().width, gunSlots.get(gunSlotsUnderlay.indexOf(l) + 1).getBounds().height));
            gunSlotsPanel.add(l);
        }
        add(gunSlotsPanel);

        mainPanel.add(mainPanelOverlay);
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
        map.setTranslate(translation);

        setX(start[0] - translation[0]);
        setY(start[1] - translation[1]);

        gunIndex = player.getInventory().indexOf(player.getEquipped());
        currentGun = gunIndex;
        for(int i = 0; i < gunSlots.size(); i++){
            if(i < player.getInventory().size()) {
                if (i != 0) {
                    gunSlots.get(i).setIcon(new ImageIcon(Images.createCombinedImage(new ArrayList<BufferedImage>() {{
                        add(player.getInventory().get(gunIndex).getImg());
                    }}, 1, 1, null, null, null, 0, 0)));
                    gunSlots.get(i).setText(Integer.toString(player.getInventory().get(gunIndex).getClip()) + "/" + player.getInventory().get(gunIndex).getMagazineText());
                } else {
                    gunSlots.get(i).setIcon(new ImageIcon(player.getEquipped().getImg()));
                    gunSlots.get(i).setText(Integer.toString(player.getEquipped().getClip()) + "/" + player.getEquipped().getMagazineText());
                }

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
                reloadTimeClock.setDelay((player.getEquipped().getReloadTime())/primaryGunSlot.getHeight());
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

    public void toggleInv(){
        invOpen = !invOpen;
        stOpen = !stOpen;
        gunSlotsPanel.setOpaque(!gunSlotsPanel.isOpaque());
        gunSlotsPanel.setVisible(!gunSlotsPanel.isVisible());
    }

    public int getNumGunSlots(){
        return numGunSlots;
    }

    public Rectangle getGunSlotsPanelBounds(){
        Rectangle rect = (Rectangle) gunSlotsPanel.getBounds().clone();
        rect.setLocation(getX(), getY() + gunSlotsPanel.getY());
        return rect;
    }
}
