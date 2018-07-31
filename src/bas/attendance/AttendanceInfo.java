/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.attendance;

import java.util.LinkedList;

import bean.base.BaseInfo;
import common.db.BeanTableInfo;
import em.AttendanceDaysType;
import em.AttendanceType;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class AttendanceInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected AttendanceType at = AttendanceType.Leave;
	protected AttendanceDaysType adt = AttendanceDaysType.FullDay;
	protected LinkedList<AttendanceEntryInfo> atList = null;
	protected boolean isEnable = true;
	
	public AttendanceInfo() {
		super();
		atList = new LinkedList<AttendanceEntryInfo>();
	}

	public AttendanceType getAt() {
		return at;
	}

	public void setAt(AttendanceType at) {
		this.at = at;
	}

	public AttendanceDaysType getAdt() {
		return adt;
	}

	public void setAdt(AttendanceDaysType adt) {
		this.adt = adt;
	}
	
	public LinkedList<AttendanceEntryInfo> getAtList() {
		return atList;
	}

	public void setAtList(LinkedList<AttendanceEntryInfo> atList) {
		this.atList = atList;
	}

	public boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(AttendanceInfo.class.getName());
		bt.setTableName("base_attendance");
		bt.setPk("0015");
		bt.addEntryBt("atList", AttendanceEntryInfo.class);
		return bt;
	}

}
