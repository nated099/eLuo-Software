package eLuoSoftware;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.DefaultCaret;


public class Smgr
{
	//enum flags for current page
	enum Page 
	{
		CLEANING, WINISS, ASSESS, PRIVACY, NETMON, ABOUT
	}
	Page task = Page.CLEANING;
	
	private StringBuilder cbId = new StringBuilder();
	private JFrame frameeLuo;
	private Thread cleaning;
	private String welcome;
	
	private JTextPane licenseTextPane;
	private JTextArea textArea;
	private JButton assAss; 
	private JButton assExp; 
	private JTextArea consoleBox;
	private JScrollPane scrollPane;
	private JProgressBar progressBar;
	private JProgressBar assProgBar; 
	private JCheckBox chkbxRst;
	private JCheckBox chkbxUndoRest;
	private JCheckBox chkbxWinPri; 
	private JCheckBox chkbxWinIss; 
	private JCheckBox chkbxDelBro; 
	private JCheckBox chkbxTU; 
	private JCheckBox chkbxRunVir; 
	private JCheckBox wCbGetStarted;
	private JCheckBox wCbGroove;
	private JCheckBox wCbSports;
	private JCheckBox wCbWeather;
	private JCheckBox wCbXbx;
	private JCheckBox wCbCortana;
	private JCheckBox wCbSkype;
	private JCheckBox wCbNews;
	private JCheckBox wCbOffice;
	private JCheckBox wCbWup;
	private JCheckBox wCbSolitaire;
	private JCheckBox wCbStore;
	private JCheckBox wCbUndo;
	private JCheckBox chkbxPTP;
	private JCheckBox chkbxWifiSense;
	private JCheckBox chkbxBSearch;
	private JCheckBox chkbxWWebCont;
	private JCheckBox chkbxWAdInf;
	private JCheckBox chkbxWSync;
	private JCheckBox chkbxWTelem;
	private JCheckBox chkbxSsd;
	private JButton btnCleaning; 
	private JButton btnWinFix;
	private JButton btnAssess;
	private JButton btnPrivacy; 
	private JButton btnNetmon;
	private JButton btnAbout; 
	private JButton btnExec;
	private JButton btnCancel;
	private JScrollPane licenseScrollPane;
	private JPanel licensePanel;
	private JPanel licButtonPanel;
	private JPanel licButtonPanel2;
	private JButton aBtnDonate;
	private JButton aBtnHelp;
	
	NotifyPop notice = new NotifyPop();
	Sourcemgr source = new Sourcemgr();
	private boolean admin = source.isAdmin();
	//private String dir = source.pwd();	//unused
	
	//Sets up the GUI variables
	protected Smgr() {
		initialize();
	}
	//Displays the GUI
	protected void Display() {
		frameeLuo.setVisible(true);
	}
	//gets the current directory that software is running from
	protected void initialize() 
	{
		if(admin == true) 
		{
			welcome = "*Administrative Privileges Detected*\r\nOS: "  + System.getProperty("os.name") + "\r\nVersion: " + System.getProperty("os.version") + "\r\nArchitecture: " + System.getProperty("os.arch") + "\r\n\r\nIt is recommended that you run a system restore before performing any task. This may be accomplished on the first (Cleaning) page.\r\n\r\n";
		}
		else
			welcome = "Administrative Privileges NOT Detected, no tasks will be run until this is run as admin. \r\n\r\nJust hit execute to startup with privileges!";
				
		
		frameeLuo = new JFrame();
		try 
		{
			frameeLuo.setIconImage(ImageIO.read(getClass().getResourceAsStream("/eLuo.png")));
		}
		catch (IOException e) {
			e.printStackTrace();
			System.err.println("internal resource acquisition failed");
		}
		
		frameeLuo.setResizable(false);
		frameeLuo.getContentPane().setBackground(SystemColor.desktop);
		frameeLuo.setTitle("eLuo Software\u00A9");
		frameeLuo.setBounds(100, 100, 652, 605);
		frameeLuo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameeLuo.getContentPane().setLayout(null);

		
		JPanel panelPages = new JPanel();		//Cardlayout for all main panels in window
		panelPages.setBorder(new BevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.BLACK, Color.BLACK));
		panelPages.setBounds(130, 0, 498, 569);
		frameeLuo.getContentPane().add(panelPages);
		panelPages.setLayout(new CardLayout(0, 0));
		
		JPanel cleaning = new JPanel();			//Until next panel all new objects will be within this cleaning panel
		cleaning.setBackground(Color.DARK_GRAY);
		panelPages.add(cleaning, "Cleaning");
		cleaning.setLayout(null);
		
		JPanel assessment = new JPanel();			//Next Primary panel
		assessment.setBackground(Color.DARK_GRAY);
		panelPages.add(assessment, "Assessment");
		assessment.setLayout(null);
		
		scrollPane = new JScrollPane(); //cleaning tab
		scrollPane.setBounds(12, 30, 474, 456);
		assessment.add(scrollPane);
		
		textArea = new JTextArea(); //cleaning tab
		textArea.setBackground(Color.BLACK);
		textArea.setEditable(false);
		textArea.setForeground(Color.GREEN);
		scrollPane.setViewportView(textArea);
		
		assProgBar = new JProgressBar();
		assProgBar.setForeground(Color.GREEN);
		assProgBar.setBounds(12, 497, 474, 23);
		assessment.add(assProgBar);
		
