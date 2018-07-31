package report.base.mj;

public enum RptAlign {
	
	LEFT(2, "左对齐"), CENTER(0, "居中"), RIGHT(4, "右对齐");
	
	private int value = 0;
	private String name = null;
	
	private RptAlign(int value, String name)
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
	
	public static RptAlign valueOf(int value)
	{
		for(RptAlign t : RptAlign.values())
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
