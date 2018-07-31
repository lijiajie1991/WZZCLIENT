/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bas.sys.userrole;

import java.util.LinkedList;

import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class UserRoleInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected LinkedList<UserRolePerEntryInfo> perList = null;
	
	public UserRoleInfo()
	{
		perList = new LinkedList<UserRolePerEntryInfo>();
	}
	
	
	public LinkedList<UserRolePerEntryInfo> getPerList() {
		return perList;
	}


	public void setPerList(LinkedList<UserRolePerEntryInfo> perList) {
		this.perList = perList;
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
		bt.setClsName(UserRoleInfo.class.getName());
		bt.setTableName("base_userrole");
		bt.setPk("0002");
		bt.addEntryBt("perList", UserRolePerEntryInfo.class);
		return bt;
	}
}
