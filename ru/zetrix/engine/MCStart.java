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
            if(MZLOptions.AllowFullscreen) {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
            setIconImage(ru.zetrix.settings.Util.getRes("fav.png"));
            setMinimumSize(BuildGui.buildgui.getMinimumSize());
            setVisible(true);
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
