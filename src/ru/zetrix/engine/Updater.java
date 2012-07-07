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

import java.awt.Cursor;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import ru.zetrix.settings.Debug;
import ru.zetrix.settings.MZLOptions;

public class Updater {
    public static final boolean debug = true;
    public static void Update() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //BuildGui.tabbedPane.remove(1);
                    BuildGui.tabbedPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BuildGui.OutText.setText("<span style=\"font-size: 15pt\">Connecting to Download Server...</span>");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException exeption) {
                        BuildGui.OutText.setText(exeption.toString());
                    }
                    
                    URL url = new URL(MZLOptions.UpdURL + MZLOptions.zip_package);
                    BuildGui.OutText.setText("<span style=\"font-size: 15pt\">Recieving a list of files...</span>");
          
                    HttpURLConnection updconn = (HttpURLConnection) url.openConnection();
                    File client = new File(MZLOptions.RootDir, MZLOptions.zip_package);
                    long cll_web = updconn.getContentLength();
                    
                    BuildGui.UpdBar.setMaximum((int) cll_web);
                    if (client.length() != cll_web && cll_web > 1) {}
                    
                    BufferedInputStream bis = new BufferedInputStream(updconn.getInputStream());
                    
                    BuildGui.OutText.setText("<span style=\"font-size: 15pt\">Testing paths...</span>");
                    
                    FileOutputStream fw = new FileOutputStream(client);
                    
                    BuildGui.OutText.setText("<span style=\"font-size: 15pt\">Loading selected files...</span>");

                    byte[] b = new byte[1024];
                    int count = 0;

                    BuildGui.OutText.setText("<span style=\"font-size: 15pt\">Saving client files to disc...</span>");
                    while ((count=bis.read(b)) != -1) {
                        fw.write(b,0,count);
                        BuildGui.UpdBar.setValue((int) client.length());
					BuildGui.UpdBar.setString("Downloading " + MZLOptions.zip_package + " ("
							+ (int) client.length() + " bytes of " + cll_web
							+ " bytes)");
                    }
                    fw.close();

                    BuildGui.OutText.setText("<span style=\"font-size: 15pt\">Unpacking client...</span>");
                    unzip();
                    BuildGui.OutText.setText("<span style=\"font-size: 15pt\">All Done! Have a nice day!</span>");
                    BuildGui.UpdBar.setString("Done!");
                    BuildGui.tabbedPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    
                } catch (IOException ex) {
                    BuildGui.OutText.setText("Error: " + ex.toString());
                }
            }
        }
                .start();
    }
  
    protected static void unzip() {
        String path = MZLOptions.RootDir;
        String ZFilePath = path + MZLOptions.zip_package;
        String ExPath = path;

        Vector zipEntries = new Vector();
        try
        {
            ZipFile zf = new ZipFile(ZFilePath);
            Enumeration en = zf.entries();

      while (en.hasMoreElements()) zipEntries.addElement((ZipEntry)en.nextElement());

      for (int i = 0; i < zipEntries.size(); i++)
      {
        ZipEntry ze = (ZipEntry)zipEntries.elementAt(i);
        extractFromZip(ZFilePath, ExPath, ze.getName(), zf, ze);
      }

      zf.close();
    }
    catch (Exception ex)
    {
      print(ex.toString());
    }
    new File(ZFilePath).delete();
  }

  static void extractFromZip(String szZipFilePath, String szExtractPath, String szName, ZipFile zf, ZipEntry ze)
  {
    if (ze.isDirectory()) return;

    String szDstName = slash2sep(szName);
    String szEntryDir;
    if (szDstName.lastIndexOf(File.separator) != -1) szEntryDir = szDstName.substring(0, szDstName.lastIndexOf(File.separator)); else {
      szEntryDir = "";
    }
   try
    {
            File newDir = new File((new StringBuilder(String.valueOf(szExtractPath))).append(File.separator).append(szEntryDir).toString());
            newDir.mkdirs();
            FileOutputStream fos = new FileOutputStream((new StringBuilder(String.valueOf(szExtractPath))).append(File.separator).append(szDstName).toString());
            InputStream is = zf.getInputStream(ze);
            byte buf[] = new byte[1024];
            do
            {
                int nLength;
                try
                {
                    nLength = is.read(buf);
                }
                catch(EOFException ex)
                {
                    break;
                }
                if(nLength < 0)
                    break;
                fos.write(buf, 0, nLength);
            } while(true);
            is.close();
            fos.close();

    }
    catch (Exception e)
    {
      print(e.toString());
    }
  }

  static String slash2sep(String src)
  {
    char[] chDst = new char[src.length()];

    for (int i = 0; i < src.length(); i++)
    {
      if (src.charAt(i) == '/') chDst[i] = File.separatorChar; else
        chDst[i] = src.charAt(i);
    }
    String dst = new String(chDst);
    return dst;
  }
  
  private static void print(String str) {
      Debug.Logger(str);
  }
}