		JPanel assbtnpanel = new JPanel();
		assbtnpanel.setLayout(null);
		assbtnpanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(0, 0, 0), new Color(192, 192, 192), new Color(64, 64, 64)), new CompoundBorder(new LineBorder(new Color(160, 160, 160)), new EmptyBorder(2, 2, 2, 2))));
		assbtnpanel.setBackground(Color.BLACK);
		assbtnpanel.setBounds(130, 523, 230, 39);
		assessment.add(assbtnpanel);
		
		JPanel assbtnpanels = new JPanel();
		assbtnpanels.setLayout(null);
		assbtnpanels.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.LIGHT_GRAY, null, null));
		assbtnpanels.setBackground(Color.DARK_GRAY);
		assbtnpanels.setBounds(3, 3, 224, 33);
		assbtnpanel.add(assbtnpanels);
		
		JPanel Effect1 = new JPanel();
		Effect1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.LIGHT_GRAY));
		Effect1.setBackground(Color.GRAY);
		Effect1.setBounds(6, 7, 480, 551);
		cleaning.add(Effect1);
		Effect1.setLayout(null);
		
		JPanel privacy = new JPanel();
		privacy.setBackground(Color.DARK_GRAY);
		panelPages.add(privacy, "Privacy");
		privacy.setLayout(null);
		
		JPanel netmonitor = new JPanel();
		netmonitor.setBackground(Color.DARK_GRAY);
		panelPages.add(netmonitor, "netMon");
		netmonitor.setLayout(null);
		
		JPanel about = new JPanel();
		about.setBackground(Color.DARK_GRAY);
		panelPages.add(about, "about");
		about.setLayout(null);
		
		assAss = new JButton("Assess");
		assAss.setBounds(10, 5, 89, 23);
		assbtnpanels.add(assAss);
		
		assExp = new JButton("Export");
		assExp.setBounds(128, 5, 89, 23);
		assbtnpanels.add(assExp);
			
		licensePanel = new JPanel();
		licensePanel.setLayout(null);
		licensePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.LIGHT_GRAY));
		licensePanel.setBackground(Color.GRAY);
		licensePanel.setBounds(6, 7, 480, 551);
		about.add(licensePanel);
		
		licenseScrollPane = new JScrollPane();
		licenseScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		licenseScrollPane.setBounds(7, 7, 465, 490);
		licensePanel.add(licenseScrollPane);
		
		licenseTextPane = new JTextPane();
		DefaultCaret lCaret = (DefaultCaret)licenseTextPane.getCaret();
		lCaret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		licenseTextPane.setText(" eLuo Software\u00A9 License\r\n|*============*|\r\n\r\nExcept where otherwise noted, all of the documentation and software included\r\nin the eLuo software is copyrighted by Nathaniel Kerr.\r\n\r\nCopyright \u00A9 2018 Nathaniel Kerr. All rights reserved.\r\n\r\nThis software is provided \"as-is,\" without any express or implied warranty.\r\n In no event shall the author be held liable for any damages arising from the\r\n use of this software.\r\n\r\nPermission is granted to anyone to use this software for any purpose,\r\n including commercial use, and to redistribute it, provided that the \r\n following conditions are met:\r\n\r\n1. All redistributions of software files must retain all copyright\r\n   notices that are currently in place, and this list of conditions without\r\n   modification.\r\n\r\n2. All redistributions must retain all occurrences of the\r\n   copyright notices that are currently in\r\n   place.\r\n\r\n3. The origin of this software must not be misrepresented; outside parties and/or person(s) may not  make claims towards modification or improvement to the original software.\r\n\r\n4. By using this software you are in agreement to all of the conditions listed above.\r\n\r\n-Nathaniel Kerr\r\n\r\nhttps://sites.google.com/view/eluo\r\nEnjoy!");
		licenseScrollPane.setViewportView(licenseTextPane);
		licenseTextPane.setBackground(Color.LIGHT_GRAY);
		
		licButtonPanel = new JPanel();
		licButtonPanel.setLayout(null);
		licButtonPanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(0, 0, 0), new Color(192, 192, 192), new Color(64, 64, 64)), new CompoundBorder(new LineBorder(new Color(160, 160, 160)), new EmptyBorder(2, 2, 2, 2))));
		licButtonPanel.setBackground(Color.BLACK);
		licButtonPanel.setBounds(130, 505, 230, 39);
		licensePanel.add(licButtonPanel);
		
		licButtonPanel2 = new JPanel();
		licButtonPanel2.setLayout(null);
		licButtonPanel2.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.LIGHT_GRAY, null, null));
		licButtonPanel2.setBackground(Color.DARK_GRAY);
		licButtonPanel2.setBounds(3, 3, 224, 33);
		licButtonPanel.add(licButtonPanel2);
		
		JLayeredPane cwLayerPane = new JLayeredPane();
		cwLayerPane.setBounds(0, 0, 480, 551);
		Effect1.add(cwLayerPane);
		
		JPanel wILayerPanel = new JPanel();
		wILayerPanel.setBounds(0, 0, 480, 140);
		cwLayerPane.add(wILayerPanel);
		wILayerPanel.setLayout(null);
		wILayerPanel.setBorder(null);
		wILayerPanel.setBackground(Color.GRAY);
		wILayerPanel.setVisible(false);
		wILayerPanel.setOpaque(false);
		
		//TODO transfer cbox to all pages
		wCbGetStarted = new JCheckBox("Get Started");
		wCbGetStarted.setBackground(Color.GRAY);
		wCbGetStarted.setBounds(6, 111, 116, 23);
		wILayerPanel.add(wCbGetStarted);
		
		wCbGroove = new JCheckBox("Groove Music");
		wCbGroove.setBackground(Color.GRAY);
		wCbGroove.setBounds(6, 85, 116, 23);
		wILayerPanel.add(wCbGroove);
		
		wCbSports = new JCheckBox("Bing Sports");
		wCbSports.setBackground(Color.GRAY);
		wCbSports.setBounds(6, 59, 116, 23);
		wILayerPanel.add(wCbSports);
		
		wCbWeather = new JCheckBox("Bing Weather");
		wCbWeather.setBackground(Color.GRAY);
		wCbWeather.setBounds(6, 33, 116, 23);
		wILayerPanel.add(wCbWeather);
		
		wCbXbx = new JCheckBox("Xbox");
		wCbXbx.setBackground(Color.GRAY);
		wCbXbx.setBounds(6, 7, 116, 23);
		wILayerPanel.add(wCbXbx);
		
		wCbCortana = new JCheckBox("Cortana");
		//wCbCortana.setEnabled(false);
		wCbCortana.setBackground(Color.GRAY);
		wCbCortana.setBounds(124, 111, 116, 23);
		wILayerPanel.add(wCbCortana);
		
		wCbSkype = new JCheckBox("Skype");
		wCbSkype.setBackground(Color.GRAY);
		wCbSkype.setBounds(124, 33, 116, 23);
		wILayerPanel.add(wCbSkype);
		
		wCbNews = new JCheckBox("Bing News");
		wCbNews.setBackground(Color.GRAY);
		wCbNews.setBounds(124, 59, 116, 23);
		wILayerPanel.add(wCbNews);
		
		wCbOffice = new JCheckBox("Get Office");
		wCbOffice.setBackground(Color.GRAY);
		wCbOffice.setBounds(124, 85, 116, 23);
		wILayerPanel.add(wCbOffice);
		
		wCbWup = new JCheckBox("Windows Updates");
		wCbWup.setBackground(Color.GRAY);
		wCbWup.setBounds(240, 33, 232, 23);
		wILayerPanel.add(wCbWup);
		
		wCbSolitaire = new JCheckBox("Microsoft Solitaire Collection");
		wCbSolitaire.setBackground(Color.GRAY);
		wCbSolitaire.setBounds(240, 7, 234, 23);
		wILayerPanel.add(wCbSolitaire);
		
		wCbStore = new JCheckBox("Windows Store");
		wCbStore.setBackground(Color.GRAY);
		wCbStore.setBounds(124, 7, 116, 23);
		wILayerPanel.add(wCbStore);
		
		wCbUndo = new JCheckBox("Undo");
		wCbUndo.setBackground(Color.GRAY);
		wCbUndo.setBounds(240, 59, 116, 23);
		wILayerPanel.add(wCbUndo);
		
		wCbGetStarted.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		wCbGroove.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		wCbSports.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		wCbWeather.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		wCbXbx.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		wCbCortana.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		wCbSkype.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		wCbNews.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		wCbOffice.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		wCbSolitaire.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		wCbWup.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		wCbStore.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		wCbUndo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		JPanel cleanLayerPanel = new JPanel();
		cleanLayerPanel.setBounds(0, 0, 480, 140);
		cwLayerPane.add(cleanLayerPanel);
		cleanLayerPanel.setLayout(null);
		cleanLayerPanel.setOpaque(false);
		
		
		//TODO may remove this. Can possibly be used to perform ALL privacy actions. At this time not necessary.
		chkbxWinPri = new JCheckBox("Windows Privacy Mode");
		chkbxWinPri.setBounds(6, 111, 213, 23);
		cleanLayerPanel.add(chkbxWinPri);
		chkbxWinPri.setBackground(Color.GRAY);
		chkbxWinPri.setVisible(false);
		
		chkbxWinIss = new JCheckBox("Fix Known Windows 10 Issues");
		chkbxWinIss.setBounds(6, 85, 208, 23);
		cleanLayerPanel.add(chkbxWinIss);
		chkbxWinIss.setBackground(Color.GRAY);	
		chkbxWinIss.setEnabled(false);
		
		chkbxTU = new JCheckBox("Tune Up");
		chkbxTU.setBounds(6, 7, 173, 23);
		cleanLayerPanel.add(chkbxTU);
		chkbxTU.setBackground(Color.GRAY);
		
		chkbxDelBro = new JCheckBox("Delete Browser History");
		chkbxDelBro.setBounds(6, 59, 208, 23);
		cleanLayerPanel.add(chkbxDelBro);
		chkbxDelBro.setBackground(Color.GRAY);
		
		chkbxRunVir = new JCheckBox("Run Virus Scanners");
		chkbxRunVir.setBounds(6, 33, 213, 23);
		cleanLayerPanel.add(chkbxRunVir);
		chkbxRunVir.setBackground(Color.GRAY);
		chkbxRunVir.setEnabled(false);
		
		chkbxRst = new JCheckBox("Create Restore Point");
		chkbxRst.setBounds(6, 111, 213, 23);
		cleanLayerPanel.add(chkbxRst);
		chkbxRst.setBackground(Color.GRAY);
		
		chkbxUndoRest = new JCheckBox("Undo Last Operation");
		chkbxUndoRest.setBounds(246, 7, 213, 23);
		cleanLayerPanel.add(chkbxUndoRest);
		chkbxUndoRest.setBackground(Color.GRAY);
		
		JPanel cwBtnPanel = new JPanel();
		cwBtnPanel.setBounds(240, 97, 230, 39);
		cleanLayerPanel.add(cwBtnPanel);
		cwBtnPanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(0, 0, 0), new Color(192, 192, 192), new Color(64, 64, 64)), new CompoundBorder(new LineBorder(new Color(160, 160, 160)), new EmptyBorder(2, 2, 2, 2))));
		cwBtnPanel.setBackground(Color.BLACK);
		cwBtnPanel.setLayout(null);
		
		JPanel clnPanelBtnFrame = new JPanel();
		clnPanelBtnFrame.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.LIGHT_GRAY, null, null));
		clnPanelBtnFrame.setBounds(3, 3, 224, 33);
		cwBtnPanel.add(clnPanelBtnFrame);
		clnPanelBtnFrame.setBackground(Color.DARK_GRAY);
		clnPanelBtnFrame.setLayout(null);
		
		btnExec = new JButton("Execute");
		btnExec.setBounds(10, 5, 86, 23);
		clnPanelBtnFrame.add(btnExec);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(128, 5, 86, 23);
		clnPanelBtnFrame.add(btnCancel);
		
		JPanel markerPanel = new JPanel();
		markerPanel.setBounds(230, 14, 10, 10);
		cleanLayerPanel.add(markerPanel);
		markerPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
		markerPanel.setBackground(Color.RED);
		

		//TODO get these working so they can be enabled again 
		//chkbxUndoRest.setEnabled(false);
		chkbxDelBro.setEnabled(false);
		//chkbxWinIss.setEnabled(false);
		chkbxWinPri.setEnabled(false);
		
		JPanel privLayerPanel = new JPanel();
		privLayerPanel.setBounds(0, 0, 480, 140);
		cwLayerPane.add(privLayerPanel);
		privLayerPanel.setLayout(null);
		privLayerPanel.setBorder(null);
		privLayerPanel.setBackground(Color.GRAY);
		privLayerPanel.setVisible(false);
		privLayerPanel.setOpaque(false);
	
		btnExec.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnExecAction(arg0);
			}
		});
		
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnCancAction(arg0);
			}
		});
		
		chkbxRst.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if(chkbxRst.isSelected())
					notice.Send("Restore Points will be enabled upon execution, if not already\r\nPlease ensure disk space is not full before proceeding");
			}
		});
		
		chkbxUndoRest.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		chkbxTU.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		chkbxWinPri.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		chkbxWinIss.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		chkbxDelBro.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		chkbxRunVir.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				chkbxSel(arg0);
			}
		});
		
		
		chkbxWTelem = new JCheckBox("Windows Telemetry");
		chkbxWTelem.setBounds(6, 111, 213, 23);
		privLayerPanel.add(chkbxWTelem);
		chkbxWTelem.setBackground(Color.GRAY);
		
		chkbxWSync = new JCheckBox("Windows Sync");
		chkbxWSync.setBounds(6, 85, 208, 23);
		privLayerPanel.add(chkbxWSync);
		chkbxWSync.setBackground(Color.GRAY);	
		chkbxWSync.setEnabled(false);
		
		chkbxWAdInf = new JCheckBox("Advertising Information");
		chkbxWAdInf.setBounds(6, 7, 173, 23);
		privLayerPanel.add(chkbxWAdInf);
		chkbxWAdInf.setBackground(Color.GRAY);
		
		chkbxWWebCont = new JCheckBox("Web Content Evaluation");
		chkbxWWebCont.setBounds(6, 59, 208, 23);
		privLayerPanel.add(chkbxWWebCont);
		chkbxWWebCont.setBackground(Color.GRAY);
		
		chkbxSsd = new JCheckBox("Smart Screen Data");
		chkbxSsd.setBounds(246, 59, 213, 23);
		privLayerPanel.add(chkbxSsd);
		chkbxSsd.setBackground(Color.GRAY);
		
		chkbxBSearch = new JCheckBox("Bing Search");
		chkbxBSearch.setBounds(6, 33, 213, 23);
		privLayerPanel.add(chkbxBSearch);
		chkbxBSearch.setBackground(Color.GRAY);
		
		chkbxWifiSense = new JCheckBox("WiFi Hotspot Sense");
		chkbxWifiSense.setBounds(246, 7, 213, 23);
		privLayerPanel.add(chkbxWifiSense);
		chkbxWifiSense.setBackground(Color.GRAY);
		
		chkbxPTP = new JCheckBox("P2P Update Downloads");
		chkbxPTP.setBounds(246, 33, 213, 23);
		privLayerPanel.add(chkbxPTP);
		chkbxPTP.setBackground(Color.GRAY);
		
		CardLayout panelPgLayout = (CardLayout) panelPages.getLayout();
				
		btnCleaning = new JButton("Cleaning");
		btnCleaning.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				panelPgLayout.show(panelPages, "Cleaning");
				//Effect1.add(scrollpanel);
				cleanLayerPanel.setVisible(true);
				wILayerPanel.setVisible(false);
				privLayerPanel.setVisible(false);
				//TODO deselect all WinIssues items
				cleanLayerPanel.add(cwBtnPanel);
				
				
				task = Page.CLEANING;
				consoleBox.setText(welcome + "\r\nVarious maintenance tasks are available on this page");
				chkbxSel(arg0);
			}
		});
		btnCleaning.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnCleaning.setBounds(0, 0, 131, 23);
		frameeLuo.getContentPane().add(btnCleaning);

		btnWinFix = new JButton("Unwanted Apps");
		btnWinFix.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				//btnCancAction(arg0);
				panelPgLayout.show(panelPages, "Cleaning");
				cleanLayerPanel.setVisible(false);
				wILayerPanel.setVisible(true);
				privLayerPanel.setVisible(false);
				//privLayerPanel.remove(cwBtnPanel);
				//cleanLayerPanel.remove(cwBtnPanel);
				wILayerPanel.add(cwBtnPanel);
				
				task = Page.WINISS;
				consoleBox.setText(welcome);
				chkbxSel(arg0);
				consoleBox.append("Remove unwanted native windows applications here!\r\n");
			}
		});
		btnWinFix.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnWinFix.setBounds(0, 22, 131, 23);
		frameeLuo.getContentPane().add(btnWinFix);
		
		btnAssess = new JButton("Assessment");
		btnAssess.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				//panelPages.setLayer(assess, )
				panelPgLayout.show(panelPages, "Assessment");
			}
		});
		btnAssess.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnAssess.setBounds(0, 66, 131, 23);
		frameeLuo.getContentPane().add(btnAssess);
		//TODO un-disable assessment page when done
		btnAssess.setEnabled(false);
		
		
		btnPrivacy = new JButton("Privacy");
		btnPrivacy.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				cleanLayerPanel.setVisible(false);
				wILayerPanel.setVisible(false);
				privLayerPanel.setVisible(true);
				//wILayerPanel.remove(cwBtnPanel);
				//cleanLayerPanel.remove(cwBtnPanel);
				privLayerPanel.add(cwBtnPanel);
				
				task = Page.PRIVACY;
				consoleBox.setText(welcome);
				chkbxSel(arg0);
				consoleBox.append("Please Note: Settings altered here may revert back to their original state after updating Windows. They can be disabled again from this page of the app.\r\n");
			}
		});
		btnPrivacy.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnPrivacy.setBounds(0, 44, 131, 23);
		frameeLuo.getContentPane().add(btnPrivacy);
		btnPrivacy.setEnabled(true);
		
		//TODO get privacy and network monitor pages setup
		btnNetmon = new JButton("Network Monitor");
		btnNetmon.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				panelPgLayout.show(panelPages, "netMon");
			}
		});
		btnNetmon.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnNetmon.setBounds(0, 88, 131, 23);
		frameeLuo.getContentPane().add(btnNetmon);
		btnNetmon.setEnabled(false);
		
		btnAbout = new JButton("About");
		btnAbout.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnAbout.setBounds(0, 110, 131, 23);
		frameeLuo.getContentPane().add(btnAbout);
		btnAbout.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				panelPgLayout.show(panelPages, "about");
			}
		});
		
		consoleBox = new JTextArea();
		consoleBox.setForeground(Color.GREEN);
		consoleBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		consoleBox.setBackground(Color.BLACK);
		consoleBox.setBounds(21, 334, 63, 86);
		consoleBox.setLineWrap(true);
		consoleBox.setWrapStyleWord(true);
		frameeLuo.getContentPane().add(consoleBox);
		
		DefaultCaret caret = (DefaultCaret)consoleBox.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane scrollpanel = new JScrollPane(consoleBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpanel.setBounds(7, 150, 464, 358);
		Effect1.add(scrollpanel);
		consoleBox.append(welcome);
		
		if(admin == false) 
		{
			chkbxRst.setEnabled(false);
			chkbxRunVir.setEnabled(false);
			chkbxTU.setEnabled(false);
			chkbxUndoRest.setEnabled(false);
			chkbxDelBro.setEnabled(false);
			chkbxWinIss.setEnabled(false);
			chkbxWinPri.setEnabled(false);
			btnAssess.setEnabled(false);
			btnWinFix.setEnabled(false);
			btnPrivacy.setEnabled(false);
			btnNetmon.setEnabled(false);
		}
		
		progressBar = new JProgressBar();
		progressBar.setBounds(6, 517, 464, 23);
		Effect1.add(progressBar);
		progressBar.setForeground(Color.GREEN);
		
		aBtnDonate = new JButton("Donate");
		aBtnDonate.setBounds(10, 5, 86, 23);
		licButtonPanel2.add(aBtnDonate);
		
		aBtnHelp = new JButton("Help");
		aBtnHelp.setBounds(128, 5, 86, 23);
		licButtonPanel2.add(aBtnHelp);
		
		ImageIcon pic=null;
		try 
		{
			pic = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/eLuoside.png"))); 
		} catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		JLabel renoPic = new JLabel();
		renoPic.setBounds(0, 132, 131, 437);
		frameeLuo.getContentPane().add(renoPic);
		renoPic.setIcon(pic);
		
	}
	private void uncheck() {
		wCbGetStarted.setSelected(false);
		wCbGroove.setSelected(false);
		wCbSports.setSelected(false);
		wCbWeather.setSelected(false);
		wCbXbx.setSelected(false);
		wCbCortana.setSelected(false);
		wCbSkype.setSelected(false);
		wCbNews.setSelected(false);
		wCbOffice.setSelected(false);
		wCbSolitaire.setSelected(false);
		wCbWup.setSelected(false);
		wCbStore.setSelected(false);
		wCbUndo.setSelected(false);
		chkbxTU.setSelected(false);
		chkbxWinIss.setSelected(false);
		chkbxDelBro.setSelected(false);
		chkbxWinPri.setSelected(false);
		chkbxRunVir.setSelected(false);
		chkbxRst.setSelected(false);
		chkbxUndoRest.setSelected(false);
		chkbxPTP.setSelected(false);
		chkbxWifiSense.setSelected(false);
		chkbxBSearch.setSelected(false);
		chkbxWWebCont.setSelected(false);
		chkbxWAdInf.setSelected(false);
		chkbxWSync.setSelected(false);
		chkbxWTelem.setSelected(false);
		chkbxSsd.setSelected(false);
	}
	private ArrayList<String> isSelected() {
		ArrayList<String> list = new ArrayList<String>();
		switch(task) {
		case WINISS:
			if(wCbGetStarted.isSelected())  //1
			{
				list.add("wCbGetStarted");
			}
			if(wCbGroove.isSelected())//2
			{
				list.add("wCbGroove");
			}
			if(wCbSports.isSelected())//3
			{
				list.add("wCbSports");
			}
			
			if(wCbWeather.isSelected())//4
			{
				list.add("wCbWeather");
			}
			
			if(wCbXbx.isSelected())//5
			{
				list.add("wCbXbx");
			}
			
			if(wCbCortana.isSelected())//6
			{
				list.add("wCbCortana");
			}
			
			if(wCbSkype.isSelected())//7
			{
				list.add("wCbSkype");
			}
			
			if(wCbNews.isSelected())//8
			{
				list.add("wCbNews");
			}
			
			if(wCbOffice.isSelected())//9
			{
				list.add("wCbOffice");
			}
			
			if(wCbSolitaire.isSelected())//10
			{
				list.add("wCbSolitaire");
			}
			
			if(wCbStore.isSelected())//11
			{
				list.add("wCbStore");
			}
			if(wCbWup.isSelected()) //12
			{
				list.add("wCbWup");
			}
			if(wCbUndo.isSelected()) //13
			{
				list.add("wCbUndo");
			}
			break;
			
		case CLEANING:
			
			if(chkbxRst.isSelected()) {//1
				list.add("chkbxRst");
									}
			if(chkbxRunVir.isSelected()) { 
				list.add("chkbxRunVir");
			}
			if(chkbxWinIss.isSelected()) { 
				list.add("chkbxWinIss");
			}
			if(chkbxDelBro.isSelected()) { 
				list.add("chkbxDelBro");
			}
			if(chkbxWinPri.isSelected()) { 
				list.add("chkbxWinPri");
			}
			if(chkbxTU.isSelected()) { 
				list.add("chkbxTU");
			}
			if(chkbxUndoRest.isSelected()) {
				list.add("chkbxUndoRest");
			}
			break;
		case PRIVACY:
			if(chkbxPTP.isSelected()) { //1
				list.add("chkbxPTP");
			}
			if(chkbxWifiSense.isSelected()) { //2
				list.add("chkbxWifiSense");
			}
			if(chkbxBSearch.isSelected()) { //3
				list.add("chkbxBSearch");
			}
			if(chkbxWWebCont.isSelected()) { //4 
				list.add("chkbxWWebCont");
			}
			if(chkbxWAdInf.isSelected()) { //5
				list.add("chkbxWAdInf");
			}
			if(chkbxWSync.isSelected()) {//6
				list.add("chkbxWSync");
			}
			if(chkbxWTelem.isSelected()) {//7
				list.add("chkbxWTelem");
			}
			if(chkbxSsd.isSelected()) {//8
				list.add("chkbxSsd");
			}
			break;
		case ABOUT:
			break;
			
		case ASSESS:
			break;
			
		case NETMON:
			break;
		}
		return list;
	}
	private void btnCancAction(java.awt.event.ActionEvent evt) 
	{	//action when cancel occurs
		if(cleaning.isAlive()) {
			//cleaning.destroy();
			cleaning.interrupt();
			consoleBox.setText(welcome);
			progressBar.setValue(0);
		}
		//TODO add winIss.isAlive() { killer
		btnCleaning.setEnabled(true);
		btnAbout.setEnabled(true);
		btnWinFix.setEnabled(true);
		btnExec.setVisible(true);
		btnPrivacy.setEnabled(true);
		//Added to btnExecAction should fix appending too many options
		//cbId = new StringBuilder();
	}
	private void chkbxSel(java.awt.event.ActionEvent evt) 
	{
		/*
		 * Cleans up other pages (unchecks)
		 */
		
		switch(task) {
			case ABOUT:
				break;
				
			case ASSESS:
				break;
				
			case NETMON:
				break;
				
			case PRIVACY:
				
				wCbGetStarted.setSelected(false);
				wCbGroove.setSelected(false);
				wCbSports.setSelected(false);
				wCbWeather.setSelected(false);
				wCbXbx.setSelected(false);
				wCbCortana.setSelected(false);
				wCbSkype.setSelected(false);
				wCbNews.setSelected(false);
				wCbOffice.setSelected(false);
				wCbSolitaire.setSelected(false);
				wCbWup.setSelected(false);
				wCbStore.setSelected(false);
				wCbUndo.setSelected(false);
				chkbxTU.setSelected(false);
				chkbxWinIss.setSelected(false);
				chkbxDelBro.setSelected(false);
				chkbxWinPri.setSelected(false);
				chkbxRunVir.setSelected(false);
				chkbxRst.setSelected(false);
				chkbxUndoRest.setSelected(false);
				
				if(!(chkbxSsd.isSelected()) && !(chkbxPTP.isSelected()) && !(chkbxWTelem.isSelected()) && !(chkbxWifiSense.isSelected()) && !(chkbxBSearch.isSelected()) && !(chkbxWWebCont.isSelected()) && !(chkbxWAdInf.isSelected()) && !(chkbxWSync.isSelected())) 
				{
					consoleBox.setText(welcome);
					//erases the used buffer
					cbId.setLength(0);
				}
				
				break;
				
			case CLEANING:
								
				wCbGetStarted.setSelected(false);
				wCbGroove.setSelected(false);
				wCbSports.setSelected(false);
				wCbWeather.setSelected(false);
				wCbXbx.setSelected(false);
				wCbCortana.setSelected(false);
				wCbSkype.setSelected(false);
				wCbNews.setSelected(false);
				wCbOffice.setSelected(false);
				wCbSolitaire.setSelected(false);
				wCbStore.setSelected(false);
				wCbWup.setSelected(false);
				wCbUndo.setSelected(false);
				
				chkbxPTP.setSelected(false);
				chkbxWifiSense.setSelected(false);
				chkbxBSearch.setSelected(false);
				chkbxWWebCont.setSelected(false);
				chkbxWAdInf.setSelected(false);
				chkbxWSync.setSelected(false);
				chkbxWTelem.setSelected(false);
				chkbxSsd.setSelected(false);
				
				//when nothing is selected display welcome
				if(!(chkbxTU.isSelected()) && !(chkbxWinIss.isSelected()) && !(chkbxDelBro.isSelected()) && !(chkbxWinPri.isSelected()) && !(chkbxUndoRest.isSelected()) && !(chkbxRst.isSelected())) 
				{
					consoleBox.setText(welcome);
					//erases the used buffer
					cbId.setLength(0);
				}

				//Unselect all for undo system restore
				if(chkbxUndoRest.isSelected()) 
				{
					chkbxTU.setSelected(false);
					chkbxTU.setEnabled(false);
					chkbxWinIss.setSelected(false);
					chkbxWinIss.setEnabled(false);
					chkbxDelBro.setSelected(false);
					chkbxDelBro.setEnabled(false);
					chkbxWinPri.setSelected(false);
					chkbxWinPri.setEnabled(false);
					chkbxRunVir.setSelected(false);
					chkbxRunVir.setEnabled(false);
					chkbxRst.setSelected(false);
					chkbxRst.setEnabled(false);
					consoleBox.setText("Undo System Restore");
				}
				if(!(chkbxUndoRest.isSelected())) {
					chkbxTU.setEnabled(true);
					//chkbxWinIss.setEnabled(true);
					//chkbxDelBro.setEnabled(true);
					chkbxWinPri.setEnabled(true);
					//chkbxRunVir.setEnabled(true);
					chkbxRst.setEnabled(true);
				}
				
				//Auto-select system restore when critical processes are run
				if(chkbxTU.isSelected() || chkbxWinIss.isSelected() || chkbxWinPri.isSelected())
				{
					//chkbxRst.setSelected(true);
				}
				break;
				
			case WINISS:

				chkbxTU.setSelected(false);
				chkbxWinIss.setSelected(false);
				chkbxDelBro.setSelected(false);
				chkbxWinPri.setSelected(false);
				chkbxRunVir.setSelected(false);
				chkbxRst.setSelected(false);
				chkbxUndoRest.setSelected(false);
				
				chkbxPTP.setSelected(false);
				chkbxWifiSense.setSelected(false);
				chkbxBSearch.setSelected(false);
				chkbxWWebCont.setSelected(false);
				chkbxWAdInf.setSelected(false);
				chkbxWSync.setSelected(false);
				chkbxWTelem.setSelected(false);
				chkbxSsd.setSelected(false);
				
				//when nothing is selected display welcome
				if(!(wCbGetStarted.isSelected()) && !(wCbGroove.isSelected()) && !(wCbSports.isSelected()) && !(wCbWeather.isSelected()) && !(wCbXbx.isSelected()) && !(wCbCortana.isSelected()) && !(wCbSkype.isSelected()) && !(wCbNews.isSelected())	&& !(wCbOffice.isSelected()) && !(wCbSolitaire.isSelected()) && !(wCbWup.isSelected())&& !(wCbStore.isSelected()) && !(wCbUndo.isSelected())) 
				{
					consoleBox.setText(welcome);
					//erases the used buffer
					cbId.setLength(0);
				}
				else
				{
					
				}
				//Unselect all for undo system restore
				if(wCbUndo.isSelected()) 
				{
					wCbGetStarted.setSelected(false);
					wCbGetStarted.setEnabled(false);
					wCbGroove.setSelected(false);
					wCbGroove.setEnabled(false);
					wCbSports.setSelected(false);
					wCbSports.setEnabled(false);
					wCbWeather.setSelected(false);
					wCbWeather.setEnabled(false);
					wCbXbx.setSelected(false);
					wCbXbx.setEnabled(false);
					wCbCortana.setSelected(false);
					wCbCortana.setEnabled(false);
					wCbSkype.setSelected(false);
					wCbSkype.setEnabled(false);
					wCbNews.setSelected(false);
					wCbNews.setEnabled(false);
					wCbOffice.setSelected(false);
					wCbOffice.setEnabled(false);
					wCbSolitaire.setSelected(false);
					wCbSolitaire.setEnabled(false); 
					wCbWup.setSelected(false);
					wCbWup.setEnabled(false);
					wCbStore.setSelected(false);
					wCbStore.setEnabled(false);

					consoleBox.setText("Reinstalls All Default Windows Built-in Apps");
				}
				if(!(wCbUndo.isSelected())) 
				{
					wCbGetStarted.setEnabled(true);
					wCbGroove.setEnabled(true);
					wCbSports.setEnabled(true);
					wCbWeather.setEnabled(true);
					wCbXbx.setEnabled(true);
					wCbCortana.setEnabled(true);
					wCbSkype.setEnabled(true);
					wCbNews.setEnabled(true);
					wCbOffice.setEnabled(true);
					wCbSolitaire.setEnabled(true);
					wCbWup.setEnabled(true);
					wCbStore.setEnabled(true);
				}
				break;

		}
		
	}
	private void btnExecAction(java.awt.event.ActionEvent evt)
	{
		progressBar.setValue(0);
		consoleBox.setText(welcome);	
		cleaning = new Thread(t);
		t.task(isSelected());
		
		switch(task) {
			case WINISS:
	
				cleaning.setName("WinRmv");
				break;
				
			case CLEANING:
				cleaning.setName("Cleaning");
				break;
				
			case ABOUT:
				break;
				
			case ASSESS:
				break;
				
			case NETMON:
				break;
				
			case PRIVACY:
				cleaning.setName("Privacy");
				break;
		}
		cleaning.start();
	}
	Taskable t = new Taskable() 
	{	
		@Override
		public void run()
		{
			/*
			 * Thread Initialization tasks
			 */
			btnExec.setVisible(false);
			if(admin == false && !(batch.exists()))
			{
				Thread.currentThread().interrupt();
				//Goes to finally{}
			}
			
			else if(admin == false) 
			{
				commands.put(0, new String[] {"admin.bat"});
			}
			else if(tasks.size() == 0) 
			{
				consoleBox.append("Choose an option to continue\r\n");
				btnExec.setVisible(true);
			}
			if(x==0 && admin == true) 
			{
				progressString = welcome;
				writeLog();
			}
			prog = (float) 100 / runNum;
			
			//When the process starts, disable other buttons
			switch(task) {
				case CLEANING:
					btnAssess.setEnabled(false);
					btnAbout.setEnabled(false);
					btnWinFix.setEnabled(false);
					btnPrivacy.setEnabled(false);
					break;
				case WINISS:
					btnCleaning.setEnabled(false);
					btnAbout.setEnabled(false);
					btnAssess.setEnabled(false);
					btnPrivacy.setEnabled(false);
					break;
				case ABOUT:
					break;
				case ASSESS:
					break;
				case NETMON:
					break;
				case PRIVACY:
					btnCleaning.setEnabled(false);
					btnAbout.setEnabled(false);
					btnAssess.setEnabled(false);
					btnWinFix.setEnabled(false);					
					break;
			}
			
			try 
			{ 
			if(admin == true)
			if(tasks.get(i).equals("chkbxUndoRest"))
			{
				try (InputStream iS = this.cmd.Send("Get-ComputerRestorePoint"))
				{
					try (BufferedReader line = new BufferedReader(new InputStreamReader(iS)))
					{
						//TODO filter restore points to find single 
						while (!Thread.currentThread().isInterrupted()) 
						{
							if((restoreId = msgCntrl.Restore(line.readLine())) != 0) {
								System.out.println("InputStream Available Memory: " + iS.available()); 
								count++;
								progressString = msgCntrl.Ret()+ "\r\n Count: "+ count + " Prog#: " + prog + "restoreId " +restoreId+ "\r\n" + sdf.format(new Date());
								consoleBox.append(restoreId +" Found restore id! Restoring computer to this time.");
								
								this.cmd.Send("restore-computer -restorepoint " + restoreId);
								break;
							}
							else {
								System.out.println("InputStream Available Memory: " + iS.available()); 
							count++;
							progressString = msgCntrl.Ret()+ "\r\n Count: "+ count + " Prog#: " + prog + "\r\n" + sdf.format(new Date());
							}
							writeLog();
						}
						
						iS.close();
						line.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (NullPointerException n1) {
					n1.printStackTrace();
					consoleBox.append("Restore point was not created or was removed.");
				}
			}
				
				for(x = 0; x < commands.size(); x++) 
				{	//each time line.readline() is used it runs a line through, even in a conditional statement or debug expression
					//commands is a hashtable that returns a string array of tasks to run
					for(int z = 0; z < commands.get(x).length; z++)
					try (InputStream iS = this.cmd.Send(commands.get(x)[z]))
					//InputStream iS = this.commands[0] == "admin.bat" ? this.AdminUp() : (this.cmd.Send(this.commands[x]))
					{
						try (BufferedReader line = new BufferedReader(new InputStreamReader(iS)))
						{
							//while loop runs until each command is finished
							while (!Thread.currentThread().isInterrupted()) 
							{
								msgCntrl.Checker(line.readLine(), x, commands.get(x)[z]);
								System.out.println("InputStream Available Memory: " + iS.available());  //TODO logwriter here instead use lambda in string
								count++;
								progressString = commands.get(x)[z] + " " + msgCntrl.Ret()+ "\r\n Count: "+ count + " Prog#: " + prog + "\r\n" + sdf.format(new Date());;
								
								if (msgCntrl.Ret() != null && msgCntrl.Ret() != "break") 
								{
									if(commands.get(x)[z].indexOf("typeperf") !=-1) 
									{
										if(!(analysis.Build(msgCntrl.Ret()).equals(msgCntrl.Ret()))) {
											consoleBox.append(analysis.Build(msgCntrl.Ret()) + "\r\n");
											writeLog();
										}
										
									}
									else {
										writeLog();
										consoleBox.append(msgCntrl.Ret() + "\r\n");
							    		System.out.println("Progress: " + progressString); //TODO create progressString tasks
										}
							    }
									
							    else if(msgCntrl.Ret() == null)
							    {
							    	writeLog();
							    	System.out.println("null was returned");
							        if(iS.available() == -1) {
							        	break;
							        }
							    }
							    else if(msgCntrl.Ret().equalsIgnoreCase("break")) {
							    	writeLog();
							    	break;
							    }
							    
								progressBar.setValue((int) (count * prog));
								System.out.println(count * prog + "%\r\n");
							}
							//moved progress bar up a bracket due to new system
							line.close();
						}
						catch (IOException e) 
						{
							if(admin == true) {
								writeLog(e.toString());
							}
							e.printStackTrace();
							System.out.println("IOERR: at BufferedReader/Inputstreamreader: " +e.toString()+ "\r\n");
							progressBar.setValue((int) (count * prog));
							count++;
							
						}
						finally 
						{
							try
							{
								iS.close();
								
								continue;
							}
							catch(IOException e)
							{
								if(admin == true) {
									writeLog(e.toString());
								}
								consoleBox.append("Closing stream issue: " + e.toString() + "\r\n");
							}
						}
						
					} catch (IOException e) 
					{
						if(admin == true) 
						{
							writeLog(e.toString());
						}
						e.printStackTrace();
						consoleBox.append("IOException: at inputstream: " +e.toString()+"\r\n");
					}
					
				} 
			}
			finally
			{
				
				try 
				{	this.cmd.close(); 	}	
				catch (IOException e) 
				{	consoleBox.append("Issue closing process: " + e.toString() + "\r\n"); writeLog(e.toString());	}
				
				/*
				 * Conditional actions if running as admin w/o batch file to run as admin
				 */
				if(admin == false && !(batch.exists())) 
				{
					//consoleBox.append("\r\n"+batch.getPath() + ": Has been moved or doesn't exist. Cannot run until this has been remedied.\r\n");
					if(Admin.MakeAdmin()) {
						ActionEvent arg0 = null;
						btnExecAction(arg0);
					}
						
				}
				else if(admin == false) 
				{
					System.exit(0);
				}
				else if(admin == true) 
				{
					//btnAssess.setEnabled(true);
					btnWinFix.setEnabled(true);
					btnCleaning.setEnabled(true);
					btnAbout.setEnabled(true);
					btnExec.setEnabled(true);
					btnPrivacy.setEnabled(true);
				}
				
				//TODO post finalization message for important topics, things discovered etc.
				
				/*if(!(msgCntrl.results().isEmpty()) || msgCntrl.results() != null) {
					consoleBox.append("Running errors: \r\n"+msgCntrl.results());
				}*/
				if(x<(i-1))
				{				
					consoleBox.append("It appears we had to terminate early, "+ x +" instead of "+i+". To help improve the software, email the logfile in \"C:\\elog (date).txt\" to eluosoftware@gmail.com\r\n");
					writeLog("It appears we had to terminate early, "+ x +" instead of "+i+". To help improve the software, email the logfile in \"C:\\elog (date).txt\" to eluosoftware@gmail.com\r\n");
				}
				else if(!(btnExec.isVisible()) && admin == true)
						consoleBox.append("\r\nFinished!\r\n");
				
				//TODO ensure analysis class functionality
				/*if(analysis.Build() == true)
				{
					consoleBox.append(analysis.typeperf());
				}*/
				
				progressBar.setValue(100);
				uncheck();
				btnExec.setVisible(true);
				this.taskWipe();
 			}
		}
	};
}
