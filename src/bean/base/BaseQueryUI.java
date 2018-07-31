package bean.base;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.LinkedList;

import common.db.FilterInfo;
import common.db.FilterItemInfo;
import em.CompareType;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.table.ColumnInfo;
import ui.base.QueryUI;

public class BaseQueryUI extends QueryUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	protected LabelContainer conName = null;
	protected FormatInputField txtName = null;
	
	public BaseQueryUI()
	{
		super();
	}
	
	public void initUI(LinkedList<ColumnInfo> cols) throws Exception
	{
		super.initUI(cols);
		
		txtNumber = new FormatInputField();
		conNumber = new LabelContainer(txtNumber, 80, BorderLayout.WEST, true, "编号：");
		conNumber.setBounds(new Rectangle(5, 5, 230, 30));
		
		txtName = new FormatInputField();
		conName = new LabelContainer(txtName, 80, BorderLayout.WEST, true, "名称：");
		conName.setBounds(new Rectangle(260, 5, 230, 30));
		
		jpFilter.add(conNumber);
		jpFilter.add(conName);
	}
	
	protected void createFilterAndSort()
	{
		super.createFilterAndSort();
		
		filter = new FilterInfo();
		
		String number = txtNumber.getText();
		String name = txtName.getText();
		
		filter.addItem(new FilterItemInfo("number", CompareType.LIKE, number));
		filter.addItem(new FilterItemInfo("name", CompareType.LIKE, name));
		filter.setMkr("#0 and #1");
	}

}
