/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.inwh;

import java.math.BigDecimal;
import java.util.Date;

import bas.material.MaterialInfo;
import bas.measureunit.MeasureUnitInfo;
import bean.bill.BillInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class InWhInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected Date inStockDate = null;
	protected MaterialInfo materialInfo = null;
	protected MeasureUnitInfo unitInfo = null;
	protected BigDecimal price = BigDecimal.ZERO;
	protected BigDecimal qty = BigDecimal.ZERO;
	protected BigDecimal amt = BigDecimal.ZERO;
	protected String remark = null;
	protected String sourceBillId = null;
	
	public InWhInfo() {
		super();
	}
	
	
	

	public Date getInStockDate() {
		return inStockDate;
	}




	public void setInStockDate(Date inStockDate) {
		this.inStockDate = inStockDate;
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




	public String getRemark() {
		return remark;
	}




	public void setRemark(String remark) {
		this.remark = remark;
	}




	public String getSourceBillId() {
		return sourceBillId;
	}




	public void setSourceBillId(String sourceBillId) {
		this.sourceBillId = sourceBillId;
	}




	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(InWhInfo.class.getName());
		bt.setTableName("bill_inwh");
		bt.setPk("0018");
		return bt;
	}

}
