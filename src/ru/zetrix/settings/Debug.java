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
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Debug mode options and functions
 * @author ZeTRiX
 */
public class Debug {
    public static final boolean debug = true; //Debug-режим(Режим отладки) для лаунчера - добавляет соотв. параметр к запуску.
    public static final boolean splashlogo = false; //Иконка перед загрузкой лаунчера. Без пре-лого лаунчер грузится быстрее!

    public static void Logger(String text) {
        File WD = new File(Util.getWorkingDirectory().getAbsolutePath() + File.separator + "logs" + '/');
        if (!WD.exists()) {
            WD.mkdir();
        } else {
            File logfile = new File(Util.getWorkingDirectory().getAbsolutePath() + File.separator + "logs" + File.separator + "MZL_Log.txt");
            if (!logfile.exists()) {
                try {
                    logfile.createNewFile();
                } catch (IOException exep) {
                    exep.printStackTrace();
                }
            } else {
                try {
                    FileOutputStream fos = new FileOutputStream(Util.getWorkingDirectory().getAbsolutePath() + File.separator + "logs" + File.separator + "MZL_Log.txt", true);
                    fos.write((text + "\r\n").getBytes());
                    fos.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}