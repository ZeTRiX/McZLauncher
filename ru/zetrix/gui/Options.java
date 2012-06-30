package ru.zetrix.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ZeTRiX
 */
public class Options extends JFrame {
    private JLabel UpdateText = new JLabel("No Options, yet");
    public Options() {
        super("Options");
        setIconImage(ru.zetrix.settings.Util.getRes("fav.png"));
        setBackground(Color.BLACK);
        this.setBounds(300, 300, 260, 140);
        setResizable(false);
        
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3,2,2,2));
        final ImageIcon icon = new ImageIcon(Options.class.getResource("/ru/zetrix/res/bg.png"));
        JPanel mpan = new JPanel() {
            protected void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0,0, null);
                super.paintComponent(g);
            }
        };;
        mpan.add(UpdateText, "Center");
        container.add(mpan, "Center");
    }

}
