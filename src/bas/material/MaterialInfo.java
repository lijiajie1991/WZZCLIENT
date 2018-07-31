/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.material;

import java.math.BigDecimal;

import bas.material.group.MaterialGroupInfo;
import bas.measureunit.MeasureUnitInfo;
import bas.percent.PercentInfo;
import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class MaterialInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected MaterialGroupInfo mgInfo = null;
	protected String model = null;
	protected MeasureUnitInfo unitInfo = null;
	protected BigDecimal price = BigDecimal.ZERO;
	protected boolean isEnable = true;
	protected PercentInfo pcInfo = null;
	protected String barCode = null;
	
	
	public MaterialInfo() {
		super();
	}

	

	public PercentInfo getPcInfo() {
		return pcInfo;
	}



	public void setPcInfo(PercentInfo pcInfo) {
		this.pcInfo = pcInfo;
	}



	public MaterialGroupInfo getMgInfo() {
		return mgInfo;
	}



	public void setMgInfo(MaterialGroupInfo mgInfo) {
		this.mgInfo = mgInfo;
	}



	public String getModel() {
		return model;
	}



	public void setModel(String model) {
		this.model = model;
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



	public boolean getIsEnable() {
		return isEnable;
	}



	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(MaterialInfo.class.getName());
		bt.setTableName("base_material");
		bt.setPk("0012");
		return bt;
	}

}
