package bas.sys.userrole;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import bas.sys.permission.IHttpPermission;
import bas.sys.permission.PermissionInfo;
import bean.IHttp;
import bean.Info;
import exp.ExpHandle;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.tree.SimpleTree;
import ui.base.EditUI;

public class UserRoleEditUI extends EditUI{
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, PermissionInfo> allMap = null;
	private HashMap<String, PermissionInfo> allotMap = null;
	
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	protected LabelContainer conName = null;
	protected FormatInputField txtName = null;
	
	protected SimpleTree jtAll = null;
	protected SimpleTree jtAllot = null;
	
	protected JButton btnAdd = null;
	protected JButton btnDel = null;
	
	protected JButton btnAddAll = null;
	protected JButton btnDelAll = null;
	
	public UserRoleEditUI()
	{
		super();
		allotMap = new HashMap<String, PermissionInfo>();
		allMap = new HashMap<String, PermissionInfo>();
	}
	
	public void initUI(int width, int height) {
		super.initUI(700, 530);
		
		txtNumber = new FormatInputField();
		txtNumber.setEditable(false);
		conNumber = new LabelContainer(txtNumber, 120, BorderLayout.WEST, true, "编码：");
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		
		txtName = new FormatInputField();
		conName = new LabelContainer(txtName, 120, BorderLayout.WEST, true, "名称：");
		conName.setBounds(new Rectangle(370, 5, 300, 30));
		
		JPanel jpPermission = new JPanel();
		jpPermission.setLayout(null);
		jpPermission.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		jpPermission.setBounds(new Rectangle(5, 50, 665, 410));
		
		jtAll = new SimpleTree("可分配权限");
		jtAll.setBounds(new Rectangle(5, 5, 295, 400));
		jpPermission.add(jtAll);
		
		jtAllot = new SimpleTree("已分配权限");
		jtAllot.setBounds(new Rectangle(365, 5, 295, 400));
		jpPermission.add(jtAllot);
		
		btnAdd = new JButton(">");
		btnAdd.setBounds(new Rectangle(305, 100, 55, 30));
		jpPermission.add(btnAdd);
		
		btnDel = new JButton("<");
		btnDel.setBounds(new Rectangle(305, 135, 55, 30));
		jpPermission.add(btnDel);
		
		btnAddAll = new JButton(">>");
		btnAddAll.setBounds(new Rectangle(305, 200, 55, 30));
		jpPermission.add(btnAddAll);
		
		btnDelAll = new JButton("<<");
		btnDelAll.setBounds(new Rectangle(305, 235, 55, 30));
		jpPermission.add(btnDelAll);
		
		
		jp.add(conNumber);
		jp.add(conName);
		jp.add(jpPermission);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		LinkedList<Info> list = IHttpPermission.getInstance().getPermissionList();
		for(Info info : list)
		{
			allMap.put(info.getId(), (PermissionInfo) info);
		}
		jtAll.initTree(list, PermissionInfo.class);
	}
	
	public void addListener() throws Exception {
		super.addListener();
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddPermission(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(UserRoleEditUI.this, err);
				}
			}
		});
		
		btnAddAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddAllPermission(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(UserRoleEditUI.this, err);
				}
			}
		});
		
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionDelPermission(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(UserRoleEditUI.this, err);
				}
			}
		});
		
		btnDelAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionDelAllPermission(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(UserRoleEditUI.this, err);
				}
			}
		});
		
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		
		String number = txtNumber.getText();
		String name = txtName.getText();
		
		UserRoleInfo info = (UserRoleInfo) this.editData;
		info.setNumber(number);
		info.setName(name);
		
		LinkedList<UserRolePerEntryInfo> perList = info.getPerList();
		perList.clear();
		
		Iterator<Entry<String, PermissionInfo>> it = allotMap.entrySet().iterator();
		while(it.hasNext())
		{
			UserRolePerEntryInfo entryInfo = new UserRolePerEntryInfo();
			entryInfo.setPermissionInfo(it.next().getValue());
			perList.addLast(entryInfo);
		}
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		
		UserRoleInfo info = (UserRoleInfo) this.editData;
		
		txtNumber.setText(info.getNumber());
		txtName.setText(info.getName());
		
		
		allotMap.clear();
		LinkedList<UserRolePerEntryInfo> list = info.getPerList();
		int size = list.size();
		for(int i = 0; i < size; i++)
		{
			UserRolePerEntryInfo entryInfo = list.get(i);
			PermissionInfo pInfo = entryInfo.getPermissionInfo();
			allotMap.put(pInfo.getId(), allMap.get(pInfo.getId()));
		}
		this.initAllotTree();
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		UserRoleInfo info = (UserRoleInfo) this.editData;
		info.setNumber(IHttpUserRole.getInstance().getNewNumber());
	}

	protected Class<?> getInfoClass() {
		return UserRoleInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpUserRole.getInstance();
	}

	protected String getEditUITitle() {
		return "用户角色";
	}

	protected void actionAddPermission(ActionEvent e) throws Exception
	{	
		LinkedList<Info> allotList = new LinkedList<Info>();
		DefaultMutableTreeNode node = jtAll.getSelectedNode();
		Info nodeInfo = jtAll.getSelectedInfo();
		if(nodeInfo != null)
			allotList.addLast(nodeInfo);
		
		allotList.addAll(jtAll.getParentInfoList(node));
		allotList.addAll(jtAll.getChildrenInfoList(node));
		int size = allotList.size();
		for(int i = 0; i < size; i++)
		{
			Info info = allotList.get(i);
			allotMap.put(info.getId(), (PermissionInfo) info);
		}
		
		initAllotTree();
	}

	protected void actionAddAllPermission(ActionEvent e) throws Exception
	{
		LinkedList<Info> allotList = new LinkedList<Info>();
		DefaultMutableTreeNode node = jtAll.getRoot();
		allotList.addAll(jtAll.getChildrenInfoList(node));
		int size = allotList.size();
		for(int i = 0; i < size; i++)
		{
			Info info = allotList.get(i);
			allotMap.put(info.getId(), (PermissionInfo) info);
		}
		
		initAllotTree();
	}

	protected void actionDelPermission(ActionEvent e) throws Exception
	{
		LinkedList<Info> allotList = new LinkedList<Info>();
		DefaultMutableTreeNode node = jtAllot.getSelectedNode();
		Info nodeInfo = jtAllot.getSelectedInfo();
		if(nodeInfo != null)
			allotList.addLast(nodeInfo);
		
		allotList.addAll(jtAllot.getParentInfoList(node, 1));
		allotList.addAll(jtAllot.getChildrenInfoList(node));
		int size = allotList.size();
		for(int i = 0; i < size; i++)
		{
			Info info = allotList.get(i);
			allotMap.remove(info.getId());
		}
		
		initAllotTree();
	}

	protected void actionDelAllPermission(ActionEvent e) throws Exception
	{
		allotMap.clear();
		initAllotTree();
	}

	private void initAllotTree() throws Exception
	{
		LinkedList<Info> allotList = new LinkedList<Info>();
		Iterator<Entry<String, PermissionInfo>> it = allotMap.entrySet().iterator();
		while(it.hasNext())
		{
			PermissionInfo info = it.next().getValue();
			if(info != null)
				allotList.addLast(info);
		}
		jtAllot.initTree(allotList, PermissionInfo.class);
	}
}
