//=========================================================================
// Данный класс отвечает за загрузку файлов.
// Не советую изменять этот класс без знания JAVA. Для настройки лаунчера
// используйте класс "Config".
//=========================================================================

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.SwingWorker;

public class Downloads extends SwingWorker<Integer, Object> {

	public int doSomeWork() {
		// ===============================================================================================
		// Изменяем GUI перед загрузкой
		TXDGui.jtxt2.setVisible(true);
		TXDGui.jtxt2.setText(Config.msg8);
		TXDGui.prb1.setStringPainted(true);
		TXDGui.prb1.setMinimum(0);
		TXDGui.prb1.setMaximum(100);
		TXDGui.prb1.setValue(0);
		TXDGui.prb1.setBounds(40, 450, 770, 20);
		TXDGui.prb1.setString(Config.msg9);
		TXDGui.prb1.setVisible(true);
		TXDGui.but1.setVisible(false);
		TXDGui.but2.setVisible(false);
		TXDGui.but3.setVisible(false);
		TXDGui.but4.setVisible(false);
		TXDGui.jtx1.setVisible(false);

		// ===============================================================================================
		// Создание директории игры
		File folder1 = new File(Config.txtd11);
		folder1.mkdir();

		// ===========================================================================
		// Метод загрузки client.zip

		try {
			URL url = new URL(Config.txtd13);
			File f1 = new File(Config.txtd11 + "client.zip");
			URLConnection connection = url.openConnection();
			long down = connection.getContentLength();
			long downm = f1.length();
			TXDGui.prb1.setMaximum((int) down);
			TXDGui.jtxt1.setText(Config.msg10);
			if (downm != down && down > 1) {
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				BufferedInputStream bis = new BufferedInputStream(
						conn.getInputStream());
				FileOutputStream fw = new FileOutputStream(f1);
				byte[] b = new byte[1024];
				int count = 0;
				while ((count = bis.read(b)) != -1) {
					fw.write(b, 0, count);
					TXDGui.prb1.setValue((int) f1.length());
					TXDGui.prb1.setString(Config.msg11 + "("
							+ (int) f1.length() + " байт / " + down
							+ " байт  )");
				}
				fw.close();
			}
		} catch (Exception ex) {}
		// ===========================================================================
		// Метод распаковки client.zip
		try {
			File f1 = new File(Config.txtd11 + "client.zip");

			if (f1.length() > 1) {
				TXDGui.jtxt1.setText(Config.msg14);
				String path = Config.txtd11 + "client.zip";
				String dir_to = Config.txtd11;
				ZipFile zip = new ZipFile(path);
				Enumeration<?> entries = zip.entries();
				LinkedList<ZipEntry> zfiles = new LinkedList<ZipEntry>();
				while (entries.hasMoreElements()) {
					ZipEntry entry = (ZipEntry) entries.nextElement();
					if (entry.isDirectory()) {
						new File(dir_to + "/" + entry.getName()).mkdir();
					} else {
						zfiles.add(entry);
					}
				}
				for (ZipEntry entry : zfiles) {
					InputStream in = zip.getInputStream(entry);
					OutputStream out = new FileOutputStream(dir_to + "/" + entry.getName());
					byte[] buffer = new byte[1024];
					int len;
					while ((len = in.read(buffer)) >= 0)
						out.write(buffer, 0, len);
					in.close();
					out.close();
				}
				zip.close();
				TXDGui.jtxt1.setText(Config.msg12);
				TXDGui.jtxt2.setText(Config.msg13);
				TXDGui.prb1.setVisible(false);
				TXDGui.but1.setVisible(true);
				TXDGui.but3.setVisible(true);
				TXDGui.jtx1.setVisible(true);
				return 1;
			} else {
				TXDGui.jtxt1.setText(Config.msg15);
				TXDGui.jtxt2.setText(Config.msg16);
				TXDGui.prb1.setVisible(false);
				TXDGui.but1.setVisible(true);
				TXDGui.but3.setVisible(true);
				TXDGui.jtx1.setVisible(true);
				return 1;
			}
		} catch (Exception ex) {
			TXDGui.jtxt1.setText(Config.msg15);
			TXDGui.jtxt2.setText(Config.msg16);
			TXDGui.prb1.setVisible(false);
			TXDGui.but1.setVisible(true);
			TXDGui.but3.setVisible(true);
			TXDGui.jtx1.setVisible(true);
			return 1;	
		}

	
	}

	@Override
	protected Integer doInBackground() {
		return new Integer(doSomeWork());
	}
}
