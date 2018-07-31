package myswing.editor;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FormatInputField extends JTextField{
	private static final long serialVersionUID = 1L;
	private String name = null;
	
	//double类型的正则表达式
	public static final String DT_DOUBLE = "[0-9]+.{0,1}[0-9]*";
	//int类型的正则表达式
	public static final String DT_INTEGER = "[0-9]+";
	
	//正则表达式
	private String regex = null;
	//为double类型时，指定小数位
	private int scale = -1;
	
	/**
	 * @功能描述 默认构造函数，数据类型为字符串
	 * @作者 乌卒卒
	 * @日期 2015-09-13
	 */
	public FormatInputField()
	{
		this(null);
	}
	
	/**
	 * @功能描述 指定数据类型的构造函数
	 * @param reg 正则表达式
	 * @作者 乌卒卒
	 * @日期 2015-09-13
	 */
	public FormatInputField(String reg)
	{
		super();
		this.regex = reg;
		
		if(this.regex != null)
		{
			if(FormatInputField.DT_DOUBLE.equals(this.regex))
				this.setScale(2);
			else if(FormatInputField.DT_INTEGER.equals(this.regex))
				this.setScale(0);
		}
		
		//为控件添加校验方法
		this.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent cmp) {
				//获取控件， 获取输入字符串
				JTextField txtField = (JTextField) cmp; 
				String txt = txtField.getText();
				
				//允许输入为空
				if(txt == null || "".equals(txt))
					return true;
				
				//输入不为空，且表达式不为空，则要检验
				if(regex != null)
				{
					boolean isMatch =  txt.matches(regex);
					if(!isMatch)
						JOptionPane.showMessageDialog(null, FormatInputField.this.getName() + "输入格式错误！", "提示", JOptionPane.INFORMATION_MESSAGE);
					
					return isMatch;
				}
				
				return true;
			}
		});
	
		this.addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				if(regex != null && 
						(regex.equals(FormatInputField.DT_DOUBLE) || 
								regex.equals(FormatInputField.DT_INTEGER)))
				{
					BigDecimal val = getBigDecimalValue();
					setBigDecimalValue(val);
				}
			}
			
			public void focusGained(FocusEvent e) {
				if(regex != null && 
						(regex.equals(FormatInputField.DT_DOUBLE) || 
								regex.equals(FormatInputField.DT_INTEGER)))
				{
					if(getBigDecimalValue().compareTo(BigDecimal.ZERO) == 0)
						setText("");
					
				}
			}
		});
	}
	
	/**
	 * @功能描述 返回BigDecimal类型的值
	 * @作者 乌卒卒
	 * @日期 2015-09-13
	 * @return
	 */
	public BigDecimal getBigDecimalValue()
	{
		String txt = this.getText();
		
		//值为空时，返回0
		if(txt == null || "".equals(txt))
			return BigDecimal.ZERO;
		
		BigDecimal val = new BigDecimal(txt);
		//有设置小数位时，返回设置小数位的值
		if(this.scale != -1)
			return val.setScale(scale, BigDecimal.ROUND_HALF_UP);
		
		return val;
	}
	
	/**
	 * @功能描述 设置BigDecimal类型的值
	 * @作者 乌卒卒
	 * @日期 2015-09-13
	 * @return
	 */
	public void setBigDecimalValue(BigDecimal val)
	{
		if(val == null)
		{
			this.setText("");
			return;
		}
		
		if(scale != -1)
		{
			this.setText(val.setScale(scale, BigDecimal.ROUND_HALF_UP).toString());
		}
		else
		{
			this.setText(val.toString());
		}
	}
	
	/**
	 * @功能描述 返回int类型的值
	 * @作者 乌卒卒
	 * @日期 2015-09-13
	 * @return
	 */
	public int getIntValue()
	{
		return getBigDecimalValue().intValue();
	}
	
	/**
	 * @功能描述 设置int类型的值
	 * @作者 乌卒卒
	 * @日期 2015-09-13
	 * @return
	 */
	public void setIntValue(int val)
	{
		this.setText(String.format("%d", val));
	}
	
	/**
	 * @功能描述 返回double类型的值
	 * @作者 乌卒卒
	 * @日期 2015-09-13
	 * @return
	 */
	public double getDoubleValue()
	{
		return getBigDecimalValue().doubleValue();
	}
	
	/**
	 * @功能描述 设置double类型的值
	 * @作者 乌卒卒
	 * @日期 2015-09-13
	 * @return
	 */
	public void setDoubleValue(double val)
	{
		setBigDecimalValue(new BigDecimal(val));
	}

	/**
	 * @功能描述 设置小数位
	 * @作者 乌卒卒
	 * @日期 2015-09-13
	 * @return
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	/**
	 * @功能描述 设置是否为必录， 必录项时显示为黄色， 不是必录项时显示白色
	 * @作者 乌卒卒
	 * @日期 2015-09-13
	 * @return
	 */
	public void setRequest(boolean isRequest)
	{
		if(isRequest)
			this.setBackground(Color.yellow);
		else
			this.setBackground(Color.white);
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
