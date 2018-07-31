package bean.bill;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.LinkedList;

import common.db.FilterInfo;
import common.db.FilterItemInfo;
import em.CompareType;
import myswing.container.LabelContainer;
import myswing.editor.DatePicker;
import myswing.editor.FormatInputField;
import myswing.table.ColumnInfo;
import ui.base.QueryUI;

public class BillQueryUI extends QueryUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	protected LabelContainer condf = null;
	protected DatePicker pkdf = null;
	
	protected LabelContainer condt = null;
	protected DatePicker pkdt = null;
	
	public BillQueryUI()
	{
		super();
	}
	
	public void initUI(LinkedList<ColumnInfo> cols) throws Exception
	{
		super.initUI(cols);
		
		txtNumber = new FormatInputField();
		conNumber = new LabelContainer(txtNumber, 80, BorderLayout.WEST, true, "编号：");
		conNumber.setBounds(new Rectangle(5, 5, 230, 30));
		
		pkdf = new DatePicker();
		condf = new LabelContainer(pkdf, 80, BorderLayout.WEST, true, "开始日期：");
		condf.setBounds(new Rectangle(5, 40, 230, 30));
		
		pkdt = new DatePicker();
		condt = new LabelContainer(pkdt, 80, BorderLayout.WEST, true, "结束日期：");
		condt.setBounds(new Rectangle(260, 40, 230, 30));
		
		jpFilter.add(conNumber);
		jpFilter.add(condf);
		jpFilter.add(condt);
	}
	
	protected void createFilterAndSort()
	{
		super.createFilterAndSort();
		
		filter = filter != null ? filter : new FilterInfo();
		
		String number = txtNumber.getText();
		String df = pkdf.getDateStr() + " 00:00:00";
		String dt = pkdt.getDateStr() + " 23:59:59";
		
		filter.addItem(new FilterItemInfo("number", CompareType.LIKE, number));
		filter.addItem(new FilterItemInfo("bizdate", CompareType.GRATERANDEQUAL, df));
		filter.addItem(new FilterItemInfo("bizdate", CompareType.LESSANDEQUAL, dt));
		
		filter.setMkr("#0 and #1 and #2");
	}

}
