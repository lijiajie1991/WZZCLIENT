package bill.purchase;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.LinkedList;

import bas.person.PersonInfo;
import bas.supplier.SupplierInfo;
import bean.Info;
import bean.bill.BillQueryUI;
import cache.BaseData;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import em.CompareType;
import myswing.container.LabelContainer;
import myswing.editor.MyComboBox;
import myswing.table.ColumnInfo;

public class PurchaseQueryUI extends BillQueryUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conPerson = null;
	protected MyComboBox cmbPerson = null;
	
	protected LabelContainer conSupplier = null;
	protected MyComboBox cmbSupplier = null;
	
	public PurchaseQueryUI()
	{
		super();
	}
	
	public void initUI(LinkedList<ColumnInfo> cols) throws Exception
	{
		super.initUI(cols);
		
		cmbPerson = new MyComboBox();
		conPerson = new LabelContainer(cmbPerson, 80, BorderLayout.WEST, true, "服务员：");
		conPerson.setBounds(new Rectangle(5, 40, 230, 30));
		
		cmbSupplier = new MyComboBox();
		conSupplier = new LabelContainer(cmbSupplier, 80, BorderLayout.WEST, true, "供应商：");
		conSupplier.setBounds(new Rectangle(260, 40, 230, 30));
		
		
		condf.setBounds(new Rectangle(5, 75, 230, 30));
		condt.setBounds(new Rectangle(260, 75, 230, 30));
		
		jpFilter.add(conPerson);
		jpFilter.add(conSupplier);
		
		LinkedList<Info> supList = BaseData.getInfoList(SupplierInfo.class);
		LinkedList<Info> perList = BaseData.getInfoList(PersonInfo.class);
		
		LinkedList<Info> supList_temp = new LinkedList<Info>();
		supList_temp.addAll(supList);
		SupplierInfo allSup = new SupplierInfo();
		allSup.setId("all");
		allSup.setNumber("all");
		allSup.setName("全部");
		supList_temp.addFirst(allSup);
		cmbSupplier.addItems(supList_temp.toArray(new SupplierInfo[0]));
		cmbSupplier.setSelectedItem(allSup);
		
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
		
		SupplierInfo supInfo = (SupplierInfo) cmbSupplier.getSelectedItem();
		if(supInfo != null && !"all".equals(supInfo.getId()))
		{
			subFilter.addItem(new FilterItemInfo("customer.id", CompareType.EQUAL, supInfo.getId()));
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
