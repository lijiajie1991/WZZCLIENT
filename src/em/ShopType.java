package em;

import java.io.Serializable;

public enum ShopType implements Serializable{
	STORE(0, "实体店"), ADMIN(1, "管理");
	
	private int value = 0;
	private String name = null;
	
	private ShopType(int value, String name)
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
	
	public static ShopType valueOf(int value)
	{
		for(ShopType t : ShopType.values())
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
