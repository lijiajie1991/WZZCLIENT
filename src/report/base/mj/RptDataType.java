package report.base.mj;

public enum RptDataType {
	
	STR(0, "字符串"), QTY(1, "数量"), AMT(2, "金额"), RATE(3, "比例"), BE(4, "布尔"), WT(5, "重量");
	
	private int value = 0;
	private String name = null;
	
	private RptDataType(int value, String name)
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
	
	public static RptDataType valueOf(int value)
	{
		for(RptDataType t : RptDataType.values())
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
