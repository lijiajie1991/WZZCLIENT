package em;

import java.io.Serializable;

public enum PermissionType implements Serializable{
	BEAN(0, "实体"), RPT(1, "报表"), LISTUI(2, "列表界面"), EDITUI(3, "编辑界面"), BTN(4, "按钮"), ACTION(5, "操作");
	
	private int value = 0;
	private String name = null;
	
	private PermissionType(int value, String name)
	{
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	public static PermissionType valueOf(int value)
	{
		for(PermissionType t : PermissionType.values())
		{
			if(t.getValue() == value)
				return t;
		}
		
		return null;
	}
	

	public String toString()
	{
		return this.getName();
	}
}
