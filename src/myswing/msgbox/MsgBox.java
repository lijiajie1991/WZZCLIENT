package myswing.msgbox;

import java.awt.Component;

import javax.swing.JOptionPane;

public class MsgBox {
	
	public static void showInfo(Component ower, String msg)
	{
		JOptionPane.showMessageDialog(ower, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static int showConfirm(Component ower, String msg)
	{
		return JOptionPane.showConfirmDialog(ower, msg, "提示", JOptionPane.YES_NO_OPTION);
	}
	
	public static int showConfirm(Component ower, String tip,  String msg)
	{
		return JOptionPane.showConfirmDialog(ower, msg, tip, JOptionPane.YES_NO_OPTION);
	}
}
