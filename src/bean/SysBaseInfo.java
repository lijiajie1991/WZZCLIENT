/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月13日 下午11:32:15 
  */
package bean;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月13日 下午11:32:15 
  */
public abstract class SysBaseInfo extends SysInfo{
	private static final long serialVersionUID = 1L;
	
	protected String number  = null;
	protected String name  = null;
	
	public SysBaseInfo()
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
