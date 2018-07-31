/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bas.sys.userrole;

import bas.sys.permission.PermissionInfo;
import bean.EntryInfo;
import common.db.BeanTableInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class UserRolePerEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected PermissionInfo permissionInfo = null;
	
	public UserRolePerEntryInfo()
	{
	}
	
	public PermissionInfo getPermissionInfo() {
		return permissionInfo;
	}


	public void setPermissionInfo(PermissionInfo permissionInfo) {
		this.permissionInfo = permissionInfo;
	}


	/**
	  * @throws Exception 
	 * @功能描述
	  * @作者 黎嘉杰 
	  * @日期 2016年9月10日 下午12:56:18 
	  * @参数 
	  * @返回
	  */
	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(UserRolePerEntryInfo.class.getName());
		bt.setTableName("base_userrole_perentry");
		bt.setPk("000201");
		return bt;
	}
}
