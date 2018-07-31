package em;

import java.io.Serializable;

public enum OpenStateType implements Serializable{
	ADDNEW(0, "新增"), EDIT(1, "编辑"), VIEW(2, "查看");
	
	private int value = 0;
	private String name = null;
	
	private OpenStateType(int value, String name)
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
	
	public static OpenStateType valueOf(int value)
	{
		for(OpenStateType t : OpenStateType.values())
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
