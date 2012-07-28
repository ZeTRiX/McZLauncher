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
import ru.zetrix.engine.BuildGui;

/**
 *
 * @author ZeTRiX
 */
public class MonitorUtils {
    public static Thread serverPollThread;

    public static String[] CreateServerList() {
        String NamesList[] = new String[MZLOptions.serverlist.length];
        for (int cnt = 0; cnt < MZLOptions.serverlist.length; cnt++)
            NamesList[cnt] = MZLOptions.serverlist[cnt].split(":")[0];
        return NamesList;
    }

    public static String genServerStatus(String[] data) {
        if ((data[0] == null) && (data[1] == null) && (data[2] == null)) return "Server is offline";
        if ((data[1] != null) && (data[2] != null)) {
            if (data[1].equals(data[2])) return "Server is full (Players: " + data[1] + " of " + data[2] + ")";
            return data[1] + " of " + data[2] + " players now playing on this server.";
        }
        return "Server unreachable";
    }

    public static BufferedImage genServerIcon(String[] data) {
        BufferedImage MonitIcon = Util.getRes("monitor.png");
        if ((data[0] == null) && (data[1] == null) && (data[2] == null)) return MonitIcon.getSubimage(0, 0, MonitIcon.getHeight(), MonitIcon.getHeight());
        if ((data[1] != null) && (data[2] != null)) {
            if (data[1].equals(data[2])) return MonitIcon.getSubimage(MonitIcon.getHeight(), 0, MonitIcon.getHeight(), MonitIcon.getHeight());
            return MonitIcon.getSubimage(MonitIcon.getHeight() * 2, 0, MonitIcon.getHeight(), MonitIcon.getHeight());
        } return MonitIcon.getSubimage(MonitIcon.getHeight() * 3, 0, MonitIcon.getHeight(), MonitIcon.getHeight());
    }
    
    public static void pollSelectedServer() {
        serverPollThread = new Thread() {
            @Override
            public void run() {
                BuildGui.MonitGui.updateBar("Updating...", BuildGui.MonitGui.ServerIcon);
                
                String ServerName = MZLOptions.serverlist[Util.getPropertyInt("server", 0)].split(":")[0];
                String IP = MZLOptions.serverlist[Util.getPropertyInt("server", 0)].split(":")[1];
                if (IP.equals("nsrv")) {
                    BuildGui.MonitGui.updateBar("No server chosen", genServerIcon(new String[] { "0","0","0" }));
                    return;
                }
                int Port = Integer.parseInt(MZLOptions.serverlist[Util.getPropertyInt("server", 0)].split(":")[2]);
                
                String[] GetMonitData = (ru.zetrix.settings.NetUtil.ConnectNet(MZLOptions.AuthScrpt, "a=monitor" + "&ip=" + IP + "&port=" + Port)).trim().split(":");
                String[] MonitData = {ServerName, GetMonitData[0], GetMonitData[1]};
                BuildGui.MonitGui.updateBar(genServerStatus(MonitData), genServerIcon(MonitData));
                
                serverPollThread.interrupt();
                serverPollThread = null;
                print("Refreshing server done!");
            }
        };
        serverPollThread.start();
    }
    
    private static void print(String str) {
        Debug.Logger(str);
    }
}
