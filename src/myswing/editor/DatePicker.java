package myswing.editor;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;

import common.util.DateTimeUtil;
import listener.DataChangeListener;

public class DatePicker extends JTextField
{
	private static final long serialVersionUID = 1L;
	
	DateChooser dc_date = null;
	
	public DatePicker()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = df.format(new Date());
		Font font = new Font("宋体",Font.PLAIN, 15);
		
		dc_date = DateChooser.getInstance("yyyy-MM-dd");
		dc_date.register(this);
		this.setText(dateStr);
		this.setEditable(false);
		this.setFont(font);
	}
	
	public String getDateStr()
	{
		return this.getText();
	}
	
	public Date getDate() throws Exception
	{
		return DateTimeUtil.parseStr(getDateStr());
	}
	
	public void setDate(Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = df.format(date);
		this.setText(dateStr);
	}
	
	public void setDate(String dateStr)
	{
		this.setText(dateStr);
	}

	public void setEnable(boolean flag)
	{
		this.setEnabled(flag);
	}
	
	public void addDataChangeListener(DataChangeListener ls)
	{
		dc_date.addDataChangeListener(ls);
	}
	
	public void showDatePane()
	{
		dc_date.showDatePane();
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
}
