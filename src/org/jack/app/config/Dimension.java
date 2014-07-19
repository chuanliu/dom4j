package org.jack.app.config;

import java.util.ArrayList;
import java.util.List;

public class Dimension {
	private String name;
	private List <Metic> metrics= new ArrayList();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Metic> getMetrics() {
		return metrics;
	}
	public void setMetrics(List<Metic> metrics) {
		this.metrics = metrics;
	}
	
}
