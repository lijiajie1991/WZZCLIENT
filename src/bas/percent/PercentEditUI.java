package bas.percent;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;

import bas.job.IHttpJob;
import bas.job.JobInfo;
import bean.IHttp;
import bean.Info;
import bean.base.BaseEditUI;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.PercentType;
import em.SortType;
import exp.BizException;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;

public class PercentEditUI extends BaseEditUI{
	private static final long serialVersionUID = 1L;
	protected LinkedList<Info> jobList = null;
	
	protected MyComboBox column_cmbJob = null;
	protected MyComboBox column_cmbType = null;
	protected FormatInputField colunm_txtVal = null;
	protected FormatInputField colunm_txtRemark = null;
	
	protected BeanTable tblEntry = null;
	
	public PercentEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 550);
		
		conName.setBounds(new Rectangle(5, 5, 300, 30));
		conName.setBounds(new Rectangle(340, 5, 300, 30));
		
		column_cmbJob = new MyComboBox();
		DefaultCellEditor column_job = new DefaultCellEditor(column_cmbJob);
		
		column_cmbType = new MyComboBox();
		DefaultCellEditor column_type = new DefaultCellEditor(column_cmbType);
		
		colunm_txtVal = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor colunm_val = new DefaultCellEditor(colunm_txtVal);
		
		colunm_txtRemark = new FormatInputField();
		DefaultCellEditor colunm_remark = new DefaultCellEditor(colunm_txtRemark);
		
		tblEntry = new BeanTable();
		LinkedList<ColumnInfo> colList = new LinkedList<ColumnInfo>(); 
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("jobinfo", "职位", 100, JobInfo.class));
		colList.addLast(new ColumnInfo("pt", "提成类型", 150, PercentType.class));
		colList.addLast(new ColumnInfo("val", "比例%（金额）", 100, BigDecimal.class));
		colList.addLast(new ColumnInfo("otval", "加班增加比例%（金额）", 150, BigDecimal.class));
		colList.addLast(new ColumnInfo("specval", "点牌增加比例%（金额）", 150, BigDecimal.class));
		colList.addLast(new ColumnInfo("remark", "说明", 200, String.class));
		tblEntry.initTable(colList);
		tblEntry.setColumnEditor("jobinfo", column_job);
		tblEntry.setColumnEditor("pt", column_type);
		tblEntry.setColumnEditor("val", colunm_val);
		tblEntry.setColumnEditor("otval", colunm_val);
		tblEntry.setColumnEditor("specval", colunm_val);
		tblEntry.setColumnEditor("remark", colunm_remark);
		tblEntry.enableDetailBtn();
		tblEntry.bindClass(PercentEntryInfo.class);
		JPanel jp_tbl = tblEntry.toJpanel();
		jp_tbl.setBounds(new Rectangle(5, 75, 635, 400));
		
		jp.add(jp_tbl);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		filter.setMkr("#0 or #1");
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		jobList = IHttpJob.getInstance().getInfoList(filter, order);
		column_cmbJob.addItems(jobList.toArray(new JobInfo[0]));
		column_cmbType.addItems(PercentType.values());
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		PercentInfo info = (PercentInfo) this.editData;
		
		
		LinkedList<PercentEntryInfo> perList = info.getPerList();
		perList.clear();
		perList.addAll((Collection<? extends PercentEntryInfo>) tblEntry.storeFiles());
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		PercentInfo info = (PercentInfo) this.editData;
		tblEntry.loadFiles(info.getPerList());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		
		if(jobList != null && !jobList.isEmpty())
		{
			PercentInfo info = (PercentInfo) this.editData;
			LinkedList<PercentEntryInfo> perList = info.getPerList();
			int size = jobList.size();
			for(int i = 0; i < size; i++)
			{
				PercentEntryInfo entryInfo = new PercentEntryInfo();
				entryInfo.setJobInfo((JobInfo) jobList.get(i));
				perList.addLast(entryInfo);
			}
		}
	}

	protected Class<?> getInfoClass() {
		return PercentInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpPercent.getInstance();
	}

	protected String getEditUITitle() {
		return "提成比例";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}


	protected void verifyInput() throws Exception {
		super.verifyInput();
		PercentInfo info = (PercentInfo) this.editData;
		LinkedList<PercentEntryInfo> perList = info.getPerList();
		int size = perList.size();
		for(int i = 0; i < size; i++)
		{
			PercentEntryInfo entryInfo = perList.get(i);
			PercentType pt = entryInfo.getPt();
			if(pt == PercentType.PERCENT)
			{
				BigDecimal val = entryInfo.getVal();
				BigDecimal otVal = entryInfo.getOtVal();
				BigDecimal specVal = entryInfo.getSpecVal();
				
				
				val = val != null ? val : BigDecimal.ZERO;
				otVal = otVal != null ? otVal : BigDecimal.ZERO;
				specVal = specVal != null ? specVal : BigDecimal.ZERO;
				
				val = val.add(otVal).add(specVal);
				if(val.compareTo(new BigDecimal(100)) > 0)
					throw new BizException("第" + (i + 1) + "行， 按比例提成时， 比例不能大于100！");
			}
		}
		
	}

	
}
