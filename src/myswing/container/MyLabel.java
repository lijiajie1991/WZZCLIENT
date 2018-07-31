package myswing.container;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

public class MyLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	
	private boolean isUnderLine = false;
	
	public MyLabel(String txt) {
		super(txt);
	}

	public MyLabel() {
		super();
		
	}

	public MyLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		
	}

	public MyLabel(Icon image) {
		super(image);
	}

	public MyLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
	}

	public MyLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
	}
	

	public void setUnderLine(boolean isUnderLine) {
		this.isUnderLine = isUnderLine;
	}

	protected void paintComponent(Graphics g) {  
        super.paintComponent(g);  
       
        if(isUnderLine)
        {
	        int y = getHeight() - 1;  
	        g.setColor(Color.black);  
	        g.drawLine(0, y, getWidth(), y);  
        }
    } 
}
