package em;

import java.io.Serializable;

public enum ProfessionType implements Serializable{
	EMPTY(0, "无"), NAIL(1, "美甲店"), BEAUTY(2, "美容院");
	
	private int value = 0;
	private String name = null;
	
	private ProfessionType(int value, String name)
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
	
	public static ProfessionType valueOf(int value)
	{
		for(ProfessionType t : ProfessionType.values())
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
