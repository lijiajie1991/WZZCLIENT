package em;

public enum ComType {
	Barcode(0, "条码抢");
	
	private int value = 0;
	private String name = null;
	
	private ComType(int value, String name)
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
	
	public static ComType valueOf(int value)
	{
		for(ComType t : ComType.values())
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
