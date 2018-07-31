package em;

import java.io.Serializable;

public enum AttendanceType implements Serializable{
	Leave(0, "请假"), Absent(1, "旷工"), Late(2, "迟到"), Early(3, "早退");
	
	private int value = 0;
	private String name = null;
	
	private AttendanceType(int value, String name)
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
	
	public static AttendanceType valueOf(int value)
	{
		for(AttendanceType t : AttendanceType.values())
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
