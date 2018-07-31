package bill.salary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import bean.IHttp;
import bean.bill.BillListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.OpenStateType;
import em.RptAlign;
import em.SortType;
import exp.ExpHandle;
import myswing.btn.ToolButton;
import myswing.table.ColumnInfo;

public class SalaryListUI extends BillListUI{
	private static final long serialVersionUID = 1L;

	protected ToolButton btnQuickAccount = null;
	public SalaryListUI()
	{
		super("员工工资单");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("person_name", "员工", 100));
		colList.addLast(new ColumnInfo("datefrom", "开始日期", 100, Date.class));
		colList.addLast(new ColumnInfo("dateto", "结束日期", 100, Date.class));
		colList.addLast(new ColumnInfo("salary", "底工资", 100));
		colList.addLast(new ColumnInfo("bounty", "补贴", 100));
		colList.addLast(new ColumnInfo("percentamt", "提成工资", 100));
		colList.addLast(new ColumnInfo("amt", "总工资", 100));
		colList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
		colList.addLast(new ColumnInfo("remark", "备注", 100));
	}
	

	public void initToolBar() throws Exception {
		super.initToolBar();
		
		btnQuickAccount = new ToolButton("批量结算", "addnew");
		btnList.add(5, btnQuickAccount);
	}

	public void addListener() throws Exception {
		super.addListener();
		
		btnQuickAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionQuickAccount(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
	}
	
	protected void actionQuickAccount(ActionEvent e) throws Exception{
		this.ower.setEnabled(false);
		
		SalaryQuickAccountUI ui = new SalaryQuickAccountUI();
		ui.setQuitLs(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRefresh(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		ui.setOwer(ower);
		ui.setOst(OpenStateType.ADDNEW);
		ui.initUI(1024, 768);
		ui.initToolBar();
		ui.createToolBar();
		ui.addListener();
		ui.onLoad();
	}


	protected Class<?> getEditUI() {
		return SalaryEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return SalaryInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpSalary.getInstance();
	}

	
	protected SortInfo getSortInfo() {
		SortInfo sortInfo = super.getSortInfo();
		if(sortInfo == null)
		{
			sortInfo = new SortInfo();
			sortInfo.addItem(new SortItemInfo("a.number", SortType.ASC));
		}
			
		return sortInfo;
	}
}
