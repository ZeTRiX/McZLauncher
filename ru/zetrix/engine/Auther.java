package ru.zetrix.engine;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

        String AuthorizeResult = ru.zetrix.settings.NetUtil.ConnectNet("http://test.zetlog.ru/launchertest/auth.php", "user=" + user + "&password=" + ShieldUtil.ShaHash(pass) + "&opt=" + ru.zetrix.settings.ShieldUtil.MACAddr() + "&localhost=" + LHost.toString());
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
    private static void print(String str) {
        Debug.Logger(str);
    }
}
