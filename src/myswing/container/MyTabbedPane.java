package myswing.container;

import java.awt.Component;
import java.util.LinkedList;

import javax.swing.JTabbedPane;

public class MyTabbedPane extends JTabbedPane{
	private static final long serialVersionUID = 1L;
	
	protected LinkedList<String> keys = null;
	

	public MyTabbedPane()
	{
		super();
		
		keys = new LinkedList<String>();
	}
	
	public MyTabbedPane(int tabPlacement)
	{
		super(tabPlacement);
		keys = new LinkedList<String>();
	}
	
	public String getTabIdByIndex(int index)
	{
		return keys.get(index);
	}
	
	public int getIndexByTabId(String tid)
	{
		return keys.indexOf(tid);
	}
	
	public String getSelectTabId()
	{
		int index = this.getSelectedIndex();
		return this.getTabIdByIndex(index);
	}

	public void add(String tid, Component component, Object constraints, int index) {
		super.add(component, constraints, index);
		keys.add(index, tid);
	}

	public void remove(int index) {
		super.remove(index);
		keys.remove(index);
	}
	
	
}
