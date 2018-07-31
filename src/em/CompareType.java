package em;

import java.io.Serializable;

public enum CompareType implements Serializable{
	EQUAL(0, "等于"), NOTEQUAL(1, "等于"),  
	GRATER(2, "大于"), GRATERANDEQUAL(3, "大于或等于"), 
	LESS(4, "大于"), LESSANDEQUAL(5, "小于或等于"),
	INCLUDE(6, "包含"), NOTINCLUDE(7, "不包含"),
	ISNULL(8, "为空"), ISNOTNULL(9, "不为空"),
	LEFTLIKE(10, "左类似"), RIGHTLIKE(11, "右类似"), LIKE(12, "类似");
	
	private int value = 0;
	private String name = null;
	
	private CompareType(int value, String name)
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
	
	public static CompareType valueOf(int value)
	{
		for(CompareType t : CompareType.values())
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
