/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.vip;

import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class VipInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	public VipInfo() {
		super();
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(VipInfo.class.getName());
		bt.setTableName("base_vip");
		bt.setPk("0010");
		return bt;
	}

}
