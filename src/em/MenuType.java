package em;

import java.io.Serializable;

public enum MenuType implements Serializable{
	PKG(0, "文件夹"), BASE(1, "基础资料"), BILL(2, "单据"), RPT(3, "报表"), CENTER(4, "工作台");
	
	private int value = 0;
	private String name = null;
	
	private MenuType(int value, String name)
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
	
	public static MenuType valueOf(int value)
	{
		for(MenuType t : MenuType.values())
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
