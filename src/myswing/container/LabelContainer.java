package myswing.container;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import myswing.editor.FormatInputField;

public class LabelContainer extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private MyLabel lab = null;
	
	public LabelContainer(Component cmp, int labLen, String dir,  boolean isUnderLine, String labTxt)
	{
		Font font = new Font("宋体",Font.PLAIN, 15);
		lab = new MyLabel(labTxt);
		lab.setUnderLine(isUnderLine);
		lab.setFont(font);
		
		lab.setVerticalAlignment(JLabel.BOTTOM);
		
		this.setLayout(new BorderLayout());
		lab.setPreferredSize(new Dimension(labLen, getHeight()));
		
		if(cmp instanceof FormatInputField)
		{
			((FormatInputField)cmp).setName(labTxt);
		}
		
		this.add(lab, dir);
		this.add(cmp, BorderLayout.CENTER);
	}
	
	public void setFont(Font font)
	{
		super.setFont(font);
		if(lab != null)
			lab.setFont(font);
	}
	
	public void setLabelText(String txt)
	{
		lab.setText(txt);
	}
}
