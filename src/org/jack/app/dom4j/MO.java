package org.jack.app.dom4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MO {
	private String className;
	private String fdn;
	private Map mapAttr=new HashMap();
	private List<MO> listMO=new ArrayList<MO>();
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getFdn() {
		return fdn;
	}
	public void setFdn(String fdn) {
		this.fdn = fdn;
	}
	public Map getMapAttr() {
		return mapAttr;
	}
	public void setMapAttr(Map mapAttr) {
		this.mapAttr = mapAttr;
	}
	public List<MO> getListMO() {
		return listMO;
	}
	public void setListMO(List<MO> listMO) {
		this.listMO = listMO;
	}
	
	
}
