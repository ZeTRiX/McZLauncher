package ru.zetrix.engine;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import ru.zetrix.settings.Debug;
import ru.zetrix.settings.MZLOptions;
import ru.zetrix.settings.Util;

/**
 * @author ZeTRiX
 */
public class BuildGui extends JFrame {
    public static String[] clientinf;
    public static String[] hashes;
    public static boolean AuthOk = false;
    public static BuildGui buildgui;
    public static MCStart MineStart;
    public static Updater Update;
    private String RootDir = ru.zetrix.settings.Util.getWorkingDirectory().getAbsolutePath() + File.separator;
    public JButton loginb = new JButton("Login", (new ImageIcon(ru.zetrix.settings.Util.getRes("lkey.png"))));
    public JButton openNews = new JButton("News", (new ImageIcon(ru.zetrix.settings.Util.getRes("news.png"))));
    public JButton Options = new JButton("Options", (new ImageIcon(ru.zetrix.settings.Util.getRes("opt.png"))));
    public static JLabel UserText = new JLabel("User:");
    public static JTextField UserName = new JTextField(20);
    public static JLabel PassText = new JLabel("Password:");
    public static JPasswordField Password = new JPasswordField(20);
    public JCheckBox SaveData = new JCheckBox("Remember me", false);
    public JCheckBox update = new JCheckBox("Update", false);
    public static JTextPane OutText;
    
    public static JTabbedPane tabbedPane;
    public static Box MainBox;
    public static JPanel optpane;
    public static JPanel updpane;

    String[] modelem = new String[] {"Online", "Offline"};
    private JComboBox mode = new JComboBox(modelem);

    String[] memoryset = new String[] {"1GB", "2GB", "4GB", "8GB", "16GB"};
    private JComboBox memory = new JComboBox(memoryset);

