package report.base.mj;

public enum RptType {
	
	LIST(0, "列表报表"), CROSS(1, "交叉报表");
	
	private int value = 0;
	private String name = null;
	
	private RptType(int value, String name)
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
	
	public static RptType valueOf(int value)
	{
		for(RptType t : RptType.values())
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
