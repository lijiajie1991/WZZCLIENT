package bean.bill;

import java.awt.event.ActionEvent;

import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.ShopType;
import myswing.msgbox.MsgBox;
import ui.base.ListUI;

public abstract class BillListUI extends ListUI{
	private static final long serialVersionUID = 1L;

	public BillListUI()
	{
		this("");
	}

	public BillListUI(String name)
	{
		super(name);
	}

	protected void actionDelete(ActionEvent e) throws Exception {
		int rowIndex = tblMain.getSelectedRow();
		if(rowIndex < 0)
			return;
		
		String id = tblMain.getValueAt(rowIndex, "id").toString();
		BillInfo info = (BillInfo) this.getHttpInstance().getInfo(id);
		if(info.getIsAuditTrue())
		{
			MsgBox.showInfo(ower, "所选记录已经确认， 不能删除！");
			return;
		}
		
		super.actionDelete(e);
	}
	
	protected FilterInfo getFilterInfo() {
		FilterInfo filter = new FilterInfo();
		if(ContextUtil.getShopInfo().getSt() == ShopType.STORE)
		{
			filter .addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		}
		else
		{
			filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
			filter.addItem(new FilterItemInfo("a.adminshopId", CompareType.EQUAL, ContextUtil.getShopId()));
			filter.setMkr("#0 or #1");
		}
		
		FilterInfo subFilter = super.getFilterInfo();
		if(subFilter != null)
		{
			filter.mergeFilter(subFilter, "and");
		}
		
		return filter;
	}

	public boolean isCanOpen() {
		ShopType st = ContextUtil.getShopType();
		return st == null || st == ShopType.STORE;
	}
	
	protected Class<?> getQueryUI()
	{
		return BillQueryUI.class;
	}
}
