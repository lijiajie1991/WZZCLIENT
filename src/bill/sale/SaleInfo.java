/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.sale;

import java.math.BigDecimal;

import bas.customer.CustomerInfo;
import bas.material.MaterialInfo;
import bas.measureunit.MeasureUnitInfo;
import bas.person.PersonInfo;
import bean.bill.BillInfo;
import common.db.BeanTableInfo;
import em.PayType;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class SaleInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected PersonInfo personInfo = null;
	protected CustomerInfo customerInfo = null;
	protected MaterialInfo materialInfo = null;
	protected MeasureUnitInfo unitInfo = null;
	protected BigDecimal price = BigDecimal.ZERO;
	protected BigDecimal qty = BigDecimal.ZERO;
	protected BigDecimal amt = BigDecimal.ZERO;
	protected BigDecimal factamt = BigDecimal.ZERO;
	protected String remark = null;
	protected PayType pt = PayType.Cash;
	protected boolean isBalance = false;
	protected String sourceBillId = null;
	
	public SaleInfo() {
		super();
	}

	

	public PersonInfo getPersonInfo() {
		return personInfo;
	}



	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}



	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}



	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}



	public MaterialInfo getMaterialInfo() {
		return materialInfo;
	}



	public void setMaterialInfo(MaterialInfo materialInfo) {
		this.materialInfo = materialInfo;
	}



	public MeasureUnitInfo getUnitInfo() {
		return unitInfo;
	}



	public void setUnitInfo(MeasureUnitInfo unitInfo) {
		this.unitInfo = unitInfo;
	}



	public BigDecimal getPrice() {
		return price;
	}



	public void setPrice(BigDecimal price) {
		this.price = price;
	}



	public BigDecimal getQty() {
		return qty;
	}



	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}



	public BigDecimal getAmt() {
		return amt;
	}



	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}



	public BigDecimal getFactamt() {
		return factamt;
	}



	public void setFactamt(BigDecimal factamt) {
		this.factamt = factamt;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public PayType getPt() {
		return pt;
	}



	public void setPt(PayType pt) {
		this.pt = pt;
	}

	public boolean getIsBalance() {
		return isBalance;
	}

	public void setIsBalance(boolean isBalance) {
		this.isBalance = isBalance;
	}
	
	

	public String getSourceBillId() {
		return sourceBillId;
	}



	public void setSourceBillId(String sourceBillId) {
		this.sourceBillId = sourceBillId;
	}



	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(SaleInfo.class.getName());
		bt.setTableName("bill_sale");
		bt.setPk("0020");
		return bt;
	}

}
