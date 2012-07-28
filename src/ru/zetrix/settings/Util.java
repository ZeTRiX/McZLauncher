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

package ru.zetrix.settings;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Util {
    public static File workDir = null;
    private static ConfigUtils ConfigFile = new ConfigUtils("/ru/zetrix/res/MZL.config", new File(getWorkingDirectory(), "MZL.config"));
    static {
        ConfigFile.load();
    }
    
    public static File getWorkingDirectory() {
        if (workDir == null)
            workDir = getWorkPath(MZLOptions.GameFolder);
        return workDir;
    }
    
    public static File getWorkPath(String workDir){
        String userHome = System.getProperty("user.home", ".");
        File workingDirectory;
        switch (getPlatform().ordinal()) {
            case 0:
            case 1:
                workingDirectory = new File(userHome, '.' + workDir + '/');
                break;
            case 2:
                String applicationData = System.getenv("APPDATA");
                if (applicationData != null)
                    workingDirectory = new File(applicationData, "." + workDir + '/');
                else
                    workingDirectory = new File(userHome, '.' + workDir + '/');
                break;
            case 3:
                workingDirectory = new File(userHome, "Library/Application Support/" + workDir);
                break;
            default:
                workingDirectory = new File(userHome, workDir + '/');
        }
        if ((!workingDirectory.exists()) && (!workingDirectory.mkdirs()))
            throw new RuntimeException("The working directory could not be created: " + workingDirectory);
        return workingDirectory;
    }
    
    public static OS getPlatform() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return OS.windows;
        }
        if (osName.contains("mac")) {
            return OS.macos;
        }
        if (osName.contains("solaris")) {
            return OS.solaris;
        }
        if (osName.contains("sunos")) {
            return OS.solaris;
        }
        if (osName.contains("linux")) {
            return OS.linux;
        }
        if (osName.contains("unix")) {
            return OS.linux;
        }
        return OS.unknown;
    }
    
    public enum OS {
        linux,
        solaris,
        windows,
        macos,
        unknown
    }
        
    public static void SleepTime(Long sll) {
        try {
            Thread.sleep(sll.longValue());
        } catch (Exception localException) {
            print(localException.toString());
        }
    }
    
    public static BufferedImage getRes(String resname) {
        try {
            BufferedImage img = ImageIO.read(ru.zetrix.engine.BuildGui.class.getResource("/ru/zetrix/res/img/" + resname));
            print("Loading resourse: " + resname);
            return img;
        } catch (Exception e) {
            print("Error loading resource: " + resname);
            print("Text of Error: " + e);
        } return new BufferedImage(1, 1, 2);
    }
        
    public static void setProperty(String s, Object value) {
        if (ConfigFile.checkProperty(s).booleanValue()) ConfigFile.changeProperty(s, value); else
            ConfigFile.put(s, value);
    }
    
    public static String getPropertyString(String s) {
        if (ConfigFile.checkProperty(s).booleanValue()) return ConfigFile.getPropertyString(s);
        return null;
    }
    
    public static boolean getPropertyBoolean(String s) {
        if (ConfigFile.checkProperty(s).booleanValue()) return ConfigFile.getPropertyBoolean(s).booleanValue();
        return false;
    }
    
    public static int getPropertyInt(String s) {
        if (ConfigFile.checkProperty(s).booleanValue()) return ConfigFile.getPropertyInteger(s).intValue();
        return 0;
    }
    
    public static int getPropertyInt(String s, int d) {
        if (ConfigFile.checkProperty(s).booleanValue()) return ConfigFile.getPropertyInteger(s).intValue();
        return d;
    }
    
    private static void print(String str) {
        Debug.Logger(str);
    }
}
