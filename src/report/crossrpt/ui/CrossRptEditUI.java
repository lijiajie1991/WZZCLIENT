package report.crossrpt.ui;

import java.awt.BorderLayout;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JPanel;

import bean.IHttp;
import bean.bill.BillInfo;
import em.OpenStateType;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;
import report.base.mj.RptAlign;
import report.base.mj.RptDataType;
import report.base.ui.RptEditUI;
import report.crossrpt.info.CrossRptInfo;
import report.crossrpt.info.CrossSuffixInfo;
import report.crossrpt.info.CrossSumInfo;
import report.crossrpt.info.IHttpCrossRpt;

public class CrossRptEditUI extends RptEditUI{
	private static final long serialVersionUID = 1L;
	
	protected BeanTable tblCsEntry = null;
	protected BeanTable tblSfEntry = null;
	
	public CrossRptEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height)
	{
		super.initUI(width, height);
		conType.setVisible(false);
		
		JPanel jp_cs = new JPanel(new BorderLayout());
		tblCsEntry = new BeanTable();
		LinkedList<ColumnInfo> csList = new LinkedList<ColumnInfo>(); 
		csList.addLast(new ColumnInfo("id", "id", 0));
		csList.addLast(new ColumnInfo("seq", "序号", 100, Integer.class));
		csList.addLast(new ColumnInfo("keystr", "关键字", 100, String.class));
		csList.addLast(new ColumnInfo("name", "名称", 100, String.class));
		csList.addLast(new ColumnInfo("val", "值", 100, String.class));
		csList.addLast(new ColumnInfo("width", "宽度", 100, Integer.class));
		csList.addLast(new ColumnInfo("align", "对齐方式", 100, RptAlign.class));
		csList.addLast(new ColumnInfo("dt", "数据类型", 100, RptDataType.class));
		tblCsEntry.initTable(csList);
		tblCsEntry.setColumnEditor("seq", colunm_Integer);
		tblCsEntry.setColumnEditor("keystr", colunm_Str);
		tblCsEntry.setColumnEditor("name", colunm_Str);
		tblCsEntry.setColumnEditor("val", colunm_Str);
		tblCsEntry.setColumnEditor("width", colunm_Integer);
		tblCsEntry.setColumnEditor("align", column_align);
		tblCsEntry.setColumnEditor("dt", column_dt);
		tblCsEntry.enableDetailBtn();
		tblCsEntry.bindClass(CrossSumInfo.class);
		jp_cs.add(tblCsEntry.toJpanel(), BorderLayout.CENTER);
		tab.add(jp_cs, "交叉列");
		
		JPanel jp_sf = new JPanel(new BorderLayout());
		tblSfEntry = new BeanTable();
		LinkedList<ColumnInfo> sfList = new LinkedList<ColumnInfo>(); 
		sfList.addLast(new ColumnInfo("id", "id", 0));
		sfList.addLast(new ColumnInfo("seq", "序号", 100, Integer.class));
		sfList.addLast(new ColumnInfo("keystr", "关键字", 100, String.class));
		sfList.addLast(new ColumnInfo("name", "名称", 100, String.class));
		sfList.addLast(new ColumnInfo("val", "值", 100, String.class));
		sfList.addLast(new ColumnInfo("width", "宽度", 100, Integer.class));
		sfList.addLast(new ColumnInfo("align", "对齐方式", 100, RptAlign.class));
		sfList.addLast(new ColumnInfo("dt", "数据类型", 100, RptDataType.class));
		tblSfEntry.initTable(sfList);
		tblSfEntry.setColumnEditor("seq", colunm_Integer);
		tblSfEntry.setColumnEditor("keyStr", colunm_Str);
		tblSfEntry.setColumnEditor("name", colunm_Str);
		tblSfEntry.setColumnEditor("val", colunm_Str);
		tblSfEntry.setColumnEditor("width", colunm_Integer);
		tblSfEntry.setColumnEditor("align", column_align);
		tblSfEntry.setColumnEditor("dt", column_dt);
		tblSfEntry.enableDetailBtn();
		tblSfEntry.bindClass(CrossSuffixInfo.class);
		jp_sf.add(tblSfEntry.toJpanel(), BorderLayout.CENTER);
		tab.add(jp_sf, "前缀列");
		
	}
	
	

	protected void loadFiles() throws Exception {
		super.loadFiles();
		
		CrossRptInfo info = (CrossRptInfo) this.getEditData();
		tblCsEntry.loadFiles(info.getCsList());
		tblSfEntry.loadFiles(info.getSuffixList());
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		CrossRptInfo info = (CrossRptInfo) this.getEditData();
		
		LinkedList<CrossSumInfo> csList = info.getCsList();
		csList.clear();
		csList.addAll((Collection<? extends CrossSumInfo>) tblCsEntry.storeFiles());
		
		LinkedList<CrossSuffixInfo> sfList = info.getSuffixList();
		sfList.clear();
		sfList.addAll((Collection<? extends CrossSuffixInfo>) tblSfEntry.storeFiles());
	}
	
	protected void setCompentStatus() {
		super.setCompentStatus();
		
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		boolean isView = OpenStateType.VIEW.equals(this.getOst());
		
		tblCsEntry.setEnable(!flag && !isView);
		tblSfEntry.setEnable(!flag && !isView);
	}

	protected Class<?> getInfoClass() {
		return CrossRptInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpCrossRpt.getInstance();
	}

	protected String getEditUITitle() {
		return "交叉报表";
	}
}
