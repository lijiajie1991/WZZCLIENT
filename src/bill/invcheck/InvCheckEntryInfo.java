/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bill.invcheck;

import java.math.BigDecimal;

import bas.material.MaterialInfo;
import bas.measureunit.MeasureUnitInfo;
import bean.EntryInfo;
import common.db.BeanTableInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class InvCheckEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected MaterialInfo materialInfo = null;
	protected MeasureUnitInfo unitInfo = null;
	protected BigDecimal thisQty = BigDecimal.ZERO;
	protected BigDecimal checkQty = BigDecimal.ZERO;
	protected BigDecimal lostQty = BigDecimal.ZERO;
	
	public InvCheckEntryInfo()
	{
		super();
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



	public BigDecimal getThisQty() {
		return thisQty;
	}



	public void setThisQty(BigDecimal thisQty) {
		this.thisQty = thisQty;
	}



	public BigDecimal getCheckQty() {
		return checkQty;
	}



	public void setCheckQty(BigDecimal checkQty) {
		this.checkQty = checkQty;
	}



	public BigDecimal getLostQty() {
		return lostQty;
	}



	public void setLostQty(BigDecimal lostQty) {
		this.lostQty = lostQty;
	}



	/**
	  * @throws Exception 
	 * @功能描述
	  * @作者 黎嘉杰 
	  * @日期 2016年9月10日 下午12:56:18 
	  * @参数 
	  * @返回
	  */
	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(InvCheckEntryInfo.class.getName());
		bt.setTableName("bill_invcheck_entry");
		bt.setPk("003801");
		return bt;
	}
}
