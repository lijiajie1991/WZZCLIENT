package em;

import java.io.Serializable;

public enum ShopStatusType implements Serializable{
	NORMAL(0, "提醒"), WARNNING(1, "提醒"), ARREARS(2, "欠费");
	
	private int value = 0;
	private String name = null;
	
	private ShopStatusType(int value, String name)
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
	
	public static ShopStatusType valueOf(int value)
	{
		for(ShopStatusType t : ShopStatusType.values())
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
