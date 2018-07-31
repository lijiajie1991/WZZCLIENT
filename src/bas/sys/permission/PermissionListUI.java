package bas.sys.permission;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import bas.sys.menu.IHttpMenu;
import bas.sys.menu.MenuInfo;
import bean.IHttp;
import bean.Info;
import bean.base.BaseListUI;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.CompareType;
import em.PermissionType;
import em.SortType;
import exp.ExpHandle;
import myswing.btn.ToolButton;
import myswing.table.ColumnInfo;

public class PermissionListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;
	protected ToolButton btnInit = null;
	
	public PermissionListUI()
	{
		super("权限项维护");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 100));
	}

	public void initToolBar() throws Exception {
		super.initToolBar();
		
		btnInit = new ToolButton("初始化权限项", "btnInit");
		btnList.add(4, btnInit);
	}

	public void addListener() throws Exception {
		super.addListener();
		
		btnInit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionInit(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
	}
	
	protected void actionInit(ActionEvent e) throws Exception
	{
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.clsName", CompareType.ISNOTNULL, ""));
		
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("parent.seq", SortType.ASC));
		order.addItem(new SortItemInfo("a.seq", SortType.ASC));
		
		IHttpPermission instance = IHttpPermission.getInstance();
		LinkedList<Info> list = IHttpMenu.getInstance().getInfoList(filter, order);
		int size = list.size();
		for(int i = 0; i < size; i++)
		{
			int seq = 0;
			
			MenuInfo mInfo = (MenuInfo) list.get(i);
			String mName = mInfo.getName();
			String clsName = mInfo.getClsName();
			String infoCls = clsName.replace("ListUI", "Info");
			String editCls = clsName.replace("ListUI", "EditUI");
			
			if(clsName == null || "".equals(clsName))
				continue;
			
			if(!clsName.startsWith("bas") && !clsName.startsWith("bill"))
			{
				PermissionInfo parentInfo = new PermissionInfo();
				parentInfo.setParentInfo(null);
				parentInfo.setPt(PermissionType.LISTUI);
				parentInfo.setNumber(String.format("%03d%03d", i, seq++));
				parentInfo.setName(mName);
				parentInfo.setUipath(clsName);
				parentInfo.setBtnpath("");
				String id = instance.save(parentInfo);
				parentInfo.setId(id);
			}
			else
			{
				PermissionInfo parentInfo = new PermissionInfo();
				parentInfo.setParentInfo(null);
				parentInfo.setPt(PermissionType.BEAN);
				parentInfo.setNumber(String.format("%03d%03d", i, seq++));
				parentInfo.setName(mName);
				parentInfo.setUipath(infoCls);
				parentInfo.setBtnpath("");
				String id = instance.save(parentInfo);
				parentInfo.setId(id);
				
				PermissionInfo listUIInfo = new PermissionInfo();
				listUIInfo.setParentInfo(parentInfo);
				listUIInfo.setPt(PermissionType.LISTUI);
				listUIInfo.setNumber(String.format("%03d%03d", i, seq++));
				listUIInfo.setName("列表界面");
				listUIInfo.setUipath(clsName);
				listUIInfo.setBtnpath("");
				instance.save(listUIInfo);
				
				PermissionInfo editUIInfo = new PermissionInfo();
				editUIInfo.setParentInfo(parentInfo);
				editUIInfo.setPt(PermissionType.EDITUI);
				editUIInfo.setNumber(String.format("%03d%03d", i, seq++));
				editUIInfo.setName("编辑界面");
				editUIInfo.setUipath(editCls);
				editUIInfo.setBtnpath("");
				instance.save(editUIInfo);
				
				PermissionInfo newInfo = new PermissionInfo();
				newInfo.setParentInfo(parentInfo);
				newInfo.setPt(PermissionType.ACTION);
				newInfo.setNumber(String.format("%03d%03d", i, seq++));
				newInfo.setName("新增");
				newInfo.setUipath(infoCls);
				newInfo.setBtnpath("addnew");
				instance.save(newInfo);
				
				PermissionInfo editInfo = new PermissionInfo();
				editInfo.setParentInfo(parentInfo);
				editInfo.setPt(PermissionType.ACTION);
				editInfo.setNumber(String.format("%03d%03d", i, seq++));
				editInfo.setName("编辑");
				editInfo.setUipath(infoCls);
				editInfo.setBtnpath("edit");
				instance.save(editInfo);
				
				PermissionInfo delInfo = new PermissionInfo();
				delInfo.setParentInfo(parentInfo);
				delInfo.setPt(PermissionType.ACTION);
				delInfo.setNumber(String.format("%03d%03d", i, seq++));
				delInfo.setName("删除");
				delInfo.setUipath(infoCls);
				delInfo.setBtnpath("delete");
				instance.save(delInfo);
				
				if(clsName.startsWith("bas"))
				{
					PermissionInfo saveInfo = new PermissionInfo();
					saveInfo.setParentInfo(parentInfo);
					saveInfo.setPt(PermissionType.ACTION);
					saveInfo.setNumber(String.format("%03d%03d", i, seq++));
					saveInfo.setName("保存");
					saveInfo.setUipath(infoCls);
					saveInfo.setBtnpath("save");
					instance.save(saveInfo);
				}
				else if(clsName.startsWith("bill"))
				{
					PermissionInfo audittrueInfo = new PermissionInfo();
					audittrueInfo.setParentInfo(parentInfo);
					audittrueInfo.setPt(PermissionType.ACTION);
					audittrueInfo.setNumber(String.format("%03d%03d", i, seq++));
					audittrueInfo.setName("确认");
					audittrueInfo.setUipath(infoCls);
					audittrueInfo.setBtnpath("audittrue");
					instance.save(audittrueInfo);
					
					PermissionInfo auditfalseInfo = new PermissionInfo();
					auditfalseInfo.setParentInfo(parentInfo);
					auditfalseInfo.setPt(PermissionType.ACTION);
					auditfalseInfo.setNumber(String.format("%03d%03d", i, seq++));
					auditfalseInfo.setName("改单");
					auditfalseInfo.setUipath(infoCls);
					auditfalseInfo.setBtnpath("auditfalse");
					instance.save(auditfalseInfo);
				}
			}
		}
	}

	protected Class<?> getEditUI() {
		return PermissionEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return PermissionInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpPermission.getInstance();
	}

	
	protected SortInfo getSortInfo() {
		SortInfo sortInfo = super.getSortInfo();
		if(sortInfo == null)
		{
			sortInfo = new SortInfo();
			sortInfo.addItem(new SortItemInfo("a.longnumber", SortType.ASC));
		}
			
		return sortInfo;
	}

	
}
