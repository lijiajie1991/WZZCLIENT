/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bas.sys.user;

import bas.sys.userrole.UserRoleInfo;
import bean.EntryInfo;
import common.db.BeanTableInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class UserRoleEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected UserRoleInfo roleInfo = null;
	
	public UserRoleEntryInfo()
	{
	}

	public UserRoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(UserRoleInfo roleInfo) {
		this.roleInfo = roleInfo;
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
		bt.setClsName(UserRoleEntryInfo.class.getName());
		bt.setTableName("base_user_roleentry");
		bt.setPk("000101");
		return bt;
	}
}
