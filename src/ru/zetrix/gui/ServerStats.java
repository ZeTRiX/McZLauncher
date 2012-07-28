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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import ru.zetrix.settings.MonitorUtils;

public class ServerStats extends JComponent {
    private static final long serialVersionUID = 1L;
    public String[] nullr = {"0", "0", "0"};
    public BufferedImage ServerIcon = MonitorUtils.genServerIcon(nullr);
    public String ServerStatus = MonitorUtils.genServerStatus(nullr);
    
    @Override
    protected void paintComponent(Graphics maing) {
        Graphics2D g = (Graphics2D)maing.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(ServerIcon, 0, 0, ServerIcon.getWidth(), ServerIcon.getHeight(), null);
        g.drawString(ServerStatus, ServerIcon.getWidth() + 2, ServerIcon.getHeight() / 2 + g.getFontMetrics().getHeight() / 4);
        g.dispose();
        super.paintComponent(maing);
    }
    
    public void updateBar(String Stat, BufferedImage Icon) {
        ServerStatus = Stat;
        ServerIcon = Icon;
        repaint();
    }
}