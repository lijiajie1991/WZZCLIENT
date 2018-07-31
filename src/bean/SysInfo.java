/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月13日 下午11:32:15 
  */
package bean;

import java.util.Date;

import common.util.ContextUtil;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月13日 下午11:32:15 
  */
public abstract class SysInfo extends Info{
	private static final long serialVersionUID = 1L;
	
	//创建时间
	protected Date createTime = null;
	//创建人ID， 关联user。这里不能直接引用UserInfo, 否则会导致死循环
	protected String createrId = null;
	//最后更新时间
	protected Date lastUpdateTime = null; 
	//最后修改人ID， 关联user。这里不能直接引用UserInfo, 否则会导致死循环
	protected String lastUpdateUserId = null;
	
	public SysInfo()
	{
		super();
	}

	/**
	 * @return 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return 最后更新时间
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime 最后更新时间
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(String lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}
	
	
}
