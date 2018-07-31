package ui.menu;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import bas.sys.menu.IHttpMenu;
import bas.sys.menu.MenuInfo;
import bas.sys.user.IHttpUser;
import bas.sys.user.UserInfo;
import bean.Info;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import common.util.PermissionUtil;
import em.CompareType;
import em.SortType;
import ui.base.BaseTabUI;

/**
  * @说明 菜单页签
  * @作者 黎嘉杰 
  * @日期 2016年9月11日 下午11:34:47 
  */
public class MenuUI extends BaseTabUI{
	private static final long serialVersionUID = 1L;

	//菜单树
	protected JTree tree_menu = null;
	//菜单监听事件
	protected MouseListener ls = null;
	
	public MenuUI(MouseListener ls) {
		super();
		this.setName("菜单");
		this.ls = ls;
	}
	
	public MenuUI(JFrame ower, MouseListener ls) {
		super();
		this.setOwer(ower);
		this.setName("菜单");
		this.ls = ls;
	}
	
	public void initUI(HashMap<String, Object> ctx) throws Exception
	{
		super.initUI(ctx);
		tree_menu = createTree("菜单");
		tree_menu.setToggleClickCount(1);
		initTree();
	}

	public void addListener()  throws Exception{
		tree_menu.addMouseListener(ls);
	}
	
	/**
	  * @功能描述 创建菜单树
	  * @作者 黎嘉杰 
	  * @日期 2016年9月12日 下午7:05:46 
	  * @参数 
	  * @返回
	  */
	private JTree createTree(String rootName) {
		//创建根节点
		DefaultMutableTreeNode top_left = new DefaultMutableTreeNode(rootName);
		JTree t = new JTree(top_left);
		//设置选择模式
		t.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		//添加滚动条
		new JScrollPane(t);
		this.add(t, BorderLayout.CENTER);
		return t;
	}
	
	
	/**
	  * @功能描述 初始化菜单树
	  * @作者 黎嘉杰 
	  * @日期 2016年9月12日 下午7:06:21 
	  * @参数 
	  * @返回
	  */
	private void initTree() throws Exception {
		boolean isAdmin = false;
		
		//获取当前用户
		UserInfo userInfo = ContextUtil.getUserInfo();
		isAdmin = PermissionUtil.isSuperUser();
		
		//获取当前用户的权限路径
		HashSet<String> uiPaths = IHttpUser.getInstance().getUserUIPermissionSet(userInfo.getId());
		//获取所有菜单
		FilterInfo ptfilter = new FilterInfo();
		ptfilter.addItem(new FilterItemInfo("a.isVisable", CompareType.EQUAL, "1"));
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("parent.seq", SortType.ASC));
		order.addItem(new SortItemInfo("a.seq", SortType.ASC));
		LinkedList<Info> menuList = IHttpMenu.getInstance().getInfoList(ptfilter, order);
		
		//记录每个树节点
		HashMap<String, MenuNode> menuMap = new HashMap<String, MenuNode>();
		//获取根节点
		DefaultTreeModel tm = (DefaultTreeModel) tree_menu.getModel();
		MutableTreeNode root = (MutableTreeNode) tm.getRoot();

		//遍历所有菜单
		for (Info info : menuList) {
			//获取菜单实体
			MenuInfo mInfo = (MenuInfo) info;
			
			//获取菜单对应的UI路径， 如果用户没有这个UI的权限， 则不显示
			String clsName = mInfo.getClsName();
			clsName = clsName != null ? clsName : "";
			if(!isAdmin && !"".equals(clsName) && !uiPaths.contains(mInfo.getClsName()))
				continue;
			
			MenuNode node = new MenuNode(mInfo);
			menuMap.put(mInfo.getId(), node);

			MutableTreeNode p = null;
			String parentId = mInfo.getParentInfo() != null && mInfo.getParentInfo().getId() != null ? mInfo.getParentInfo().getId() : "null";
			if (menuMap.containsKey(parentId)) {
				p = menuMap.get(parentId);
			} else {
				p = root;
			}

			tm.insertNodeInto(node, p, p.getChildCount());
		}
		
		Iterator<Entry<String, MenuNode>> it = menuMap.entrySet().iterator();
		while(it.hasNext())
		{
			MenuNode node = it.next().getValue();
			if(!node.isHasLeaf())
				tm.removeNodeFromParent(node);
		}
		tree_menu.expandRow(0);
	}

	protected Class<?> getInfoClass() {
		return MenuUI.class;
	}

	public boolean isCanOpen() {
		return true;
	}
}
