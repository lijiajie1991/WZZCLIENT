package bas.sys.user;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;

import bas.sys.shop.IHttpShop;
import bas.sys.shop.ShopInfo;
import bas.sys.userrole.IHttpUserRole;
import bas.sys.userrole.UserRoleInfo;
import bean.IHttp;
import bean.Info;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.SortType;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;
import ui.base.EditUI;

public class UserEditUI extends EditUI{
	private static final long serialVersionUID = 1L;
	
	protected MyComboBox column_cmbShops = null;
	protected MyComboBox column_cmbRoles = null;
	
	
	protected LabelContainer conShop = null;
	protected MyComboBox cmbShop = null;
	
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	protected LabelContainer conName = null;
	protected FormatInputField txtName = null;
	
	protected LabelContainer conPassword = null;
	protected JPasswordField txtPassword = null;
	
	protected JTabbedPane tab = null;
	protected BeanTable tblShopEntry = null;
	protected BeanTable tblRoleEntry = null;
	
	public UserEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(320, 600);
		
		cmbShop = new MyComboBox();
		conShop = new LabelContainer(cmbShop, 120, BorderLayout.WEST, true, "所属店铺：");
		conShop.setBounds(new Rectangle(5, 5, 300, 30));
		
		txtName = new FormatInputField();
		conName = new LabelContainer(txtName, 120, BorderLayout.WEST, true, "名称：");
		conName.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtNumber = new FormatInputField();
		conNumber = new LabelContainer(txtNumber, 120, BorderLayout.WEST, true, "账号：");
		conNumber.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtPassword = new JPasswordField();
		conPassword = new LabelContainer(txtPassword, 120, BorderLayout.WEST, true, "密码：");
		conPassword.setBounds(new Rectangle(5, 110, 300, 30));
		
		column_cmbShops = new MyComboBox();
		DefaultCellEditor column_shop = new DefaultCellEditor(column_cmbShops);
		
		tblShopEntry = new BeanTable();
		LinkedList<ColumnInfo> shopColList = new LinkedList<ColumnInfo>(); 
		shopColList.addLast(new ColumnInfo("id", "id", 0));
		shopColList.addLast(new ColumnInfo("shopinfo", "店铺", 200, ShopInfo.class));
		tblShopEntry.initTable(shopColList);
		tblShopEntry.setColumnEditor("shopinfo", column_shop);
		tblShopEntry.enableDetailBtn();
		tblShopEntry.bindClass(UserShopEntryInfo.class);
		
		column_cmbRoles = new MyComboBox();
		DefaultCellEditor column_role = new DefaultCellEditor(column_cmbRoles);
		
		tblRoleEntry = new BeanTable();
		LinkedList<ColumnInfo> roleColList = new LinkedList<ColumnInfo>(); 
		roleColList.addLast(new ColumnInfo("id", "id", 0));
		roleColList.addLast(new ColumnInfo("roleinfo", "角色", 200, UserRoleInfo.class));
		tblRoleEntry.initTable(roleColList);
		tblRoleEntry.setColumnEditor("roleinfo", column_role);
		tblRoleEntry.enableDetailBtn();
		tblRoleEntry.bindClass(UserRoleEntryInfo.class);
		
		tab = new JTabbedPane(JTabbedPane.TOP);
		tab.setBounds(new Rectangle(5, 145, 300, 400));
		tab.add("角色", tblRoleEntry.toJpanel());
		tab.add("店铺", tblShopEntry.toJpanel());
		
		jp.add(conShop);
		jp.add(conNumber);
		jp.add(conName);
		jp.add(conPassword);
		jp.add(tab);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.longnumber", CompareType.LEFTLIKE, ContextUtil.getShopInfo().getLongnumber()));
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.longnumber", SortType.ASC));
		LinkedList<Info> parentList = IHttpShop.getInstance().getInfoList(filter, order);
		cmbShop.addItems(parentList.toArray(new ShopInfo[0]));
		column_cmbShops.addItems(parentList.toArray(new ShopInfo[0]));
		
		FilterInfo roleFilter = new FilterInfo();
		roleFilter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		SortInfo roleOrder = new SortInfo();
		roleOrder.addItem(new SortItemInfo("a.number", SortType.ASC));
		LinkedList<Info> roleList = IHttpUserRole.getInstance().getInfoList(roleFilter, roleOrder);
		column_cmbRoles.addItems(roleList.toArray(new UserRoleInfo[0]));
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		
		ShopInfo sInfo = (ShopInfo) cmbShop.getSelectedItem();
		String number = txtNumber.getText();
		String name = txtName.getText();
		String password = String.valueOf(txtPassword.getPassword());
		
		UserInfo info = (UserInfo) this.editData;
		info.setDefShopInfo(sInfo);
		info.setPassword(password);
		info.setNumber(number);
		info.setName(name);
		
		LinkedList<UserShopEntryInfo> shopList = info.getShopList();
		shopList.clear();
		shopList.addAll((Collection<? extends UserShopEntryInfo>) tblShopEntry.storeFiles());

		LinkedList<UserRoleEntryInfo> roleList = info.getRoleList();
		roleList.clear();
		roleList.addAll((Collection<? extends UserRoleEntryInfo>) tblRoleEntry.storeFiles());
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		
		UserInfo info = (UserInfo) this.editData;
		
		cmbShop.setSelectedItem(info.getDefShopInfo());
		txtNumber.setText(info.getNumber());
		txtName.setText(info.getName());
		txtPassword.setText(info.getPassword());
		tblShopEntry.loadFiles(info.getShopList());
		tblRoleEntry.loadFiles(info.getRoleList());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		UserInfo info = (UserInfo) this.editData;
		info.setShopId(ContextUtil.getShopId());
		info.setAdminShopId(ContextUtil.getAdminShopId());
	}

	protected Class<?> getInfoClass() {
		return UserInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpUser.getInstance();
	}

	protected String getEditUITitle() {
		return "用户";
	}

}
