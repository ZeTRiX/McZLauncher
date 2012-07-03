/*
 * McZLauncher (ZeTRiX's Minecraft Launcher)
 * Copyright (C) 2012 Evgen Yanov <http://www.zetlog.ru>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program (In the "_License" folder). If not, see <http://www.gnu.org/licenses/>.
*/

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
    private JLabel UpdateText = new JLabel("Nothing goes here, yet");
    public Options() {
        super("Options");
        setIconImage(ru.zetrix.settings.Util.getRes("ficon.png"));
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
