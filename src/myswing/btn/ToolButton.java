package myswing.btn;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ToolButton extends JButton{
	private static final long serialVersionUID = 1L;

	protected String promissionKey = null;
	protected ActionListener ls = null;
	
	public ToolButton()
	{
		super();
	}
	
	public ToolButton(String txt)
	{
		super(txt);
		this.setToolTipText(txt);
	}
	
	public ToolButton(String txt, String promissionKey)
	{
		this(txt);
		this.promissionKey = promissionKey;
	}
	
	public ToolButton(String txt, ActionListener ls)
	{
		this(txt);
		super.addActionListener(ls);
		this.ls = ls;
	}
	
	public ToolButton(String txt, String promissionKey, ActionListener ls)
	{
		super(txt);
		super.addActionListener(ls);
		this.ls = ls;
		this.promissionKey = promissionKey;
	}

	public void addActionListener(ActionListener ls) {
		super.addActionListener(ls);
		this.ls = ls;
	}

	public String getPromissionKey() {
		return promissionKey;
	}

	public void setPromissionKey(String promissionKey) {
		this.promissionKey = promissionKey;
	}
	
	
}
