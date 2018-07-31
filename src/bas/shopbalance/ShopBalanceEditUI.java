package bas.shopbalance;

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
import bean.base.BaseEditUI;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.CompareType;
import em.SortType;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;

public class ShopBalanceEditUI extends BaseEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conShop = null;
	protected MyComboBox cmbShop = null;
	
	protected LabelContainer conPrice = null;
	protected FormatInputField txtPrice = null;
	
	protected LabelContainer conAmt = null;
	protected FormatInputField txtAmt = null;
	
	protected JCheckBox chkIsSms = null;
	
	public ShopBalanceEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(320, 200);
		
		conName.setBounds(new Rectangle(3000, 5, 300, 30));
		conNumber.setBounds(new Rectangle(3000, 5, 300, 30));
		
		cmbShop = new MyComboBox();
		conShop = new LabelContainer(cmbShop, 120, BorderLayout.WEST, true, "店铺：");
		conShop.setBounds(new Rectangle(5, 5, 300, 30));
		
		txtAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		conAmt = new LabelContainer(txtAmt, 120, BorderLayout.WEST, true, "余额：");
		conAmt.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtPrice = new FormatInputField(FormatInputField.DT_DOUBLE);
		conPrice = new LabelContainer(txtPrice, 120, BorderLayout.WEST, true, "单价：");
		conPrice.setBounds(new Rectangle(5, 75, 300, 30));
		
		chkIsSms = new JCheckBox("是否开通短信服务");
		chkIsSms.setBounds(new Rectangle(5, 110, 300, 30));
		
		jp.add(conShop);
		jp.add(conAmt);
		jp.add(conPrice);
		jp.add(chkIsSms);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.isenable", CompareType.EQUAL, 1));
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.longnumber", SortType.ASC));
		LinkedList<Info> shopList = IHttpShop.getInstance().getInfoList(filter, order);
		cmbShop.addItems(shopList.toArray(new ShopInfo[0]));
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		ShopInfo sInfo = (ShopInfo) cmbShop.getSelectedItem();
		BigDecimal amt = txtAmt.getBigDecimalValue();
		BigDecimal price = txtPrice.getBigDecimalValue();
		boolean isSms = chkIsSms.isSelected();
		
		ShopBalanceInfo info = (ShopBalanceInfo) this.editData;
		info.setCustomerShopInfo(sInfo);
		info.setAmt(amt);
		info.setPrice(price);
		info.setIsSms(isSms);
		
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		ShopBalanceInfo info = (ShopBalanceInfo) this.editData;
		cmbShop.setSelectedItem(info.getCustomerShopInfo());
		txtAmt.setBigDecimalValue(info.getAmt());
		txtPrice.setBigDecimalValue(info.getPrice());
		chkIsSms.setSelected(info.getIsSms());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
	}

	protected Class<?> getInfoClass() {
		return ShopBalanceInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpShopBalance.getInstance();
	}

	protected String getEditUITitle() {
		return "店铺余额";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

}
