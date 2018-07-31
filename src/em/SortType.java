package em;

import java.io.Serializable;

public enum SortType implements Serializable{
	DESC(0, "降序"), ASC(1, "升序");
	
	private int value = 0;
	private String name = null;
	
	private SortType(int value, String name)
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
	
	public static SortType valueOf(int value)
	{
		for(SortType t : SortType.values())
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
