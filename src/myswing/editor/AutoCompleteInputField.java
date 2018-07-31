package myswing.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AutoCompleteInputField extends JTextField{
	private static final long serialVersionUID = 1L;

	protected boolean isUpdating = false;
	protected boolean isSelecting = false;
	
	
	protected DefaultComboBoxModel<Object> model = null;
	protected JComboBox<Object> cmb = null;
	
	public AutoCompleteInputField() {
		super();
		
		model = new DefaultComboBoxModel<Object>();
		cmb = new JComboBox<Object>(model)
		{
			private static final long serialVersionUID = 1L;

			public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 0);
            }
        };
        cmb.setSelectedItem(null);
        cmb.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					if (!isUpdating && cmb.getSelectedItem() != null) {
						isSelecting = true;
	                	AutoCompleteInputField.this.setText(cmb.getSelectedItem().toString());				
					}
				}
			}
        }
		);
        
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (cmb.isPopupVisible()) {
                        e.setKeyCode(KeyEvent.VK_ENTER);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER
                        || e.getKeyCode() == KeyEvent.VK_UP
                        || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    e.setSource(cmb);
                    cmb.dispatchEvent(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    	AutoCompleteInputField.this.setText(cmb.getSelectedItem().toString());
                    	cmb.setPopupVisible(false);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cmb.setPopupVisible(false);
                }
            }
        });
        this.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateList();
            }

            public void removeUpdate(DocumentEvent e) {
                updateList();
            }

            public void changedUpdate(DocumentEvent e) {
                updateList();
            }

        });
        
        this.setLayout(new BorderLayout());
        this.add(cmb, BorderLayout.SOUTH);
	}

	protected void updateList()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(isSelecting){
						isSelecting = false;
						cmb.setPopupVisible(false);
						return;
					}
					
					isUpdating = true;
					model.removeAllElements();
			         LinkedList<Object> list = getList("");
			         for(Object obj : list)
			        	 cmb.addItem(obj);
			         cmb.setPopupVisible(model.getSize() > 0);
			         
			         model.removeAllElements();
			         String txt = AutoCompleteInputField.this.getText();
			         list = getList(txt);
			         for(Object obj : list)
			        	 cmb.addItem(obj);
			         
			         boolean isMatch = false;
			         int listSize = list.size();
			         if(listSize == 1){
			        	 Object obj = list.get(0);
			        	 isMatch = isPerfectMatch(txt, obj);
			         }
			         
			         cmb.setPopupVisible(false);
			         if(!isMatch){
			        	 cmb.setPopupVisible(true);
			        	 cmb.setSelectedItem(null);
			         }

			         isUpdating = false;
			         
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	protected LinkedList<Object> getList(String txt) throws Exception
	{
		LinkedList<Object> list = new LinkedList<Object>();
		return list;
	}
	
	protected boolean isPerfectMatch(String txt, Object obj) throws Exception
	{
		return false;
	}
	
	public JComboBox<Object> getCmb()
	{
		return this.cmb;
	}

}
