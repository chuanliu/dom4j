package org.jack.app.config;

import java.util.ArrayList;
import java.util.List;

public class Source {
	private String name;
	private String objectModel;
	private List <String> propertys = new ArrayList();
	private List <String> filters = new ArrayList();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getObjectModel() {
		return objectModel;
	}
	public void setObjectModel(String objectModel) {
		this.objectModel = objectModel;
	}
	public List<String> getPropertys() {
		return propertys;
	}
	public void setPropertys(List<String> propertys) {
		this.propertys = propertys;
	}
	public List<String> getFilters() {
		return filters;
	}
	public void setFilters(List<String> filters) {
		this.filters = filters;
	}
	
	

}