    public BuildGui() {
        setTitle(MZLOptions.LauncherTitle);
        setIconImage(ru.zetrix.settings.Util.getRes("fav.png"));
        setBackground(Color.BLACK);
        this.setBounds(200, 200, 460, 200);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final ImageIcon icon = new ImageIcon(BuildGui.class.getResource("/ru/zetrix/res/bg.png"));
        tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT) {
            protected void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0,0, null);
                super.paintComponent(g);
            }
        };

        Box box1 = Box.createHorizontalBox();
        box1.add(UserText);
        box1.add(Box.createHorizontalStrut(6));
        box1.add(UserName);
        Box box2 = Box.createHorizontalBox();
        box2.add(PassText);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(Password);
        Box box3 = Box.createHorizontalBox();
        box3.add(Box.createHorizontalGlue());
        box3.add(update);
        box3.add(Box.createHorizontalStrut(12));
        box3.add(SaveData);
        box3.add(Box.createHorizontalStrut(10));
        mode.setSelectedIndex(0);
        box3.add(mode);
        box3.add(Box.createHorizontalStrut(10));
        box3.add(loginb);
        UserText.setPreferredSize(PassText.getPreferredSize());
        MainBox = Box.createVerticalBox();
        MainBox.setBorder(new EmptyBorder(12,12,12,12));
        MainBox.add(box1);
        MainBox.add(Box.createVerticalStrut(12));
        MainBox.add(box2);
        MainBox.add(Box.createVerticalStrut(17));
        MainBox.add(box3);
        tabbedPane.add("Login", MainBox);

        JPanel mempane = new JPanel();
        mempane.add(memory);
        JPanel npane = new JPanel();
        npane.add(Options, BorderLayout.CENTER);
        npane.add(openNews, BorderLayout.CENTER);
        optpane = new JPanel();
        optpane.add(mempane);
        optpane.add(npane);
        tabbedPane.add("Options", optpane);
        
        OutText = new JTextPane() {
            private static final long serialVersionUID = 1L;
        };
        OutText.setContentType("text/html");
        OutText.setText("<span style=\"font-size: 15pt\">Update Info Will Be Here</span>");
        OutText.setEditable(false);
        JPanel updoutpane = new JPanel();
        updoutpane.add(OutText);
        updpane = new JPanel();
        updpane.add(updoutpane);
        tabbedPane.add("Update", updpane);
        
        this.getContentPane().add(tabbedPane);
        
        
        loginb.addActionListener(new ButtonEventListener());
        this.openNews.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ru.zetrix.gui.News().setVisible(true);
            }
        });
        this.Options.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ru.zetrix.gui.Options().setVisible(true);
            }
        });
        this.SaveData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (SaveData.isSelected()) {
                    Util.setProperty("password", new String(BuildGui.Password.getPassword()));
                }
            }
        });
    }
    
    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (mode.getSelectedItem().equals("Online") && (UserName.getText().length() > 2) && (Password.getPassword().length > 1)) {
                
                Util.setProperty("login", BuildGui.UserName.getText());
                Auther.Authorize(BuildGui.UserName.getText(), new String(BuildGui.Password.getPassword()));
            
//            if (AuthOk = true) {
                print("Searching for client files...");
                File[] client = new File[4];
                client[0] = new File(RootDir + "bin" + File.separator +"minecraft.jar");
                client[1] = new File(RootDir + "bin" + File.separator + "lwjgl.jar");
                client[2] = new File(RootDir + "bin" + File.separator + "jinput.jar");
                client[3] = new File(RootDir + "bin" + File.separator + "lwjgl_util.jar");

                if ((update.isSelected()) || (!client[0].exists()) || (!client[1].exists()) || (!client[2].exists()) || (!client[3].exists())) {
                    print("One or more files not found. \n Starting Update!");
//                    Update = new Updater();
//                    Update.execute();
                    Updater.Update();
                    MineStart = new MCStart(UserName.getText(), clientinf[1].trim());
                    setVisible(false);
                } else {
                    print("Client files succesfully detected!");
                    MineStart = new MCStart(UserName.getText(), clientinf[1].trim());
                    setVisible(false);
                }
                print("___");
                print(hashes[0].trim());
                print(hashes[1].trim());
                print(clientinf[0].trim());
                print(clientinf[1].trim());
                print("___");

                
//            } else {
//                AuthOk = false;
//            }
        
        } else if(UserName.getText().length() < 2) {
            javax.swing.JOptionPane.showMessageDialog((java.awt.Component)
                    null,
                    "You forget to enter a username or it's not valid \n Please, next time, do not forget to enter a valid one. \n",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        } else {
                print("Switching to offline mode...");
                File[] client = new File[4];
                client[0] = new File(RootDir + "bin" + File.separator + "minecraft.jar");
                client[1] = new File(RootDir + "bin" + File.separator + "lwjgl.jar");
                client[2] = new File(RootDir + "bin" + File.separator + "jinput.jar");
                client[3] = new File(RootDir + "bin" + File.separator + "lwjgl_util.jar");

                print("Searching for client files...");
                if ((!client[0].exists()) || (!client[1].exists()) || (!client[2].exists()) || (!client[3].exists())) {
                    print("One or more files nor found. \n Nothing can be done in Offline Mode!");
                    
                    JOptionPane.showMessageDialog(null,
                            "One or more game files nor found! \n Can't do anything, case of offline mode. \n You must be in Online mode to download client files.",
                            "Offline mode Error",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    print("Client files succesfully detected!");
                    print("Running game in offline mode!");
                    
                    MineStart = new MCStart(UserName.getText(), "");
                    setVisible(false);
                }
                
//            javax.swing.JOptionPane.showMessageDialog((java.awt.Component)
//                    null,
//                    "No games avaliable in offline mode \n Close this window, nigga! \n Understand me? \n",
//                    "Warning",
//                    JOptionPane.INFORMATION_MESSAGE);
        }                    
    }
}
  
	public static void main(String[] args) {
                        try {
//                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                    UIManager.setLookAndFeel(info.getClassName());
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            print("Error: " + e);
                        }
                        buildgui = new BuildGui();
                        buildgui.show();

	}
        
        private static void print(String str) {
            Debug.Logger(str);
        }
}
