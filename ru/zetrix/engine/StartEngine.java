package ru.zetrix.engine;

import java.util.ArrayList;
import javax.swing.JFrame;
import ru.zetrix.gui.PreloadImg;
import ru.zetrix.settings.Debug;

/**
 *
 * @author ZeTRiX
 */
public class StartEngine {
    public static int memory = 1024;    
    public static void main(String[] args) throws Exception {

        if (Debug.splashlogo == true) {
        JFrame preload = new JFrame("Loading...");
        com.sun.awt.AWTUtilities.setWindowOpacity(preload, 0.8f);
        preload.setBounds(450, 300, 528, 319);
        preload.add(new PreloadImg("load.png"));
        preload.setUndecorated(true);
        preload.setVisible(true);
        Thread.sleep(1500);
        preload.setVisible(false);
        }
        
        float heapSizeMegs = (float)(Runtime.getRuntime().maxMemory() / 1024L / 1024L);
        
        if (heapSizeMegs > 511.0F) {
            BuildGui.main(args); 
        } else {
            try {
                print("Starting Minecraft ZeTRiX's Launcher");
                //memory = ru.zetrix.settings.Util.getMemorySelection();
                ArrayList<String> params = new ArrayList<String>();
                
                if (ru.zetrix.settings.Util.getPlatform() == ru.zetrix.settings.Util.OS.windows) {
                    params.add("javaw"); // для Windows
                } else {
                    params.add("java"); // для Linux/Mac/остальных
                }
                if (Debug.debug == true) {
                params.add("-Xdebug");
                }
                params.add("-Xms512m");
                params.add("-Xmx" + memory + "m");
                params.add("-XX:+UseConcMarkSweepGC");
                params.add("-XX:+CMSIncrementalPacing");
                params.add("-XX:+AggressiveOpts");
                params.add("-Dsun.java2d.noddraw=true");
                params.add("-classpath");
                params.add(BuildGui.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                params.add("ru.zetrix.engine.BuildGui");
            
            ProcessBuilder MZL_Engine = new ProcessBuilder(params);
            Process process = MZL_Engine.start();
            
            if (process == null) throw new Exception("!");
            System.exit(0);
        } catch (Exception e) {
            print("Error: " + e);
            e.printStackTrace();
            BuildGui.main(args);
        }
    }
    }
    
    private static void print(String str) {
        Debug.Logger(str);
    }    
}
