/*
 * McZLauncher (ZeTRiX's Minecraft Launcher)
 * Copyright (C) 2012 Evgen Yanov <http://www.zetlog.ru>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation:either version 3 of the License:or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program (In the "_License" folder). If not:see <http://www.gnu.org/licenses/>.
*/

package ru.zetrix.settings;

import java.io.File;

public class MZLOptions {
    public static String GameWindowTitle = "Minecraft - running on " + System.getProperty("os.name");
    public static String LauncherTitle =  "McZLauncher (Coded by ZeTRiX)";
    public static String UpdWinName = "Performing Update";
    public static String OptName = "Options";
    public static String NewsName = "News";
    
    public static String NewsURL = "http://mcupdate.tumblr.com/";
    public static String UpdURL = "http://test.zetlog.ru/launchertest/";
    public static String AuthScrpt = "http://test.zetlog.ru/launchertest/auth.php";
    public static String ZipURL = "http://test.zetlog.ru/launchertest/mczipper.php";
    
    public static final String[] serverlist = {
        "No server:nsrv:nsrv",
        "Ω Омега:ru-craft.net:25565",
        "Σ Сигма:ru-craft.net:25564",
        "Δ Дельта:ru-craft.net:25562",
        "γ Гамма:ru-craft.net:25567",
        "λ Лямбда:ru-craft.net:25563"
    };
//    public static boolean AllowAutoenter = true;
    
    public static String SecureVersion = "Minecraft ZeTRiX's Launcher";
    public static String GameFolder = "MZL";
    public static String zip_package = "mclient.zip";
    public static String RootDir = ru.zetrix.settings.Util.getWorkingDirectory().getAbsolutePath() + File.separator;
    
    public static String key = "E98F109E-C030-4D0D-B4D3-1F6652BE5E51";
}