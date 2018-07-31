package ui.base;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import common.util.PermissionUtil;
import myswing.btn.ToolButton;

public abstract class BaseTabUI extends JPanel{
	private static final long serialVersionUID = 1L;
	
	protected LinkedList<ToolButton> btnList = null;
	protected JToolBar toolBar = null;
	
	protected ActionListener quitLs = null;
	protected JFrame ower = null;
	protected String name = null;
	
	public BaseTabUI()
	{
		this("");
	}
	
	public BaseTabUI(String name)
	{
		this.name = name;
		btnList = new LinkedList<ToolButton>();
	}

	public JFrame getOwer() {
		return ower;
	}

	public void setOwer(JFrame ower) {
		this.ower = ower;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void initUI(HashMap<String, Object> ctx) throws Exception
	{
		this.setLayout(new BorderLayout());
		this.setVisible(true);
	}
	
	public void initToolBar() throws Exception
	{
		
	}
	
	public void createToolBar() throws Exception
	{
		HashSet<String> permissionSet = PermissionUtil.getBtnPermission(this.getInfoClass().getName());
		
		int size = btnList.size();
		if(size > 0)
		{
			toolBar = new JToolBar(); 
			for(int i = 0; i < size; i++)
			{
				ToolButton btn = btnList.get(i);
				String key = btn.getPromissionKey();
				key = key != null ? key : "";
				boolean isEnable = "".equals(key) || PermissionUtil.isSuperUser() || permissionSet.contains(key);
				btn.setEnabled(isEnable);
				toolBar.add(btn);
			}
			
			toolBar.setFloatable(false);
			this.add(toolBar, BorderLayout.PAGE_START);
		}
	}
	
	
	public void setQuitListener(ActionListener ls)
	{
		this.quitLs = ls;
	}
	
	public abstract void addListener() throws Exception;
	
	protected abstract Class<?> getInfoClass();

	public abstract boolean isCanOpen();
}
