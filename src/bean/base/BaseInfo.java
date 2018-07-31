package bean.base;

import bean.HeadInfo;

/**
 * @说明 基础资料基类， 继承Info；有新增、保存、删除操作， 用于保存非业务性的数据（用户、商品等）
 * @作者 黎嘉杰
 * @日期 2016-08-27
 */
public abstract class BaseInfo extends HeadInfo{
	private static final long serialVersionUID = 1L;
	
	protected String number  = null;
	protected String name  = null;
	
	public BaseInfo()
	{
		super();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString()
	{
		return this.getName();
	}
}
