package myswing.tree;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import bas.sys.permission.PermissionInfo;
import bean.IHttp;
import bean.Info;
import common.db.FilterInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.BeanUtil;
import em.SortType;

public class SimpleTree extends JScrollPane{
	private static final long serialVersionUID = 1L;
	
	protected JTree tree = null;
	protected DefaultMutableTreeNode top = null;
	
	public SimpleTree(String topName)
	{
		super();
		
		top = new DefaultMutableTreeNode(topName);
		tree = new JTree(top);
		this.setViewportView(tree);
	}
	
	public void initTree(IHttp http, Class<?> cls) throws Exception
	{
		FilterInfo filter = new FilterInfo();
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.longnumber", SortType.ASC));
		LinkedList<Info> list = http.getInfoList(filter, order);
		
		initTree(list, cls);
	}
	
	public void initTree(LinkedList<Info> list, Class<?> cls) throws Exception
	{
		HashMap<String, Method> mds = BeanUtil.getMethods(cls);
		Method keyMethod = mds.get("getparentinfo");
		Method sortMethod = mds.get("getlongnumber");
		initTree(tree, top, list, keyMethod, sortMethod);
	}
	
	private void initTree(JTree t, DefaultMutableTreeNode top, LinkedList<Info> list, Method keyMethod, Method sortMethod) throws Exception
	{
		HashMap<String, Info> map = new HashMap<String, Info>();
		LinkedList<String> keyList =  new LinkedList<String>();
		int size = list.size();
		for(int i = 0; i < size; i++)
		{
			Info info = list.get(i);
			if(info == null)
				continue;
			
			String str = (String) sortMethod.invoke(info);
			map.put(str, info);
			
			boolean hasAdd = false;
			int keySize = keyList.size();
			for(int j = 0; j < keySize; j++)
			{
				String key = keyList.get(j);
				if(key.compareTo(str) >0)
				{
					keyList.add(j, str);
					hasAdd = true;
					break;
				}
			}
			if(!hasAdd)
				keyList.addLast(str);
		}
		
		
		top.removeAllChildren();
		HashMap<String, DefaultMutableTreeNode> parentMap = new HashMap<String, DefaultMutableTreeNode>();
		LinkedList<Info> childList = new LinkedList< Info>();
		for(int i = 0; i < size; i++)
		{
			String key = keyList.get(i);
			Info info = map.get(key);
			Info pInfo = (Info) keyMethod.invoke(info);
			
			if(pInfo == null || pInfo.getId() == null || pInfo.getId().equals(""))
			{
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(info);
				parentMap.put(info.getId(), node);
				top.add(node);
			}
			else
			{
				String parentId = pInfo.getId();
				if(parentMap.containsKey(parentId))
				{
					DefaultMutableTreeNode pNode = parentMap.get(parentId);
					
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(info);
					parentMap.put(info.getId(), node);
					pNode.add(node);
				}
				else
				{
					childList.addLast(info);
				}
				
			}
		}
		
		while(!childList.isEmpty())
		{
			int index = childList.size() - 1;
			while(index >= 0)
			{
				Info info = childList.get(index);
				Info pInfo = (Info) keyMethod.invoke(info);
				String parentId = pInfo.getId();
				if(parentMap.containsKey(parentId))
				{
					DefaultMutableTreeNode pNode = parentMap.get(parentId);
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(info);
					parentMap.put(info.getId(), node);
					pNode.add(node);
					childList.remove(index);
				}
				index--;
			}
		}
		
		DefaultTreeModel defaultModel = (DefaultTreeModel)tree.getModel();
		defaultModel.reload();
		expandAll(t, new TreePath(top), true);
	}

	private void expandAll(JTree tree, TreePath parent, boolean expand) {
        
		TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }
        
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        } 
	}

	public DefaultMutableTreeNode getSelectedNode()
	{
		Object obj = tree.getLastSelectedPathComponent();
		if (obj instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj;
			return node;
		}
		return null;
	}
	
	public Info getSelectedInfo()
	{
		DefaultMutableTreeNode node = getSelectedNode();
		Object obj = node.getUserObject();
		if(obj != null && obj instanceof Info)
			return (Info) obj;
		
		return null;
	}
	
	public LinkedList<DefaultMutableTreeNode> getChildrenList(DefaultMutableTreeNode node)
	{
		LinkedList<DefaultMutableTreeNode> list = new LinkedList<DefaultMutableTreeNode>();
		int count = node.getChildCount();
		for(int i = 0; i < count; i++)
		{
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
			list.addLast(childNode);
			if(!childNode.isLeaf())
			{
				list.addAll(getChildrenList(childNode));
			}
		}
		
		return list;
	}
	
	public LinkedList<DefaultMutableTreeNode> getParentList(DefaultMutableTreeNode node)
	{
		LinkedList<DefaultMutableTreeNode> list = new LinkedList<DefaultMutableTreeNode>();
		if(!node.isRoot())
		{
			DefaultMutableTreeNode pNode = (DefaultMutableTreeNode) node.getParent();
			list.addLast(pNode);
			if(!pNode.isRoot())
				list.addAll(getParentList(pNode));
		}
		
		return list;
	}
	
	public LinkedList<DefaultMutableTreeNode> getParentList(DefaultMutableTreeNode node, int childCount)
	{
		LinkedList<DefaultMutableTreeNode> list = new LinkedList<DefaultMutableTreeNode>();
		if(!node.isRoot())
		{
			DefaultMutableTreeNode pNode = (DefaultMutableTreeNode) node.getParent();
			int count = pNode.getChildCount();
			Object obj = pNode.getUserObject();
			if(obj != null && obj instanceof PermissionInfo)
			{
				PermissionInfo pInfo = (PermissionInfo) obj;
				String pui = pInfo.getUipath();
				if(pui != null && !"".equals(pui))
					count = childCount + 1;
			}
			if(childCount < count)
				return list;
			
			list.addLast(pNode);
			if(!pNode.isRoot())
				list.addAll(getParentList(pNode, childCount));
		}
		
		return list;
	}
	
	public LinkedList<Info> getChildrenInfoList(DefaultMutableTreeNode node)
	{
		LinkedList<DefaultMutableTreeNode> list = getChildrenList(node);
		return getInfoList(list);
	}
	
	public LinkedList<Info> getParentInfoList(DefaultMutableTreeNode node)
	{
		LinkedList<DefaultMutableTreeNode> list = getParentList(node);
		return getInfoList(list);
	}
	
	public LinkedList<Info> getParentInfoList(DefaultMutableTreeNode node, int childCount)
	{
		LinkedList<DefaultMutableTreeNode> list = getParentList(node, childCount);
		return getInfoList(list);
	}
	
	private LinkedList<Info> getInfoList(LinkedList<DefaultMutableTreeNode> list)
	{
		LinkedList<Info> infoList = new LinkedList<Info>();
		int size = list.size();
		for(int i = 0; i < size; i++)
		{
			DefaultMutableTreeNode node = list.get(i);
			Object obj = node.getUserObject();
			if(obj != null && obj instanceof Info)
			{
				infoList.addLast((Info) obj);
			}
		}
		
		return infoList;
	}

	public DefaultMutableTreeNode getRoot()
	{
		return top;
	}
}
