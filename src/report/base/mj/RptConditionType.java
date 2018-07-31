package report.base.mj;

public enum RptConditionType {
	
	EMP(0, "空"), WENBEN(1, "文本"), ZHENGSHU(2, "整数"), SHUZI(3, "数字"),
	MEIJU(4, "枚举"), SHUJUYUAN(5, "数据源"), RIQI(6, "日期"), SHOP(7, "店铺");
	
	private int value = 0;
	private String name = null;
	
	private RptConditionType(int value, String name)
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
	
	public static RptConditionType valueOf(int value)
	{
		for(RptConditionType t : RptConditionType.values())
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
