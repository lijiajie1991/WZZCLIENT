/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.customer;

import java.math.BigDecimal;

import bas.vip.VipInfo;
import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class CustomerInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected String cardId = null;
	protected String birth = null;
	protected String phone = null;
	protected VipInfo vipInfo = null;
	protected BigDecimal points = BigDecimal.ZERO; 
	protected BigDecimal balance = BigDecimal.ZERO; 
	protected boolean isEnable = true;
	
	public CustomerInfo() {
		super();
	}
	
	

	public String getCardId() {
		return cardId;
	}



	public void setCardId(String cardId) {
		this.cardId = cardId;
	}



	public String getBirth() {
		return birth;
	}



	public void setBirth(String birth) {
		this.birth = birth;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public VipInfo getVipInfo() {
		return vipInfo;
	}



	public void setVipInfo(VipInfo vipInfo) {
		this.vipInfo = vipInfo;
	}



	public BigDecimal getPoints() {
		return points;
	}



	public void setPoints(BigDecimal points) {
		this.points = points;
	}



	public BigDecimal getBalance() {
		return balance;
	}



	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	


	public boolean getIsEnable() {
		return isEnable;
	}



	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}



	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(CustomerInfo.class.getName());
		bt.setTableName("base_customer");
		bt.setPk("0011");
		return bt;
	}

}
