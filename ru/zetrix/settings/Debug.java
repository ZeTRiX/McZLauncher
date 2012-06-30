package ru.zetrix.settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Debug mode options and functions
 * @author ZeTRiX
 */
public class Debug {
    public static final boolean debug = true; //Вывод действий лаунчера в консоль
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