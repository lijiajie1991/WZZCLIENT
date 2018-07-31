package myswing.container;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabelContainer2 extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private MyLabel leftLab = null;
	private MyLabel rightLab = null;
	
	public LabelContainer2(Component cmp, boolean isUnderLine, int leftLen, String leftTxt, int rightLen, String rightTxt)
	{
		Font font = new Font("宋体",Font.PLAIN, 15);
		leftLab = new MyLabel(leftTxt);
		leftLab.setUnderLine(isUnderLine);
		leftLab.setFont(font);
		leftLab.setVerticalAlignment(JLabel.BOTTOM);
		
		rightLab = new MyLabel(rightTxt);
		rightLab.setUnderLine(isUnderLine);
		rightLab.setFont(font);
		rightLab.setVerticalAlignment(JLabel.BOTTOM);
		
		this.setLayout(new BorderLayout());
		leftLab.setPreferredSize(new Dimension(leftLen, getHeight()));
		rightLab.setPreferredSize(new Dimension(rightLen, getHeight()));
		
		this.add(leftLab, BorderLayout.WEST);
		this.add(rightLab, BorderLayout.EAST);
		this.add(cmp, BorderLayout.CENTER);
	}
	
	public void setFont(Font font)
	{
		super.setFont(font);
		if(leftLab != null)
			leftLab.setFont(font);
		
		if(rightLab != null)
			rightLab.setFont(font);
	}
}
