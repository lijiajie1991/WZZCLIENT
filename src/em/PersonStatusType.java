package em;

import java.io.Serializable;

public enum PersonStatusType implements Serializable{
	IN(0, "在职"), OUT(1, "离职");
	
	private int value = 0;
	private String name = null;
	
	private PersonStatusType(int value, String name)
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
	
	public static PersonStatusType valueOf(int value)
	{
		for(PersonStatusType t : PersonStatusType.values())
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
