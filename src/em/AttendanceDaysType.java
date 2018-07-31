package em;

import java.io.Serializable;

public enum AttendanceDaysType implements Serializable{
	HalfDay(1, "半天"), FullDay(2, "全天"), Hour(3, "小时"), Minutes(4, "分钟");
	
	private int value = 0;
	private String name = null;
	
	private AttendanceDaysType(int value, String name)
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
	
	public static AttendanceDaysType valueOf(int value)
	{
		for(AttendanceDaysType t : AttendanceDaysType.values())
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
