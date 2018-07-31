/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.measureunit;

import java.math.BigDecimal;

import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class MeasureUnitInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected BigDecimal coe = BigDecimal.ONE;
	
	
	public MeasureUnitInfo() {
		super();
	}

	public BigDecimal getCoe() {
		return coe;
	}

	public void setCoe(BigDecimal coe) {
		this.coe = coe;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(MeasureUnitInfo.class.getName());
		bt.setTableName("base_measureunit");
		bt.setPk("0013");
		return bt;
	}

}
