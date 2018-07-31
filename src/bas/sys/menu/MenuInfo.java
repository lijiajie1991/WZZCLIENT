/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.sys.menu;

import bean.SysBaseInfo;
import common.db.BeanTableInfo;
import em.MenuType;

/**
  * @说明 权限项
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class MenuInfo extends SysBaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected String longnumber = null;
	protected String longname = null;
	
	protected MenuInfo parentInfo = null;
	protected MenuType mt = MenuType.PKG;
	protected String clsName = null;
	protected boolean isVisable = true;
	protected String rptId = null;
	protected int seq = 0;
	
	
	public MenuInfo() {
		super();
	}
	

	public String getLongnumber() {
		return longnumber;
	}


	public void setLongnumber(String longnumber) {
		this.longnumber = longnumber;
	}

	

	public String getLongname() {
		return longname;
	}



	public void setLongname(String longname) {
		this.longname = longname;
	}



	public MenuInfo getParentInfo() {
		return parentInfo;
	}


	public void setParentInfo(MenuInfo parentInfo) {
		this.parentInfo = parentInfo;
	}


	public MenuType getMt() {
		return mt;
	}


	public void setMt(MenuType mt) {
		this.mt = mt;
	}


	public String getClsName() {
		return clsName;
	}


	public void setClsName(String clsName) {
		this.clsName = clsName;
	}


	public boolean getIsVisable() {
		return isVisable;
	}


	public void setIsVisable(boolean isVisable) {
		this.isVisable = isVisable;
	}


	public String getRptId() {
		return rptId;
	}


	public void setRptId(String rptId) {
		this.rptId = rptId;
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String toString()
	{
		return this.getName();
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(MenuInfo.class.getName());
		bt.setTableName("sys_menu");
		bt.setPk("0005");
		return bt;
	}

}
