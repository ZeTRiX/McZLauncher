package ru.zetrix.engine;

import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import ru.zetrix.settings.Debug;

/**
 *
 * @author ZeTRiX
 */
public class MCLauncher extends Applet implements Runnable, AppletStub, MouseListener {
	private static final long serialVersionUID = 1L;
	private Applet mcApplet = null;
	public Map<String, String> ClientData = new HashMap<String, String>();
	private int context = 0;
	private boolean active = false;
	private URL[] urls;
	private String bin;
	
	public MCLauncher(String bin, URL[] urls)
	{
		this.bin = bin;
		this.urls = urls;
	}

	public void init()
	{
		if (mcApplet != null)
		{
			mcApplet.init();
			return;
		}
		init(0);
	}
	
	public void init(int i)
	{
		URLClassLoader cl = new URLClassLoader(urls);
		System.setProperty("org.lwjgl.librarypath", bin + "natives");
		System.setProperty("net.java.games.input.librarypath", bin + "natives");
		try
		{
			Class <?> Mine = cl.loadClass("net.minecraft.client.MinecraftApplet");
			Applet applet = (Applet)Mine.newInstance();
			mcApplet = applet;
			applet.setStub(this);
			applet.setSize(getWidth(), getHeight());
			setLayout(new BorderLayout());
			add(applet, "Center");
			applet.init();
			active = true;
			validate();
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getParameter(String name)
	{
		String custom = (String)ClientData.get(name);
		if (custom != null) return custom;
		try
		{
			return super.getParameter(name);
		} catch(Exception e)
		{
			ClientData.put(name, null);
		}
		return null;
	}
	
	public void start()
	{
		if (mcApplet != null)
		{
			mcApplet.start();
			return;
		}
	}
	
	public boolean isActive()
	{
		if (context == 0)
		{
			context = -1;
			try
			{
				if (getAppletContext() != null)
					context = 1;
			} catch(Exception e){}
		}
		if (context == -1)
			return active;
		return super.isActive();
	}
	
	public URL getDocumentBase()
	{
		try
		{
			return new URL("http://zetlog.ru/");			
		} catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void stop()
	{
		if (mcApplet != null)
		{
			active = false;
			mcApplet.stop();
			return;
		}
	}

	public void destroy()
	{
		if (mcApplet != null)
		{
			mcApplet.destroy();
			return;
		}
	}
	
	public void mouseClicked(MouseEvent paramMouseEvent) {}
	public void mousePressed(MouseEvent paramMouseEvent) {}
	public void mouseReleased(MouseEvent paramMouseEvent){}
	public void mouseEntered(MouseEvent paramMouseEvent) {}
	public void mouseExited(MouseEvent paramMouseEvent) {}
	public void appletResize(int paramInt1, int paramInt2) {}
	public void run() {}
        
        private static void print(String str) {
            Debug.Logger(str);
        }
}
