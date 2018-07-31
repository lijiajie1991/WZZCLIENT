package start;

import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel;

import common.util.CacheUtil;
import ui.LoginUI;

public class Wuzuzu {
	public static void main(String[] args)
	{
		File temp = new File("startup_temp.jar");
		if(temp.exists())
		{
			File f = new File("startup.jar");
			f.delete();
			temp.renameTo(new File("startup.jar"));
		}
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);

				try {
					UIManager.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel());
					UIManager.put("Tree.font", new java.awt.Font("宋体", Font.PLAIN, 16));
					UIManager.put("TabbedPane.font", new java.awt.Font("宋体", Font.PLAIN, 16));
					CacheUtil.loadCache();
					new LoginUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
