package report.crossrpt.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import report.base.bean.IHttpRpt;
import report.base.bean.RptColumnInfo;
import report.base.ui.RptUI;
import report.crossrpt.DynColumnInfo;
import report.crossrpt.info.CrossRptInfo;
import report.crossrpt.info.CrossSuffixInfo;
import report.crossrpt.info.CrossSumInfo;
import report.crossrpt.info.IHttpCrossRpt;

public class CrossRptUI extends RptUI {
	private static final long serialVersionUID = 1L;

	public CrossRptUI()
	{
		super();
	}
	
	public void query(HashMap<Object, Object> con) throws Exception {
		
		List<Map<String, Object>> list = getQueryData(con);
		CrossRptInfo rptInfo = (CrossRptInfo) this.info;
		
		LinkedList<RptColumnInfo> realCols = new LinkedList<RptColumnInfo>();
		List<Map<String, Object>> realData = new LinkedList<Map<String,Object>>();
		
		LinkedList<RptColumnInfo> colList = rptInfo.getColList();
		LinkedList<RptColumnInfo> ccList = rptInfo.getColList();
		LinkedList<CrossSumInfo> csList = rptInfo.getCsList();
		LinkedList<CrossSuffixInfo> sfList = rptInfo.getSuffixList();
		
		realCols.addAll(colList);
		
		//前面固定的列键
		LinkedList<String> dataKeyList = new LinkedList<String>();
		//前面固定列键的值
		HashMap<String, HashMap<String, Object>> dataMap = new HashMap<String, HashMap<String,Object>>();
		
		LinkedList<String> dynKeyList = new LinkedList<String>();
		HashMap<String, DynColumnInfo> dynColMap = new HashMap<String, DynColumnInfo>();
		for(Map<String, Object> map : list)
		{
			StringBuffer colKey = new StringBuffer();
			StringBuffer colName = new StringBuffer();
			for(RptColumnInfo colInfo : ccList){
				Object val = map.get(colInfo.getVal());
				val = val != null ? val : "";
				colKey.append(colInfo.getKeyStr() + "-");
				colName.append(val.toString() + "-");
			}
			colKey = colKey.replace(colKey.length() - 1, colKey.length(), "");
			colName = colName.replace(colName.length() - 1, colName.length(), "");
			
			for(CrossSumInfo colInfo : csList){
				String dynKey = colKey.toString() + "(" + colInfo.getKeyStr() + ")";
				String dynName = colName.toString() + "(" + colInfo.getName() + ")";
				dynKey = dynKey + dynName;
				if(!dynColMap.containsKey(dynName))
				{
					RptColumnInfo cInfo = new RptColumnInfo();
					cInfo.setKeyStr(dynKey);
					cInfo.setVal(dynName);
					cInfo.setName(dynName);
					cInfo.setWidth(colInfo.getWidth());
					cInfo.setAlign(colInfo.getAlign());
					cInfo.setDt(colInfo.getDt());
					cInfo.setIsSum(colInfo.getIsSum());
					
					DynColumnInfo dynColInfo = new DynColumnInfo();
					dynColInfo.setColInfo(cInfo);
					dynColInfo.setKey(dynKey);
					
					dynColMap.put(dynName, dynColInfo);
					dynKeyList.addLast(dynName);
				}
				
				map.put(dynKey, map.get(colInfo.getVal()));
			}
		}
		
		for(Map<String, Object> map : list)
		{
			StringBuffer dataKey = new StringBuffer();
			HashMap<String, Object> colMap = new HashMap<String, Object>();
			for(RptColumnInfo colInfo : colList){
				Object val = map.get(colInfo.getVal());
				String key = val != null ? val.toString() : "";
				
				dataKey.append(key + "-");
				colMap.put(colInfo.getVal(), val);
			}
			if(!dataMap.containsKey(dataKey.toString())){
				dataKeyList.addLast(dataKey.toString());
				dataMap.put(dataKey.toString(), colMap);
			}
			
			HashMap<String, Object> rowData = dataMap.get(dataKey.toString());
			for(String dynKey : dynKeyList)
			{
				DynColumnInfo dynCol = dynColMap.get(dynKey);
				RptColumnInfo cInfo = dynCol.getColInfo();
				String key = dynCol.getKey();
				Object obj = map.get(key);
				BigDecimal val = obj != null ? new BigDecimal(obj.toString()) : BigDecimal.ZERO;
				
				if(rowData.containsKey(cInfo.getVal()))
				{
					Object rd = rowData.get(cInfo.getVal());
					BigDecimal rdVal = rd != null ? new BigDecimal(rd.toString()) : BigDecimal.ZERO;	
					val = rdVal.add(val);
				}
				
				rowData.put(cInfo.getVal(), val);
			}
		}
		
		for(String dynKey : dynKeyList)
		{
			DynColumnInfo dynCol = dynColMap.get(dynKey);
			RptColumnInfo cInfo = dynCol.getColInfo();
			realCols.addLast(cInfo);
			
		}
		realCols.addAll(sfList);
		
		for(String dataKey : dataKeyList)
		{
			realData.add(dataMap.get(dataKey));
		}
		
		
		int size = realCols.size();
		String[] realColsName = new String[size];
		String[] realColsKey = new String[size];
		int[] realColsWidth = new int[size];
		int[] realColsAlign = new int[size];
		for(int i = 0; i < size; i++)
		{
			RptColumnInfo rptCol = realCols.get(i);
			realColsName[i] = rptCol.getName();
			realColsKey[i] = rptCol.getKeyStr();
			realColsWidth[i] = rptCol.getWidth();
			realColsAlign[i] = rptCol.getAlign().getValue();
		}
		
		tblMain.setColumnName(realColsName);
		tblMain.setColumnKey(realColsKey);
		tblMain.setColumnWidth(realColsWidth);
		tblMain.setAlign(realColsAlign);
		
		
		fillData(realData, realCols);
	}

	protected Class<?> getRptClass() {
		return CrossRptInfo.class;
	}

	protected IHttpRpt getHttpInstance() {
		return IHttpCrossRpt.getInstance();
	}

	protected Class<?> getInfoClass() {
		return CrossRptInfo.class;
	}
}
