package em;

public enum PayType {
	Cash(0, "现金"), Bank(1, "刷银行卡"), Vip(2, "刷会员卡"), Webbank(3, "微信支付"), ALiPay(5, "支付宝"), Groupon(4, "团购");
	
	private int value = 0;
	private String name = null;
	
	private PayType(int value, String name)
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
	
	public static PayType valueOf(int value)
	{
		for(PayType t : PayType.values())
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
