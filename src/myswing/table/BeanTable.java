package myswing.table;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import bean.Info;
import common.util.BeanUtil;

public class BeanTable extends SimpleTable{
	private static final long serialVersionUID = 1L;
	protected Class<?> cls = null;
	
	public BeanTable()
	{
		super();
	}
	
	public BeanTable(boolean sorteAble)
	{
		super(sorteAble);
	}
	
	public void bindClass(Class<?> cls)
	{
		this.cls = cls;
	}
	
	public LinkedList<? extends Info> storeFiles() throws Exception
	{
		LinkedList<Info> list = new LinkedList<Info>();
		int rowCount = this.getRowCount();
		for(int rowIndex = 0; rowIndex < rowCount; rowIndex++)
		{
			HashMap<String, Object> infoMap = this.getRowMap(rowIndex);
			Info info = BeanUtil.getInfoFromMap(cls, infoMap);
			list.addLast(info);
		}
		
		return list;
	}
	
	public void loadFiles(LinkedList<? extends Info> list) throws Exception
	{
		this.removeRows();
		int size = list.size();
		for(int rowIndex = 0; rowIndex < size; rowIndex++)
		{
			Map<String, Object> map = BeanUtil.getMapInfo(list.get(rowIndex));
			this.insertRowMap(rowIndex, map);
		}
	}

}
