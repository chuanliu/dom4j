package org.jack.app.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterConfig {
	private Source sourse;
	private List<Dimension> dimension = new ArrayList<Dimension>();
	private Measurement measurement;
	private Map<String, Pivot> pivot = new HashMap<String, Pivot>();
	private List<String> privotsref = new ArrayList<String>();

	public Source getSourse() {
		return sourse;
	}

	public void setSourse(Source sourse) {
		this.sourse = sourse;
	}

	public List<Dimension> getDimension() {
		return dimension;
	}

	public void setDimension(List<Dimension> dimension) {
		this.dimension = dimension;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	public Map<String, Pivot> getPivot() {
		return pivot;
	}

	public void setPivot(Map<String, Pivot> pivot) {
		this.pivot = pivot;
	}

	public List<String> getPrivotsref() {
		return privotsref;
	}

	public void setPrivotsref(List<String> privotsref) {
		this.privotsref = privotsref;
	}

}
