package bill.shoppayment;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.LinkedList;

import javax.swing.JCheckBox;

import bas.sys.shop.IHttpShop;
import bas.sys.shop.ShopInfo;
import bean.IHttp;
import bean.Info;
import bean.bill.BillEditUI;
import bean.bill.BillInfo;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.CompareType;
import em.ShopType;
import em.SortType;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;

public class ShopPayMentEditUI extends BillEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conShop = null;
	protected MyComboBox cmbShop = null;
	
	protected LabelContainer conAmt = null;
	protected FormatInputField txtAmt = null;
	
	protected JCheckBox chkIsSms = null;
	
	public ShopPayMentEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 170);
		
		conBizdate.setBounds(new Rectangle(340, 5, 300, 30));
		
		cmbShop = new MyComboBox();
		conShop = new LabelContainer(cmbShop, 120, BorderLayout.WEST, true, "所属店铺：");
		conShop.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		conAmt = new LabelContainer(txtAmt, 120, BorderLayout.WEST, true, "余额：");
		conAmt.setBounds(new Rectangle(340, 40, 300, 30));
		
		chkIsSms = new JCheckBox("是否开通短信服务");
		chkIsSms.setBounds(new Rectangle(5, 75, 300, 30));
		
		jp.add(conShop);
		jp.add(conAmt);
		jp.add(chkIsSms);
		
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.st", CompareType.EQUAL, ShopType.ADMIN.getValue()));
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.longnumber", SortType.ASC));
		LinkedList<Info> shopList = IHttpShop.getInstance().getInfoList(filter, order);
		cmbShop.addItems(shopList.toArray(new ShopInfo[0]));
		
	}


	protected void storeFiles() throws Exception {
		super.storeFiles();
		ShopInfo sInfo = (ShopInfo) cmbShop.getSelectedItem();
		BigDecimal amt = txtAmt.getBigDecimalValue();
		boolean isSms = chkIsSms.isSelected();
		
		ShopPayMentInfo info = (ShopPayMentInfo) this.editData;
		info.setCustomerShopInfo(sInfo);
		info.setAmt(amt);
		info.setIsSms(isSms);
		
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		ShopPayMentInfo info = (ShopPayMentInfo) this.editData;
		cmbShop.setSelectedItem(info.getCustomerShopInfo());
		txtAmt.setBigDecimalValue(info.getAmt());
		chkIsSms.setSelected(info.getIsSms());
	}
	
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
	}

	protected Class<?> getInfoClass() {
		return ShopPayMentInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpShopPayMent.getInstance();
	}

	protected String getEditUITitle() {
		return "店铺充值单";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

	protected void setCompentStatus() {
		super.setCompentStatus();
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		
		cmbShop.setEnabled(!flag);
		txtAmt.setEnabled(!flag);
		chkIsSms.setEnabled(!flag);
	}


}
