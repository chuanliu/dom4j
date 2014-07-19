package dom4j.jaxb.util;

import java.util.Iterator;

import org.dom4j.Element;

import dom4j.jaxb.Dimension;
import dom4j.jaxb.Filter;
import dom4j.jaxb.Group;
import dom4j.jaxb.Measurement;
import dom4j.jaxb.Measurements;
import dom4j.jaxb.Metric;
import dom4j.jaxb.Pivot;
import dom4j.jaxb.Pivots;
import dom4j.jaxb.Profile;
import dom4j.jaxb.Source;


/**
 * @author Jack
 * Util class for filter config.
 *
 */
public class ParserUtil {
	/**
	 * parserMeasurement
	 * @param element
	 * @return
	 */
	public static Measurement parserMeasurement(Element element){
    	Measurement measurement = new Measurement();
    	measurement.setName(element.attribute("name").getText());
    	measurement.setObjectModel(element.attribute("object-model").getText());
    	measurement.setFunction(element.attribute("function").getText());
    	return measurement;
    }
	
	/**
	 * parserMeasurements
	 * @param element
	 * @return
	 */
	public static Measurements parserMeasurements(Element element){
    	Measurements measurements = new Measurements();
    	measurements.setMeasurement(parserMeasurement(element));
    	return measurements;
    }
	
	/**
	 * parserMetric
	 * @param element
	 * @return
	 */
	public static Metric parserMetric(Element element){
		Metric metric=new Metric();
		metric.setName(element.attribute("name").getText());
		metric.setObjectModel(element.attribute("object-model").getText());
		return metric;
	}
	
	/**
	 * parserSource
	 * @param element
	 * @return
	 */
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
	/**
	 * parserDimension
	 * @param element
	 * @return
	 */
	public static Dimension parserDimension(Element element){
		Dimension dimension=new Dimension();
		dimension.setName(element.attribute("name").getText());
		Iterator<Element> elementMetricIter = element.elementIterator();
			while(elementMetricIter.hasNext()){
				Element elementMetric = elementMetricIter.next();
				dimension.getMetric().add(ParserUtil.parserMetric(elementMetric));
			}
		return dimension;
	}
	
	/**
	 * parserPivot
	 * @param element
	 * @return
	 */
	public static Pivot parserPivot(Element element){
		Pivot pivot=new Pivot();
		pivot.setName(element.attribute("name").getText());
		pivot.setObjectModel(element.attributeValue("object-model"));
		pivot.setDescription(element.attributeValue("description"));
		Iterator<Element> elementsubIter = element.elementIterator();
		while(elementsubIter.hasNext()){
			Element elementPivotSub = elementsubIter.next();
			if("dimensions".equals(elementPivotSub.getName())){
				pivot.getDimensions().setOrder(elementPivotSub.attributeValue("order"));
				Iterator<Element> elementGroupIter = elementPivotSub.elementIterator();
				while(elementGroupIter.hasNext()){
					Element group = elementGroupIter.next();
					pivot.getDimensions().getGroup().add(ParserUtil.parserGroup(group));
				}
				
			}
			if("measurements".equals(elementPivotSub.getName())){
				Iterator<Element> elementdimensionsIter = elementPivotSub.elementIterator();
				while (elementdimensionsIter.hasNext()){
					Element elementMeasurement = elementdimensionsIter.next();
					pivot.getMeasurements().setMeasurement(ParserUtil.parserMeasurement(elementMeasurement));
				}
				
			}
		}
		return pivot;
	}
	
	/**
	 * parserPivots
	 * @param element
	 * @return
	 */
	public static Pivots parserPivots(Element element){
		Pivots pivots=new Pivots();
		Iterator<Element> elementPivotsIter = element.elementIterator();
		while(elementPivotsIter.hasNext()){
			Element pivotElement = elementPivotsIter.next();
			pivots.getPivot().add(ParserUtil.parserPivot(pivotElement));
		}
		return pivots;
	}
	
	/**
	 * parserGroup
	 * @param element
	 * @return
	 */
	public static Group parserGroup(Element element){
		Group group=new Group();
		Iterator<Element> elementDimensionIter = element.elementIterator();
		group.setDescription(element.attributeValue("description"));
		while(elementDimensionIter.hasNext()){
			Element elementDimension = elementDimensionIter.next();
			group.getDimension().add(ParserUtil.parserDimension(elementDimension));
		}
		return group;
	}
	
	public static Profile parserProfile(Element element){
		Profile profile=new Profile();
		profile.setName(element.attribute("name").getText());
		Iterator<Element> elementPivotsIter = element.elementIterator();
		Element Pivots = elementPivotsIter.next();
		profile.setPivots(ParserUtil.parserPivots(Pivots));;
		return profile;
	}
}
