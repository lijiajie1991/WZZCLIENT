/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.sys.shop;

import bean.SysBaseInfo;
import common.db.BeanTableInfo;
import em.ProfessionType;
import em.ShopType;

/**
  * @说明 店铺
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
public class ShopInfo extends SysBaseInfo{
	private static final long serialVersionUID = 1L;

	protected String longnumber = null;	//长编码
	protected String longname = null;	//长编码
	
	protected ShopInfo parentInfo = null;//所属店铺
	protected String address = null;	//地址
	protected String phone = null;		//电话
	protected ShopType st = null;		//店铺类型
	protected ProfessionType pft = ProfessionType.EMPTY;		//店铺类型
	
	public ShopInfo() {
		super();
	}

	
	public String getLongnumber() {
		return longnumber;
	}



	public void setLongnumber(String longnumber) {
		this.longnumber = longnumber;
	}



	public String getLongname() {
		return longname;
	}



	public void setLongname(String longname) {
		this.longname = longname;
	}



	public ShopInfo getParentInfo() {
		return parentInfo;
	}

	public void setParentInfo(ShopInfo parentInfo) {
		this.parentInfo = parentInfo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public ShopType getSt() {
		return st;
	}

	public void setSt(ShopType st) {
		this.st = st;
	}
	
	
	
	public ProfessionType getPft() {
		return pft;
	}


	public void setPft(ProfessionType pft) {
		this.pft = pft;
	}

	public String toString()
	{
		return this.getName();
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(ShopInfo.class.getName());
		bt.setTableName("base_shop");
		bt.setPk("0004");
		return bt;
	}

}
