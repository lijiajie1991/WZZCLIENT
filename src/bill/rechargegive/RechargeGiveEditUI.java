package bill.rechargegive;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;

import bean.IHttp;
import bean.bill.BillEditUI;
import bean.bill.BillInfo;
import common.util.DateTimeUtil;
import myswing.container.LabelContainer;
import myswing.editor.DatePicker;
import myswing.editor.FormatInputField;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;

public class RechargeGiveEditUI extends BillEditUI{
	private static final long serialVersionUID = 1L;
	
	protected FormatInputField colunm_txtAmt = null;
	protected FormatInputField colunm_txtGiveAmt = null;
	protected FormatInputField colunm_txtRemark = null;
	
	protected LabelContainer conName = null;
	protected FormatInputField txtName = null;
	
	protected LabelContainer conDateFrom = null;
	protected DatePicker pkDateFrom = null;
	
	protected LabelContainer conDateTo = null;
	protected DatePicker pkDateTo = null;
	
	protected BeanTable tblGiveEntry = null;
	
	public RechargeGiveEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 400);
		
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(3000, 5, 300, 30));
		
		txtName = new FormatInputField();
		conName = new LabelContainer(txtName, 120, BorderLayout.WEST, true, "名称：");
		conName.setBounds(new Rectangle(340, 5, 300, 30));
		
		pkDateFrom = new DatePicker();
		conDateFrom = new LabelContainer(pkDateFrom, 120, BorderLayout.WEST, true, "开始日期：");
		conDateFrom.setBounds(new Rectangle(5, 40, 300, 30));
		
		pkDateTo = new DatePicker();
		conDateTo = new LabelContainer(pkDateTo, 120, BorderLayout.WEST, true, "结束日期：");
		conDateTo.setBounds(new Rectangle(340, 40, 300, 30));
		
		
		colunm_txtAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor colunm_amt = new DefaultCellEditor(colunm_txtAmt);
		
		colunm_txtGiveAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor colunm_giveAmt = new DefaultCellEditor(colunm_txtGiveAmt);
		
		
		colunm_txtRemark = new FormatInputField();
		DefaultCellEditor colunm_remark = new DefaultCellEditor(colunm_txtRemark);
		
		tblGiveEntry = new BeanTable();
		LinkedList<ColumnInfo> colList = new LinkedList<ColumnInfo>(); 
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("amt", "充值金额", 150, BigDecimal.class));
		colList.addLast(new ColumnInfo("giveamt", "赠送金额", 150, BigDecimal.class));
		colList.addLast(new ColumnInfo("remark", "说明", 300, String.class));
		tblGiveEntry.initTable(colList);
		tblGiveEntry.setColumnEditor("amt", colunm_amt);
		tblGiveEntry.setColumnEditor("giveamt", colunm_giveAmt);
		tblGiveEntry.setColumnEditor("remark", colunm_remark);
		tblGiveEntry.enableDetailBtn();
		tblGiveEntry.bindClass(RechargeGiveEntryInfo.class);
		JPanel jp_tbl = tblGiveEntry.toJpanel();
		jp_tbl.setBounds(new Rectangle(5, 75, 640, 250));
		
		jp.add(conName);
		jp.add(conDateFrom);
		jp.add(conDateTo);
		jp.add(jp_tbl);
	}

	public void initToolBar() throws Exception{
		super.initToolBar();
	}
	
	protected void onLoad() throws Exception {
		super.onLoad();
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		RechargeGiveInfo info = (RechargeGiveInfo) this.editData;
		
		String name = txtName.getText();
		Date df = pkDateFrom.getDate();
		Date dt = pkDateTo.getDate();
		
		info.setName(name);
		info.setDateFrom(df);
		info.setDateTo(dt);
		
		LinkedList<RechargeGiveEntryInfo> list = info.getGiveList();
		list.clear();
		list.addAll((Collection<? extends RechargeGiveEntryInfo>) tblGiveEntry.storeFiles());
		
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		RechargeGiveInfo info = (RechargeGiveInfo) this.editData;
		
		txtName.setText(info.getName());
		pkDateFrom.setDate(info.getDateFrom());
		pkDateTo.setDate(info.getDateTo());
		
		tblGiveEntry.loadFiles(info.getGiveList());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		
		RechargeGiveInfo info = (RechargeGiveInfo) this.editData;
		info.setDateTo(DateTimeUtil.getDateAfterDays(new Date(), 7));
		info.setDateFrom(DateTimeUtil.getDateAfterDays(new Date(), 1));
	}

	protected Class<?> getInfoClass() {
		return RechargeGiveInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpRechargeGive.getInstance();
	}

	protected String getEditUITitle() {
		return "充值优惠";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

	protected void setCompentStatus() {
		super.setCompentStatus();
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		
		txtName.setEnabled(!flag);
		pkDateFrom.setEnabled(!flag);
		pkDateTo.setEnabled(!flag);
		tblGiveEntry.setEnable(!flag);
	}

	protected void actionClose(ActionEvent e) throws Exception {
		super.actionClose(e);
	}
}
