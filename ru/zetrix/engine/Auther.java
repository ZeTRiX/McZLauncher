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

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import ru.zetrix.settings.Debug;
import ru.zetrix.settings.ShieldUtil;
import ru.zetrix.settings.Util;

/**
 *
 * @author ZeTRiX
 */
public class Auther {
    private static InetAddress LHost;
    public static void Authorize(final String user, final String pass) {
        try {
            LHost = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            print(ex.toString());
        }
        new Thread() {
            @Override
            public void run() {
                print("Processing client data...");
                
                String AuthorizeResult = ru.zetrix.settings.NetUtil.ConnectNet("http://test.zetlog.ru/launchertest/auth.php", "a=auth" + "&user=" + user + "&password=" + ShieldUtil.ShaHash(user + ru.zetrix.settings.ShieldUtil.MACAddr() + pass) + "&opt=" + ru.zetrix.settings.ShieldUtil.MACAddr() + "&localhost=" + LHost.toString());

                if (AuthorizeResult == null) {
                    //BuildGui.this.setAuthError("Error connecting to login server", allpanels);
                    return;
                }
                print("Local Host and Address: " + LHost.toString());
                
                if (AuthorizeResult.trim().equals("Bad Login")) {
                    //BuildGui.this.setAuthError("Username or password is incorrect", allpanels);
                    return;
                } else {
                    BuildGui.AuthOk = true;
                    try {
                        print(AuthorizeResult);
                        BuildGui.hashes = AuthorizeResult.split("_")[0].split("<>");
                        BuildGui.clientinf = AuthorizeResult.split("_")[1].split("<>");
                        
                        print("Auth OK. Starting 'check for download' process...");
                        Util.SleepTime(Long.valueOf(500L));
                        //BuildGui.runUpdater(md5s, userdata, mods, BuildGui.buildgui);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //BuildGui.this.setAuthError(e.toString(), allpanels);
                    }
                }
            }
        }
                .start();
    }
    
    public static void Register(final String user, final char[] pass, final String mail) {
        new Thread() {
            @Override
            public void run() {
                print("Sending client data...");
                String RegResult = ru.zetrix.settings.NetUtil.ConnectNet("http://test.zetlog.ru/launchertest/auth.php", "a=reg" + "&user=" + user + "&password=" + ShieldUtil.ShaHash(user + ru.zetrix.settings.ShieldUtil.MACAddr() + pass) + "&mail=" + mail + "&opt=" + ru.zetrix.settings.ShieldUtil.MACAddr());
                print(RegResult);
                
                if (RegResult == null) {
                    print("Error... Null result");
                    return;
                }
                
                if (RegResult.trim().equals("Fail")) {
                    javax.swing.JOptionPane.showMessageDialog((java.awt.Component)
                    null,
                    "You are already have an account \n Please, do not try to register another one. \n",
                    "Warning - MultiAccount",
                    JOptionPane.WARNING_MESSAGE);
                } else if (RegResult.trim().equals("Success")) {
                    javax.swing.JOptionPane.showMessageDialog((java.awt.Component)
                    null,
                    "You have registered a new account! \n Thank you and enjoy the game. \n",
                    "Ok - Register Complete!",
                    JOptionPane.INFORMATION_MESSAGE);
                    try {
                        print("You have successfully registered...");
                        Util.SleepTime(Long.valueOf(500L));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    javax.swing.JOptionPane.showMessageDialog((java.awt.Component)
                    null,
                    "Oh, something went wrong! \n Try again later. \n",
                    "Warning - Error",
                    JOptionPane.WARNING_MESSAGE);                
                }
            }
        }
                .start();
    }
    
    private static void print(String str) {
        Debug.Logger(str);
    }
}
