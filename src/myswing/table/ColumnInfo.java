package myswing.table;

import em.RpDataType;
import em.RptAlign;

public class ColumnInfo{
	protected String keyStr = null;
	protected String name = null;
	protected String val = null;
	protected int width = 0;
	protected RptAlign align = RptAlign.CENTER;
	protected RpDataType dt = RpDataType.STR;
	protected Class<?> cls = null;
	
	protected boolean isSum = false;
	protected int level = 0;

	public ColumnInfo(String keyStr, String name)
	{
		this.keyStr = keyStr.toLowerCase();
		this.name = name;
		this.val = keyStr;
		this.width = 100;
		this.align = RptAlign.CENTER;
		this.dt = RpDataType.STR;
	}
	
	public ColumnInfo(String keyStr, String name, int width)
	{
		this(keyStr, name);
		this.width = width;
	}
	
	public ColumnInfo(String keyStr, String name, int width, Class<?> cls)
	{
		this(keyStr, name, width);
		this.cls = cls;
	}
	
	public ColumnInfo(String keyStr, String name, int width, Class<?> cls, RptAlign align)
	{
		this(keyStr, name, width);
		this.cls = cls;
		this.align = align;
	}
	
	public ColumnInfo(String keyStr, String name, RpDataType dt, Class<?> cls, boolean isSum)
	{
		this(keyStr, name);
		this.dt = dt;
		this.cls = cls;
		this.isSum = isSum;
	}

	public String getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public RptAlign getAlign() {
		return align;
	}

	public void setAlign(RptAlign align) {
		this.align = align;
	}

	public RpDataType getDt() {
		return dt;
	}

	public void setDt(RpDataType dt) {
		this.dt = dt;
	}

	public boolean getIsSum() {
		return isSum;
	}

	public void setIsSum(boolean isSum) {
		this.isSum = isSum;
	}
	
	

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Class<?> getCls() {
		return cls;
	}

	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
	
	public String toString()
	{
		return this.getName();
	}
}
