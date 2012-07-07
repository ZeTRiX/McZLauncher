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

import java.io.File;

public class MZLOptions {
    public static String GameWindowTitle = "Minecraft - running on " + System.getProperty("os.name");
    public static String LauncherTitle =  "McZLauncher (Coded by ZeTRiX)";
    
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
    public static String UpdURL = "http://test.zetlog.ru/launchertest/";
    public static String zip_package = "mclient.zetrix";
    public static String RootDir = ru.zetrix.settings.Util.getWorkPath(GameFolder).getAbsolutePath() + File.separator;
}
