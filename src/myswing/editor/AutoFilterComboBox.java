package myswing.editor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.LinkedList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;

public class AutoFilterComboBox<E> extends JComboBox<E>{
	private static final long serialVersionUID = 1L;
	
	//可选数据
	protected LinkedList<E> data = null;
	//过滤出来的数据
	protected LinkedList<E> list = null;

	protected CmbModel model = null;
	protected CmbEditor editor = null;
	
	public AutoFilterComboBox()
	{
		super();
		data = new LinkedList<E>();
		list = new LinkedList<E>();
		
		init();
		addListener();
	}
	
	protected void init()
	{
		editor = new CmbEditor();
		this.setEditor(editor);
		
		model = new CmbModel();
		this.setModel(model);
		
		//设置不可编辑
		this.setEditable(true);
	}
	
	@SuppressWarnings("unchecked")
	public void addItem(E obj)
	{
		data.addLast(obj);
		super.addItem(obj);
	}
	
	public void addItems(E[] objs)
	{
		if(objs != null && objs.length > 0)
		{
			for(E obj : objs)
			{
				this.addItem(obj);	
			}
		}
	}
	
	public void addSelectItems(E[] objs)
	{
		super.removeAllItems();
		if(objs != null && objs.length > 0)
		{
			for(E obj : objs)
			{
				super.addItem(obj);		
			}
		}
	}
	
	protected void addListener(){
		JTextField txt = this.getEditorTextField();
		txt.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
            	if(e.getKeyCode() == KeyEvent.VK_SPACE)
            		e.setKeyCode(KeyEvent.VK_ENTER);
            	
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                	AutoFilterComboBox.this.setPopupVisible(false);
                }
                else
                {
                	updateList();
                	AutoFilterComboBox.this.setPopupVisible(true);
                }
            }
        });
	}
	
	
	public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, 0);
    }
	
	protected void updateList()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			        String txt = getText();
			        System.out.println("更新列表：" + txt);
			        LinkedList<E> list = getList(txt);
			        model.setDataList(list);
			        AutoFilterComboBox.this.setPopupVisible(model.getSize() > 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
		
	}
	
	
	/**
	  * @功能描述 输入文本后调用的查询方法
	  * @作者 黎嘉杰 
	  * @日期 2016年12月16日 下午2:34:35 
	  * @参数 
	  * @返回
	 */
	protected LinkedList<E> getList(String txt)
	{
		LinkedList<E> list = new LinkedList<E>();
		if(data != null && !data.isEmpty())
		{
			for(E obj : data)
			{
				if(isMatch(txt, obj))
					list.addLast(obj);
			}
		}
		return list;
	}
	
	/**
	  * @功能描述 判断是否符合过滤条件的方法， 使用时一般需要重载
	  * @作者 黎嘉杰 
	  * @日期 2016年12月16日 下午2:34:35 
	  * @参数 
	  * @返回
	 */
	protected boolean isMatch(String txt, Object obj)
	{
		if(obj != null && obj.toString().contains(txt))
			return true;
		
		return false;
	}
	
	/**
	  * @功能描述 获取编辑的文本输入框
	  * @作者 黎嘉杰 
	  * @日期 2016年12月16日 下午2:34:35 
	  * @参数 
	  * @返回
	 */
	protected JTextField getEditorTextField()
	{
		if(editor != null)
			return (JTextField) editor.getEditorComponent();
		return null;
	}
	
	/**
	  * @功能描述 获取编辑框的文本
	  * @作者 黎嘉杰 
	  * @日期 2016年12月16日 下午2:34:35 
	  * @参数 
	  * @返回
	 */
	protected String getText()
	{
		JTextField txt = getEditorTextField();
		return txt.getText();
	}
	
	/**
	  * @功能描述 设置编辑框的文本
	  * @作者 黎嘉杰 
	  * @日期 2016年12月16日 下午2:34:35 
	  * @参数 
	  * @返回
	 */
	protected void setText(String text)
	{
		JTextField txt = getEditorTextField();
		txt.setText(text);
	}
	
	
	
	protected class CmbEditor implements ComboBoxEditor
	{
		protected JTextField txt = null;
		protected Object obj = null;
		
		public CmbEditor()
		{
			super();
			txt = new JTextField();
		}
		
		public Component getEditorComponent() {
			return txt;
		}

		public void setItem(Object obj) {
			this.obj = obj;
		}

		public Object getItem() {
			return obj;
		}

		public void selectAll() {
		}

		public void addActionListener(ActionListener ls) {
		}

		public void removeActionListener(ActionListener l) {
		}
		
	}
	
	protected class CmbModel extends AbstractListModel<E> implements MutableComboBoxModel<E>, Serializable
	{
		private static final long serialVersionUID = 1L;
		
		protected LinkedList<E> list = null;;
	    protected Object item = null;

	    /**
	     * Constructs an empty DefaultComboBoxModel object.
	     */
	    public CmbModel() {
	        super();
	        list = new LinkedList<E>();
	    }

	    
	    public void setSelectedItem(Object obj) {
	    	if(item == null && obj == null)
	    		return;
	    	
	    	if((item == null || obj == null) || (item != null && obj != null))
	    	{
	    		item = obj;
	    		fireContentsChanged(this, -1, -1);
	    		
	    		if(item != null)
	    			setText(item.toString());
	    	}
	    }

	    public Object getSelectedItem() {
	        return item;
	    }

	    public int getSize() {
	        return list.size();
	    }

	    public E getElementAt(int index) {
	    	if(index >=  0 && list.size() > index)
	    		return list.get(index);
	    	return null;
	    }

	    public int getIndexOf(Object obj) {
	        return list.indexOf(obj);
	    }

	    public void addElement(E obj) {
	    	list.addLast(obj);
	        fireIntervalAdded(this,list.size()-1, list.size()-1);
	    }
	    
	    public void setDataList(LinkedList<E> list)
	    {
	    	this.removeAllElements();
	    	item = null;
	    	if(list != null)
	    	{
	    		this.list = list;
	    		int firstIndex = 0;
	            int lastIndex = list.size() - 1;
	    		fireIntervalRemoved(this, firstIndex, lastIndex);
	    	}
	    }

	    public void insertElementAt(E obj,int index) {
	    	list.add(index, obj);
	        fireIntervalAdded(this, index, index);
	    }

	    public void removeElementAt(int index) {
	    	E obj = this.getElementAt(index);
	    	if(obj != null)
	    	{
	    		if(item != null && item.equals(obj))
	    			this.setSelectedItem(null);
	    			
	    		list.remove(index);
	    		fireIntervalRemoved(this, index, index);
	    	}
	    }

	    public void removeElement(Object obj) {
	        int index = list.indexOf(obj);
	        if ( index != -1 ) {
	            removeElementAt(index);
	        }
	    }

	    /**
	     * Empties the list.
	     */
	    public void removeAllElements() {
	        if (list.size() > 0 ) {
	            int firstIndex = 0;
	            int lastIndex = list.size() - 1;
	            list.clear();
	            item = null;
	            fireIntervalRemoved(this, firstIndex, lastIndex);
	        } else {
	            item = null;
	        }
	    }

	}
}
