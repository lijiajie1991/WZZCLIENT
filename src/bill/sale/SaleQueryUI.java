package bill.sale;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.LinkedList;

import bas.customer.CustomerInfo;
import bas.person.PersonInfo;
import bean.Info;
import bean.bill.BillQueryUI;
import cache.BaseData;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import em.CompareType;
import myswing.container.LabelContainer;
import myswing.editor.MyComboBox;
import myswing.table.ColumnInfo;

public class SaleQueryUI extends BillQueryUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conPerson = null;
	protected MyComboBox cmbPerson = null;
	
	protected LabelContainer conCustomer = null;
	protected MyComboBox cmbCustomer = null;
	
	public SaleQueryUI()
	{
		super();
	}
	
	public void initUI(LinkedList<ColumnInfo> cols) throws Exception
	{
		super.initUI(cols);
		
		cmbPerson = new MyComboBox();
		conPerson = new LabelContainer(cmbPerson, 80, BorderLayout.WEST, true, "服务员：");
		conPerson.setBounds(new Rectangle(5, 40, 230, 30));
		
		cmbCustomer = new MyComboBox();
		conCustomer = new LabelContainer(cmbCustomer, 80, BorderLayout.WEST, true, "客户：");
		conCustomer.setBounds(new Rectangle(260, 40, 230, 30));
		
		
		condf.setBounds(new Rectangle(5, 75, 230, 30));
		condt.setBounds(new Rectangle(260, 75, 230, 30));
		
		jpFilter.add(conPerson);
		jpFilter.add(conCustomer);
		
		LinkedList<Info> cusList = BaseData.getInfoList(CustomerInfo.class);
		LinkedList<Info> perList = BaseData.getInfoList(PersonInfo.class);
		
		LinkedList<Info> cusList_temp = new LinkedList<Info>();
		cusList_temp.addAll(cusList);
		CustomerInfo allCus = new CustomerInfo();
		allCus.setId("all");
		allCus.setNumber("all");
		allCus.setName("全部");
		cusList_temp.addFirst(allCus);
		cmbCustomer.addItems(cusList_temp.toArray(new CustomerInfo[0]));
		cmbCustomer.setSelectedItem(allCus);
		
		LinkedList<Info> perList_temp = new LinkedList<Info>();
		perList_temp.addAll(perList);
		PersonInfo allPer = new PersonInfo();
		allPer.setId("all");
		allPer.setNumber("all");
		allPer.setName("全部");
		perList_temp.addFirst(allPer);
		cmbPerson.addItems(perList_temp.toArray(new PersonInfo[0]));
		cmbPerson.setSelectedItem(allPer);
		
	}
	
	protected void createFilterAndSort()
	{
		super.createFilterAndSort();
		
		FilterInfo subFilter = new FilterInfo();
		
		CustomerInfo cusInfo = (CustomerInfo) cmbCustomer.getSelectedItem();
		if(cusInfo != null && !"all".equals(cusInfo.getId()))
		{
			subFilter.addItem(new FilterItemInfo("customer.id", CompareType.EQUAL, cusInfo.getId()));
		}
		
		PersonInfo perInfo = (PersonInfo) cmbPerson.getSelectedItem();
		if(perInfo != null && !"all".equals(perInfo.getId()))
		{
			subFilter.addItem(new FilterItemInfo("person.id", CompareType.EQUAL, perInfo.getId()));
		}
		
		if(subFilter.getItems().size() > 1)
		{
			subFilter.setMkr("#0 and #1");
		}
		
		if(filter != null)
		{
			filter.mergeFilter(subFilter, "and");
		}
		else
		{
			filter = subFilter;
		}
		
	}

}
