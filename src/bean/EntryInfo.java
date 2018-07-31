/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月29日 下午9:45:01 
  */
package bean;

/**
  * @说明 分录基类
  * @作者 黎嘉杰 
  * @日期 2016年8月29日 下午9:45:01 
  */
public abstract class EntryInfo extends Info{
	private static final long serialVersionUID = 1L;
	
	//表头ID
	protected String parentId = null;
	//序号
	protected int seq = 0;

	public EntryInfo() {
		super();
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
}
