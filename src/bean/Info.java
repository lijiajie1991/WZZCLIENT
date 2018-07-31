package bean;

import java.io.Serializable;

import common.db.BeanTableInfo;

/**
 * @说明：所有bean对象均继承Info， 子类所有字段必须为protected， 
 		 所有数据库字段均以f开头， 避免bean字段命名使使用到数据库某些关键字
		 bean的xxx字段对应数据库字段里的fxxx 
		 xxxInfo对应数据库字段为fxxxId
 * 		 
 * @作者 黎嘉杰
 * @日期 2016-08-26
 */
/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月11日 上午2:59:22 
  */
public abstract class Info implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//所有bean对象均有id， 对应数据库里的fid字段
	protected String id = null;
	
	//无参构造函数
	public Info()
	{
		
	}

	//返回id
	public String getId() {
		return id;
	}

	//设置id
	public void setId(String id) {
		this.id = id;
	}
	
	//重写toString， 返回对应的ID
	public String toString()
	{
		return this.getClass().getName() + ":" + this.getId();
	}
	
	public boolean equals(Object obj) {
		if(this.getId() != null && !"".equals(this.getId()) && obj instanceof Info)
		{
			Info objInfo = (Info) obj;
			if(objInfo.getId() != null && !"".equals(this.getId()))
			{
				return this.getId().equals(objInfo.getId());
			}
		}
		
		return false;
	}

	//--------------------------------------------以下为抽象类， 所有Bean对象都需要实现-----------------------------------------------------
	/**
	 * @功能描述 获取当前Bean对象数据库相关的信息
	 * @作者 黎嘉杰
	 * @return
	 */
	public abstract BeanTableInfo getBT() throws Exception;
	
}
