package ru.zetrix.settings;

import java.io.File;

/**
 *
 * @author ZeTRiX
 */
public class MZLOptions {
    public static String GameWindowTitle = "Minecraft - running on " + System.getProperty("os.name");
    public static String LauncherTitle =  "MC Launcher (Coded by ZeTRiX)";
    
    public static final String[] servers = { 
        "Ensemplix - Sandbox, sv1.ensemplix.ru, 25565", 
        "Ensemplix - HiTech, sv2.ensemplix.ru, 25563", 
        "Ensemplix - Cogito, sv2.ensemplix.ru, 25565", 
        "Ensemplix - Carnage, sv1.ensemplix.ru, 25563", 
        "Ensemplix - Davids, sv1.ensemplix.ru, 25564", 
        "Оффлайн, offline, offline" };
    
    public static boolean AllowAutoenter = true;
    public static boolean AllowFullscreen = true;
    
    public static String GameFolder = "MZL";
    public static String zip_package = "mclient.zip";
    public static String RootDir = ru.zetrix.settings.Util.getWorkPath(GameFolder).getAbsolutePath() + File.separator;
}
