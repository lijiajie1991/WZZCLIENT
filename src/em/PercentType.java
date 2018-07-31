package em;

import java.io.Serializable;

public enum PercentType implements Serializable{
	NONE(0, "不参与提成"), PERCENT(1, "按比例提成%"), AMT(2, "按固定金额提成");
	
	private int value = 0;
	private String name = null;
	
	private PercentType(int value, String name)
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
	
	public static PercentType valueOf(int value)
	{
		for(PercentType t : PercentType.values())
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
