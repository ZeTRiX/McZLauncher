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

package ru.zetrix.engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import ru.zetrix.settings.Debug;
import ru.zetrix.settings.MZLOptions;
import ru.zetrix.settings.Util;

/**
 *
 * @author ZeTRiX
 */
public class MCStart extends JFrame {
    private static final long serialVersionUID = 1L;
    public Map<String, String> ClientData = new HashMap<String, String>();
    
    public MCStart(String user, String session) {
        try {
            String bin = ru.zetrix.settings.Util.getWorkingDirectory().toString() + File.separator + "bin" + File.separator;
            setForeground(Color.BLACK);
            setBackground(Color.BLACK);
            
            print("Detecting useful files...");
            URL[] urls = new URL[4];
            urls[0] = new File(bin, "minecraft.jar").toURI().toURL();
            urls[1] = new File(bin, "lwjgl.jar").toURI().toURL();
            urls[2] = new File(bin, "jinput.jar").toURI().toURL();
            urls[3] = new File(bin, "lwjgl_util.jar").toURI().toURL();
            
            print("Setting user: " + user + ", " + session);
            final MCLauncher mcapplet = new MCLauncher(bin, urls);
            mcapplet.ClientData.put("username", user);
            mcapplet.ClientData.put("sessionid", session);
            mcapplet.ClientData.put("stand-alone", "true");
//			if(MZLOptions.AllowAutoenter && !MZLOptions.servers[BuildGui.buildgui.serverList.getSelectedIndex()].split(", ")[1].equals("offline"))
//			{
//				mcapplet.ClientData.put("server", MZLOptions.servers[BuildGui.buildgui.serverList.getSelectedIndex()].split(", ")[1]);
//				mcapplet.ClientData.put("port", MZLOptions.servers[BuildGui.buildgui.serverList.getSelectedIndex()].split(", ")[2]);
//			}

            setTitle(MZLOptions.GameWindowTitle);
            setBounds(BuildGui.buildgui.getBounds());
            setExtendedState(BuildGui.buildgui.getExtendedState());
            addWindowListener(new WindowListener() {
                @Override
                public void windowClosing(WindowEvent paramWindowEvent)	{
                    mcapplet.stop();
                    mcapplet.destroy();
                    System.exit(0);
                }
                @Override
                public void windowClosed(WindowEvent paramWindowEvent){}
                @Override
                public void windowOpened(WindowEvent paramWindowEvent){}
                @Override
                public void windowIconified(WindowEvent paramWindowEvent) {}
                @Override
                public void windowDeiconified(WindowEvent paramWindowEvent) {}
                @Override
                public void windowActivated(WindowEvent paramWindowEvent) {}
                @Override
                public void windowDeactivated(WindowEvent paramWindowEvent) {}
            });
            
            mcapplet.setForeground(Color.BLACK);
            mcapplet.setBackground(Color.BLACK);
            setLayout(new BorderLayout());
            add(mcapplet, BorderLayout.CENTER);
            validate();
            if(Util.getPropertyBoolean("fullscreen") == true) {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
            setIconImage(ru.zetrix.settings.Util.getRes("ficon.png"));
            setMinimumSize(BuildGui.buildgui.getMinimumSize());
            this.setVisible(true);
            mcapplet.init();
            mcapplet.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void print(String str) {
        Debug.Logger(str);
    }

}
