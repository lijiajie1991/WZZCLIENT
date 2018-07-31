package ui.menu;

import javax.swing.tree.DefaultMutableTreeNode;

import bas.sys.menu.MenuInfo;

public class MenuNode extends DefaultMutableTreeNode{
	private static final long serialVersionUID = 1L;
	
	protected MenuInfo menuInfo = null;
	
	public MenuNode(MenuInfo menuInfo)
	{
		super(menuInfo.getName());
		this.setMenuInfo(menuInfo);
	}

	public MenuInfo getMenuInfo() {
		return menuInfo;
	}

	public void setMenuInfo(MenuInfo menuInfo) {
		this.menuInfo = menuInfo;
	}
	
	public boolean isHasLeaf()
	{
		MenuInfo mInfo = this.getMenuInfo();
		String clsName = mInfo.getClsName();
		if(clsName != null && !"".equals(clsName))
			return true;
		
		int count = this.getChildCount();
		for(int i = 0; i < count; i++)
		{
			MenuNode child = (MenuNode) this.getChildAt(i);
			if(child.isHasLeaf())
				return true;
		}
		return false;
	}
}
