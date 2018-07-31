/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.sys.print;

import java.util.LinkedList;

import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 店铺
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
public class PrintInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	protected String clsName = null;
	protected String printMethod = null;
	
	protected LinkedList<PrintEntryInfo> entryList = null;
	
	public PrintInfo() {
		super();
		
		entryList = new LinkedList<PrintEntryInfo>();
	}
	
	public String getClsName() {
		return clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
	}
	
	

	public LinkedList<PrintEntryInfo> getEntryList() {
		return entryList;
	}

	public void setEntryList(LinkedList<PrintEntryInfo> entryList) {
		this.entryList = entryList;
	}
	
	

	public String getPrintMethod() {
		return printMethod;
	}

	public void setPrintMethod(String printMethod) {
		this.printMethod = printMethod;
	}

	public String toString()
	{
		return this.getName();
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(PrintInfo.class.getName());
		bt.setTableName("sys_print");
		bt.setPk("0041");
		bt.addEntryBt("entryList", PrintEntryInfo.class);
		return bt;
	}

}
