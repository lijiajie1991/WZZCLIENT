package myswing.editor;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class MyComboBox extends JComboBox{
	private static final long serialVersionUID = 1L;
	

	/**
	 * @功能描述 默认构造函数
	 * @作者 乌卒卒
	 * @日期 2015-09-13
	 */
	public MyComboBox()
	{
		super();
		//设置最大显示数为6
		this.setMaximumRowCount(6);
		//设置不可编辑
		this.setEditable(false);
	}
	
	/**
	 * @功能描述 默认构造函数
	 * @作者 乌卒卒
	 * @日期 2015-09-13
	 */
	public MyComboBox(boolean flag)
	{
		super();
		//设置最大显示数为6
		this.setMaximumRowCount(6);
		//设置不可编辑
		this.setEditable(flag);
	}

	/**
	 * @功能描述 添加多个项
	 * @作者 乌卒卒
	 * @日期 2015-09-13
	 * @param items
	 */
	@SuppressWarnings("unchecked")
	public void addItems(Object[] items)
	{
		DefaultComboBoxModel model = new DefaultComboBoxModel(items);
		this.setModel(model);
		this.setSelectedIndex(-1);
	}
	
	public void addItem(Object obj) {
		int index = ((DefaultComboBoxModel)this.getModel()).getIndexOf(obj);
		if(index < 0)
			super.addItem(obj);
	}
	
	
}
