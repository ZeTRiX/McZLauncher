package ru.zetrix.engine;

import java.awt.Cursor;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import ru.zetrix.settings.Debug;

public class Updater {
    private static String zip_package = "mclient.zip";
    public static final boolean debug = true;
    private static String RootDir = ru.zetrix.settings.Util.getWorkPath("MZL").getAbsolutePath() + File.separator;
    public static void Update() {
        new Thread() {
            @Override
            public void run() {
                try {
                    BuildGui.tabbedPane.remove(1);                    
                    BuildGui.OutText.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BuildGui.tabbedPane.remove(0);
                    BuildGui.OutText.setText("Connecting to Download Server...");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException exeption) {
                        print(exeption.toString());
                    }
                    
                    URL url = new URL("http://test.zetlog.ru/launchertest/" + zip_package);
                    BuildGui.OutText.setText("Recieving a list of files...");
          
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    
                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                    
                    BuildGui.OutText.setText("Testing paths...");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException exeption) {
                        print(exeption.toString());
                    }
                    
                    File f1 = new File(RootDir, zip_package);
                    FileOutputStream fw = new FileOutputStream(f1);
                    
                    BuildGui.OutText.setText("Loading selected files...");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException exeption) {
                        print(exeption.toString());
                    }
                    
                    byte[] b = new byte[1024];
                    int count = 0;
                    
                    BuildGui.OutText.setText("Saving files to disc...");
                    while ((count=bis.read(b)) != -1) 
                        fw.write(b,0,count);
                    fw.close();
              
                    BuildGui.OutText.setText("Unpacking client...");
                    unzip();
                    BuildGui.OutText.setText("All Done! Have a nice day!");
                    BuildGui.OutText.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    
                } catch (IOException ex) {
                    BuildGui.OutText.setText("Error: " + ex.toString());
                }
            }
        }
                .start();
    }
  
    protected static void unzip() {
        String path = ru.zetrix.settings.Util.getWorkPath("MZL").getAbsolutePath() + File.separator;
        String ZFilePath = path + "mclient.zip";
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
