package eLuoSoftware;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class NotifyPop{

	ImageIcon icon;
	public NotifyPop(){
		try 
		{
			//work on size of image
			//outsource icon for multiple use?
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/eLuo.png"));
			Image newImg = image.getScaledInstance(60, 50, java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(newImg);
			//Display license agreement

		} catch(NullPointerException n) {
			System.out.println(n.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	public void License() {
		JOptionPane.showMessageDialog(null,  "  eLuo Software\u00A9 License\r\n|*=====================*|\r\n\r\nExcept where otherwise noted, all of the documentation and software included\r\nin the eLuo software is copyrighted by Nathaniel Kerr.\r\n\r\nThis software is provided \"as-is,\" without any express or implied warranty.\r\n In no event shall the author be held liable for any damages arising from the\r\n use of this software.\r\n\r\nPermission is granted to anyone to use this software for any purpose,\r\n including commercial use, and to redistribute it, provided that the \r\n following conditions are met:\r\n\r\n1. All redistributions of software files must retain all copyright\r\n   notices that are currently in place, and this list of conditions without\r\n   modification.\r\n\r\n2. All redistributions must retain all occurrences of the\r\n   copyright notices that are currently in\r\n   place.\r\n\r\n3. The origin of this software must not be misrepresented; outside parties and/or person(s) \r\n    may not make claims towards modification or improvement to the original software.\r\n\r\n4. By using this software you are in agreement to all of the conditions listed above.\r\n\r\nEnjoy!\r\n\r\nhttps://sites.google.com/view/eluo\r\nCopyright \u00A9 2018 eLuo Software. All rights reserved.", "License Agreement", JOptionPane.INFORMATION_MESSAGE, icon);
	}
	public void Send(String message) {
		JOptionPane.showMessageDialog(null, message, "Notice", JOptionPane.INFORMATION_MESSAGE, icon);
	}
}
