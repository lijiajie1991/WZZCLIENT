/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.project.group;

import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 权限项
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class ProjectGroupInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	public ProjectGroupInfo() {
		super();
	}


	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(ProjectGroupInfo.class.getName());
		bt.setTableName("base_projectgroup");
		bt.setPk("000801");
		return bt;
	}

}
