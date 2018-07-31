package bill.salary;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;

import bas.person.IHttpPerson;
import bas.person.PersonInfo;
import bean.IHttp;
import bean.Info;
import bean.bill.BillEditUI;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.OpenStateType;
import em.SortType;
import exp.ExpHandle;
import myswing.btn.ToolButton;
import myswing.editor.DatePicker;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;

public class SalaryQuickAccountUI extends BillEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LinkedList<SalaryInfo> list = null;
	
	protected DatePicker column_pkDate = null;
	protected MyComboBox column_cmbPerson = null;
	protected FormatInputField column_txtAmt = null;
	
	protected ToolButton btnBal = null;
	protected ToolButton btnConfirmAll = null;
	
	protected BeanTable tblEntry = null;
	public SalaryQuickAccountUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(1024, 600);
		
		conNumber.setBounds(new Rectangle(3000, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(3000, 5, 300, 30));
		conBizdate.setLabelText("结算日期");
		
		column_cmbPerson = new MyComboBox();
		DefaultCellEditor colunm_person = new DefaultCellEditor(column_cmbPerson);
		
		column_txtAmt = new FormatInputField(FormatInputField.DT_INTEGER);
		DefaultCellEditor colunm_amt = new DefaultCellEditor(column_txtAmt);
		
		column_pkDate = new DatePicker();
		DefaultCellEditor colunm_date = new DefaultCellEditor(column_pkDate);
		
		tblEntry = new BeanTable();
		LinkedList<ColumnInfo> proColList = new LinkedList<ColumnInfo>(); 
		proColList.addLast(new ColumnInfo("id", "id", 0));
		proColList.addLast(new ColumnInfo("personinfo", "员工", 100, PersonInfo.class));
		proColList.addLast(new ColumnInfo("datefrom", "开始日期", 100, Date.class));
		proColList.addLast(new ColumnInfo("dateto", "结束日期", 100, Date.class));
		proColList.addLast(new ColumnInfo("salary", "底工资", 150, BigDecimal.class));
		proColList.addLast(new ColumnInfo("bounty", "补贴", 100, BigDecimal.class));
		proColList.addLast(new ColumnInfo("percentamt", "提成", 150, BigDecimal.class));
		proColList.addLast(new ColumnInfo("punishamt", "扣罚", 100, BigDecimal.class));
		proColList.addLast(new ColumnInfo("amt", "总工资", 150, BigDecimal.class));
		tblEntry.initTable(proColList);
		tblEntry.setColumnEditor("personinfo", colunm_person);
		tblEntry.setColumnEditor("datefrom", colunm_date);
		tblEntry.setColumnEditor("dateto", colunm_date);
		tblEntry.setColumnEditor("salary", colunm_amt);
		tblEntry.setColumnEditor("bounty", colunm_amt);
		tblEntry.setColumnEditor("percentamt", colunm_amt);
		tblEntry.setColumnEditor("punishamt", colunm_amt);
		tblEntry.setColumnEditor("amt", colunm_amt);
		tblEntry.setEditenable(false);
		tblEntry.bindClass(SalaryInfo.class);
		
		tblEntry.setBounds(new Rectangle(5, 5, 1010, 535));
		
		jp.add(tblEntry);
	}
	
	public void initToolBar() throws Exception {
		super.initToolBar();
		btnList.clear();
		
		btnBal = new ToolButton("结算", "");
		btnList.addLast(btnBal);
		
		btnConfirmAll = new ToolButton("全部确认", "");
		btnList.addLast(btnConfirmAll);
		
		btnList.addLast(btnClose);
	}
	
	protected void onLoad() throws Exception {
		super.onLoad();
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		
		FilterInfo personfilter = new FilterInfo();
		personfilter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		personfilter.addItem(new FilterItemInfo("a.pst", CompareType.EQUAL, "0"));
		personfilter.setMkr("#0 and #1");
		LinkedList<Info> perList = IHttpPerson.getInstance().getInfoList(personfilter, order);
		
		column_cmbPerson.addItems(perList.toArray(new PersonInfo[0]));
	}

	public void addListener() throws Exception {
		super.addListener();
		
		btnBal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionCalBalance(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(SalaryQuickAccountUI.this, err);
				}
			}
		});
		
		btnConfirmAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionConfirmAll(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(SalaryQuickAccountUI.this, err);
				}
			}
		});
		
		tblEntry.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
			}
			
			public void mouseEntered(MouseEvent e) {
			}
			
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() != 2)
					return;

				try {
					actionViewSalary();
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(SalaryQuickAccountUI.this, err);
				}
			
				
			}
		});
	}
	
	protected void actionCalBalance(ActionEvent e) throws Exception
	{
		list = IHttpSalary.getInstance().getPersonSalaryList();
		tblEntry.removeRows();
		tblEntry.loadFiles(list);
	}
	
	protected void actionConfirmAll(ActionEvent e) throws Exception
	{
		if(list != null && !list.isEmpty())
		{
			IHttpSalary.getInstance().setAllAuditTrue(list);
			btnConfirmAll.setEnabled(false);
			btnBal.setEnabled(false);
		}
	}

	protected void actionViewSalary() throws Exception
	{
		int rowIndex = tblEntry.getSelectedRow();
		if(rowIndex < 0)
			return;
		
		if(list == null || list.size() <= rowIndex)
			return;
		
		SalaryInfo info = list.get(rowIndex);
		
		this.setEnabled(false);
		
		SalaryEditUI ui = new SalaryEditUI();
		ui.setQuitLs(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		ui.setOwer(this);
		ui.setOst(OpenStateType.VIEW);
		ui.initUI(1024, 768);
		ui.initToolBar();
		ui.createToolBar();
		ui.addListener();
		ui.onLoad();
		ui.setEditData(info);
		ui.loadFiles();
		this.ower.setEnabled(false);
		
	
	}

	protected void storeFiles() throws Exception {
		
	}

	protected void loadFiles() throws Exception {
		
	}
	
	protected void loadDefault() throws Exception {
		
	}
	
	protected Class<?> getInfoClass() {
		return SalaryQuickAccountUI.class;
	}

	protected IHttp getHttpInstance() {
		return null;
	}

	protected String getEditUITitle() {
		return "工资结算";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		
	}

	protected void setCompentStatus() {
		
	}

	protected void actionClose(ActionEvent e) throws Exception {
		super.actionClose(e);
	}
}
