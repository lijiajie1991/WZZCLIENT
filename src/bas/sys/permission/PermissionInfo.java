/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.sys.permission;

import bean.SysBaseInfo;
import common.db.BeanTableInfo;
import em.PermissionType;

/**
  * @说明 权限项
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class PermissionInfo extends SysBaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected String longnumber = null;
	protected String longname = null;
	
	protected PermissionInfo parentInfo = null;
	protected PermissionType pt = PermissionType.LISTUI;
	protected String uipath = null;
	protected String btnpath = null;
	
	public PermissionInfo() {
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



	public PermissionInfo getParentInfo() {
		return parentInfo;
	}

	public void setParentInfo(PermissionInfo parentInfo) {
		this.parentInfo = parentInfo;
	}

	public PermissionType getPt() {
		return pt;
	}

	public void setPt(PermissionType pt) {
		this.pt = pt;
	}

	public String getUipath() {
		return uipath;
	}

	public void setUipath(String uipath) {
		this.uipath = uipath;
	}

	public String getBtnpath() {
		return btnpath;
	}

	public void setBtnpath(String btnpath) {
		this.btnpath = btnpath;
	}

	public String toString()
	{
		return this.getName();
	}
	
	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(PermissionInfo.class.getName());
		bt.setTableName("base_permission");
		bt.setPk("0003");
		return bt;
	}

}
