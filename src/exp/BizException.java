package exp;

import java.io.IOException;

public class BizException extends IOException{
	private static final long serialVersionUID = 1L;

	public BizException(String message)
	{
		super(message);
	}
}
