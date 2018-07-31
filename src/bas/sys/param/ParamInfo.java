/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.sys.param;

import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 店铺
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
public class ParamInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected String val = null;
	
	public ParamInfo() {
		super();
	}
	
	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String toString()
	{
		return this.getName();
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(ParamInfo.class.getName());
		bt.setTableName("sys_param");
		bt.setPk("0030");
		return bt;
	}

}
