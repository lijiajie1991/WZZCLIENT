package exp;

import javax.swing.JFrame;

import myswing.msgbox.MsgBox;
import myswing.msgbox.MsgBoxUI;

public class ExpHandle {
	public ExpHandle(JFrame ower, Exception e)
	{
		if(e instanceof BizException)
		{
			MsgBox.showInfo(ower, e.getMessage());
		}
		else
		{
			new MsgBoxUI(ower, e);
		}
	}
}
