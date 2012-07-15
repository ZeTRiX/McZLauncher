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

import java.util.ArrayList;
import javax.swing.JFrame;
import ru.zetrix.gui.PreloadImg;
import ru.zetrix.settings.Debug;
import ru.zetrix.settings.Util;

public class StartEngine {
    public static int memory = Util.getPropertyInt("memory", 1024);
    public static void main(String[] args) throws Exception {
        print("Memory allocated: " + memory + " MB");
        
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
        
        print("Starting Minecraft ZeTRiX's Launcher");
        if (heapSizeMegs > 511.0F) {
            BuildGui.main(args); 
        } else {
            try {
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
