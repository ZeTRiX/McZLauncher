package ru.zetrix.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.net.URI;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import ru.zetrix.settings.Debug;

/**
 * @author ZeTRiX
 */

public class News extends JFrame
{
  private static String newsurl = "http://mcupdate.tumblr.com/";
  private static JScrollPane scrollPane;
  
  public News()
  {   
    add(NewsFrame(), "Center");
    setIconImage(ru.zetrix.settings.Util.getRes("fav.png"));
  }

  private static JScrollPane getUpdateNews()
  {
    if (scrollPane != null) return scrollPane;
    try
    {
      final JTextPane editorPane = new JTextPane()
      {
        private static final long serialVersionUID = 1L;
      };
      editorPane.setText("<html><b>Loading news page...</b></html>");
      editorPane.addHyperlinkListener(new HyperlinkListener() {
        public void hyperlinkUpdate(HyperlinkEvent he) {
          if (he.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
            try {
              openLink(he.getURL().toURI());
            } catch (Exception e) {
              e.printStackTrace();
            }
        }
      });
      new Thread() {
        public void run() {
          try {
    		  editorPane.setPage(new URL(newsurl));
          } catch (Exception e) {
            e.printStackTrace();
            editorPane.setText("<html><b>Error connecting to news server</b></html>");
          }
        }
      }
      .start();
      editorPane.setBackground(Color.DARK_GRAY);
      editorPane.setEditable(false);
      scrollPane = new JScrollPane(editorPane);
      scrollPane.setBorder(null);
      editorPane.setMargin(null);
    } catch (Exception e2) {
      e2.printStackTrace();
    }

    return scrollPane;
  }

  static JFrame NewsFrame() {
    JFrame NewsFr = new JFrame("Новости");
    NewsFr.setPreferredSize(new Dimension(1000,600));
    NewsFr.pack();
    NewsFr.setLocationRelativeTo(null);
    NewsFr.setVisible(true);
    NewsFr.add(getUpdateNews(), "Center");
    return NewsFr;
  }

  public void setNoNetwork() {
    removeAll();
    add(NewsFrame(), "Center");
    validate();
  }
  
    public static void openLink(URI uri) {
        try {
            print("Getting news page from " + newsurl);
            Object o = Class.forName("java.awt.Desktop").getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            o.getClass().getMethod("browse", new Class[] { URI.class }).invoke(o, new Object[] { uri });
        } catch (Throwable e) {
            print("Failed to open link " + uri.toString());
        }
    }
    
    private static void print(String str) {
        Debug.Logger(str);
    }
}
