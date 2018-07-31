package ui.menu;

import javax.swing.JMenuItem;

import bean.Info;

public class MenuItem extends JMenuItem{
	private static final long serialVersionUID = 1L;
	
	public Info info = null;
	
	public MenuItem(String name, Info info)
	{
		super(name);
		this.info = info;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}
	
	
}
