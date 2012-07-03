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

package ru.zetrix.gui.laf;

import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * @author ZeTRiX
 */
public class MZButtonUI extends BasicButtonUI {
    public static ComponentUI createUI(JComponent c) {
        return new MZButtonUI();
    }
    
    public void installUI (JComponent c) {
        super.installUI (c);
        AbstractButton button = (AbstractButton) c;
        
        button.setOpaque(false);
        button.setFocusable(true);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    }
    
    final ImageIcon icon = new ImageIcon(MZButtonUI.class.getResource("/ru/zetrix/gui/laf/images/button.png"));
    
    public void paint(Graphics g, JComponent c) {
        g.drawImage(icon.getImage(), 0,0, null);
        super.paint(g, c);
    }
}
