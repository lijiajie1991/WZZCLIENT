package common.util;

import java.math.BigDecimal;
import java.util.Stack;

public class BigDecimalUtil {
	public static int getIntVal(Object obj)
	{
		if(obj == null)
			return 0;
		return Integer.parseInt(obj.toString());
	}
	
	public static BigDecimal getBigDecimal(Object val)
	{
		if(val == null)
			return BigDecimal.ZERO;
		
		if(val instanceof BigDecimal)
			return (BigDecimal) val;
		
		if("".equals(val))
			return BigDecimal.ZERO;
		
		return new BigDecimal(val.toString());
	}
	
	public static BigDecimal parseFormula(String formula)
	{
		formula = formula.replace(" ", "");
		
		try{
			BigDecimal rs = new BigDecimal(formula);
			return rs;
		}catch(Exception e)
		{
			
		}
		
		BigDecimal rs = BigDecimal.ZERO;
		
		String a = null;
		String oper = null;
		
		boolean isbracket = false;
		Stack<String> s = new Stack<String>();
		char[] chs = formula.toCharArray();
		for(char c : chs)
		{
			String str = String.valueOf(c);
			if(!isbracket && (str.equals("+") || str.equals("-")))
			{
				StringBuffer sumVal = new StringBuffer();
				while(!s.isEmpty())
					sumVal.append(s.pop());
				sumVal = sumVal.reverse();
				String sumValStr = sumVal.toString();
				if(sumValStr.contains("*") || sumValStr.contains("/"))
					sumValStr = parseFormula(sumValStr).toString();
				
				if(a == null)
					a = sumValStr;
				else
				{
					a = calFormual(a, sumValStr, oper).toString();
				}
				
				oper = str;
			}
			else if(str.equals("{") || str.equals("[") || str.equals("("))
			{
				isbracket = true;
				s.push(str);
			}
			else if(str.equals("}") || str.equals("]") || str.equals(")"))
			{
				String kd = "";
				if(str.equals("}"))
					kd = "{";
				else if(str.equals("]"))
					kd = "[";
				else if(str.equals(")"))
					kd = "(";
				
				StringBuffer sumVal = new StringBuffer();
				while(!s.isEmpty())
				{
					str = s.pop();
					if(str.equals(kd))
						break;
					sumVal.append(str);
				}
				String subRs = parseFormula(sumVal.reverse().toString()).toString();
				sumVal = new StringBuffer();
				sumVal.append(subRs);
				
				s.push(sumVal.reverse().toString());
				
				isbracket = s.contains("{") || s.contains("[") || s.contains("(");
			}
			else
			{
				s.push(str);
			}
		}
		
		StringBuffer sumVal = new StringBuffer();
		while(!s.isEmpty())
			sumVal.append(s.pop());
		sumVal = sumVal.reverse();
		String sumValStr = sumVal.toString();
		if(sumValStr.contains("*"))
		{
			BigDecimal totalVal = BigDecimal.ONE;
			String[] vals = sumValStr.split("\\*");
			for(String val : vals)
			{
				BigDecimal bgVal = parseFormula(val);
				totalVal = totalVal.multiply(bgVal);
			}
			sumValStr = totalVal.toString();
		}
		else if(sumValStr.contains("/"))
		{
			BigDecimal totalVal = null;
			String[] vals = sumValStr.split("/");
			for(String val : vals)
			{
				BigDecimal bgVal = parseFormula(val);
				totalVal = totalVal != null ? totalVal.divide(bgVal, 10, BigDecimal.ROUND_HALF_UP) : bgVal;
			}
			sumValStr = totalVal.toString();
		}
		
		if(a == null)
			rs = new BigDecimal(sumValStr);
		else
			rs = calFormual(a, sumValStr, oper);
		
		return rs;
	}
	
	public static BigDecimal calFormual(String a, String b, String oper)
	{
		BigDecimal rs = null;
		BigDecimal aVal = new BigDecimal(a);
		BigDecimal bVal = new BigDecimal(b);
		
		if(oper.equals("+"))
			rs = aVal.add(bVal);
		else if(oper.equals("-"))
			rs = aVal.subtract(bVal);
		else if(oper.equals("*"))
			rs = aVal.multiply(bVal);
		else if(oper.equals("/"))
			rs = aVal.divide(bVal, 10, BigDecimal.ROUND_HALF_UP);
		return rs;
	}
}
