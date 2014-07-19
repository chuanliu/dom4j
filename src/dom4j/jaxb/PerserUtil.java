package dom4j.jaxb;

import java.util.Iterator;

import org.dom4j.Element;


public class PerserUtil {
	
	public static Measurement parserMeasurement(Element element){
    	Measurement measurement = new Measurement();
    	measurement.setName(element.attribute("name").getText());
    	measurement.setName(element.attribute("object-model").getText());
    	measurement.setName(element.attribute("function").getText());
    	return measurement;
    }
	
	public static Measurements parserMeasurements(Element element){
    	Measurements measurements = new Measurements();
    	measurements.setMeasurement(parserMeasurement(element));
    	return measurements;
    }
	
	public static Metric parserMetric(Element element){
		Metric metric=new Metric();
		metric.setName(element.attribute("name").getText());
		metric.setObjectModel(element.attribute("object-model").getText());
		return metric;
	}
	public static Source parserSource(Element element){
		Source source=new Source();
		source.setName(element.attributeValue("name"));
		source.setObjectModel(element.attributeValue("object-model"));
		Iterator<Element> elementsubIter = element.elementIterator();
		while(elementsubIter.hasNext()){
			Element subelement = elementsubIter.next();
			if (subelement.getName().equals("properties")){
				Iterator<Element> elementProperty = element.elementIterator();
				while(elementProperty.hasNext()){
					Element propertyElement= elementProperty.next();
					source.getProperties().setProperty(propertyElement.getText());
				}
				
			}else if (subelement.getName().equals("filters")){
				Iterator<Element> elementfilters = element.elementIterator();
				while(elementfilters.hasNext()){
					Element elementFilter = elementfilters.next();
					Filter filter = new Filter();
					filter.setFilter(elementFilter.getText());
					source.getFilters().add(filter);
				}
			}
		}
		return source;
	}
	public static Dimension parserDimension(Element element){
		Dimension dimension=new Dimension();
		Iterator<Element> elementsubIter = element.elementIterator();
		while(elementsubIter.hasNext()){
			Element elementDimension = elementsubIter.next();
			dimension.setName(elementDimension.attributeValue("name"));
			
			Iterator<Element> elementMetricIter = elementDimension.elementIterator();
			while(elementMetricIter.hasNext()){
				Element elementMetric = elementMetricIter.next();
				dimension.getMetric().add(PerserUtil.parserMetric(elementMetric));
			}
			
		}
		
		return dimension;
	}
	public static Pivot parserPivot(Element element){
		Pivot pivot=new Pivot();
		pivot.setName(element.attribute("name").getText());
		pivot.setName(element.attributeValue("object-model"));
		pivot.setName(element.attributeValue("description"));
		Iterator<Element> elementsubIter = element.elementIterator();
		while(elementsubIter.hasNext()){
			Element elementPivotSub = elementsubIter.next();
			if("dimensions".equals(elementPivotSub.getName())){
				pivot.getDimensions().setOrder(elementPivotSub.attributeValue("order"));
				Iterator<Element> elementGroupIter = elementPivotSub.elementIterator();
				while(elementGroupIter.hasNext()){
					Element group = elementGroupIter.next();
					pivot.getDimensions().getGroup().add(PerserUtil.parserGroup(group));
				}
				
			}
			if("measurements".equals(elementPivotSub.getName())){
				Iterator<Element> elementdimensionsIter = elementPivotSub.elementIterator();
				while (elementdimensionsIter.hasNext()){
					Element elementMeasurement = elementdimensionsIter.next();
					pivot.getMeasurements().setMeasurement(PerserUtil.parserMeasurement(elementMeasurement));
				}
				
			}
		}
		return pivot;
	}
	
	public static Pivots parserPivots(Element element){
		Pivots pivots=new Pivots();
		Iterator<Element> elementPivotsIter = element.elementIterator();
		while(elementPivotsIter.hasNext()){
			Element pivotElement = elementPivotsIter.next();
			pivots.getPivot().add(PerserUtil.parserPivot(pivotElement));
		}
		return pivots;
	}
	public static Group parserGroup(Element element){
		Group group=new Group();
		Iterator<Element> elementDimensionIter = element.elementIterator();
		group.setDescription(element.attributeValue("description"));
		while(elementDimensionIter.hasNext()){
			Element elementDimension = elementDimensionIter.next();
			group.getDimension().add(PerserUtil.parserDimension(elementDimension));
		}
		return group;
	}

}
