package bas.attendance;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
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
import em.AttendanceDaysType;
import em.AttendanceType;
import em.CompareType;
import em.SortType;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;

public class AttendanceEditUI extends BaseEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LinkedList<Info> jobList = null;
	
	protected MyComboBox column_cmbJob = null;
	protected FormatInputField colunm_txtVal = null;
	protected FormatInputField colunm_txtRemark = null;
	
	
	protected LabelContainer conAt = null;
	protected MyComboBox cmbAt = null;
	
	protected LabelContainer conAdt = null;
	protected MyComboBox cmbAdt = null;
	
	protected JCheckBox chkIsEnable = null;
	
	protected BeanTable tblEntry = null;
	
	public AttendanceEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 550);
		
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conName.setBounds(new Rectangle(340, 5, 300, 30));
		
		cmbAt = new MyComboBox();
		conAt = new LabelContainer(cmbAt, 120, BorderLayout.WEST, true, "类型：");
		conAt.setBounds(new Rectangle(5, 40, 300, 30));
		
		cmbAdt = new MyComboBox();
		conAdt = new LabelContainer(cmbAdt, 120, BorderLayout.WEST, true, "统计时间：");
		conAdt.setBounds(new Rectangle(340, 40, 300, 30));
		
		chkIsEnable = new JCheckBox("是否生效");
		chkIsEnable.setBounds(new Rectangle(5, 75, 300, 30));
		
		
		column_cmbJob = new MyComboBox();
		DefaultCellEditor column_job = new DefaultCellEditor(column_cmbJob);
		
		colunm_txtVal = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor colunm_val = new DefaultCellEditor(colunm_txtVal);
		
		colunm_txtRemark = new FormatInputField();
		DefaultCellEditor colunm_remark = new DefaultCellEditor(colunm_txtRemark);
		
		tblEntry = new BeanTable();
		LinkedList<ColumnInfo> colList = new LinkedList<ColumnInfo>(); 
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("jobinfo", "职位", 100, JobInfo.class));
		colList.addLast(new ColumnInfo("amt", "扣取金额", 100, BigDecimal.class));
		colList.addLast(new ColumnInfo("remark", "说明", 200, String.class));
		tblEntry.initTable(colList);
		tblEntry.setColumnEditor("jobinfo", column_job);
		tblEntry.setColumnEditor("amt", colunm_val);
		tblEntry.setColumnEditor("remark", colunm_remark);
		tblEntry.enableDetailBtn();
		tblEntry.bindClass(AttendanceEntryInfo.class);
		JPanel jp_tbl = tblEntry.toJpanel();
		jp_tbl.setBounds(new Rectangle(5, 75, 635, 400));

		jp.add(jp_tbl);
		jp.add(conAt);
		jp.add(conAdt);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		cmbAt.addItems(AttendanceType.values());
		cmbAdt.addItems(AttendanceDaysType.values());
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		filter.setMkr("#0 or #1");
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		jobList = IHttpJob.getInstance().getInfoList(filter, order);
		column_cmbJob.addItems(jobList.toArray(new JobInfo[0]));
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		AttendanceInfo info = (AttendanceInfo) this.editData;
		
		AttendanceType at = (AttendanceType) cmbAt.getSelectedItem();
		AttendanceDaysType adt = (AttendanceDaysType) cmbAdt.getSelectedItem();
		boolean isEnable = chkIsEnable.isSelected();
		
		info.setAt(at);
		info.setAdt(adt);
		info.setIsEnable(isEnable);
		
		LinkedList<AttendanceEntryInfo> perList = info.getAtList();
		perList.clear();
		perList.addAll((Collection<? extends AttendanceEntryInfo>) tblEntry.storeFiles());
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		AttendanceInfo info = (AttendanceInfo) this.editData;
		
		cmbAt.setSelectedItem(info.getAt());
		cmbAdt.setSelectedItem(info.getAdt());
		chkIsEnable.setSelected(info.getIsEnable());
		tblEntry.loadFiles(info.getAtList());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		AttendanceInfo info = (AttendanceInfo) this.editData;
		info.setAt(AttendanceType.Leave);
		info.setAdt(AttendanceDaysType.FullDay);
		info.setIsEnable(true);
		
		if(jobList != null && !jobList.isEmpty())
		{
			LinkedList<AttendanceEntryInfo> adList = info.getAtList();
			int size = jobList.size();
			for(int i = 0; i < size; i++)
			{
				AttendanceEntryInfo entryInfo = new AttendanceEntryInfo();
				entryInfo.setJobInfo((JobInfo) jobList.get(i));
				adList.addLast(entryInfo);
			}
		}
		
	}

	protected Class<?> getInfoClass() {
		return AttendanceInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpAttendance.getInstance();
	}

	protected String getEditUITitle() {
		return "考勤策略";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

}
