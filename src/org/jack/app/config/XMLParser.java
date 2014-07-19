package org.jack.app.config;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLParser {
//	public static void parse() throws Exception {
//		SAXReader reader = new SAXReader();
//		long start=System.nanoTime();
//		Document document = reader.read(new File(
//				"src/config.xml"));
//		Element root = document.getRootElement();
//		Iterator<Element> it = root.elementIterator();
//		FilterConfig filterConfig=new FilterConfig();
//		while (it.hasNext()) {
//			Element element = it.next();
//			if(element.getName().equals("source")){
//				filterConfig.setSource(parserSource(element)) ;
//			}
//		}
//	}

//	private static Source parserSource(Element element) {
//		
//		Source source=new Source();
//		Attribute name= element.attribute("name");
//		Attribute objectModel= element.attribute("object-model");
//		source.setName(name.getText());
//		source.setObjectModel(objectModel.getText());
//		Iterator<Element> itForSource = element.elementIterator();
//		while (itForSource.hasNext()){
//			Element sourceElement = itForSource.next();
//			if(sourceElement.getName().equals("properties")){
//				Iterator<Element> sourceElementProperties = sourceElement.elementIterator();
//				while(sourceElementProperties.hasNext()){
//					Element sourcePropertyElement = itForSource.next();
//					source.getProperties().setProperty(sourcePropertyElement.getText());
//				}
//			}else if(sourceElement.getName().equals("filters")){
//				Iterator<Element> sourceElementFilters = sourceElement.elementIterator();
//				while(sourceElementFilters.hasNext()){
//					Element sourceFilterElement = itForSource.next();
//					Filter filter =new Filter();
//					filter.setFilter(sourceFilterElement.getText());
//					source.getFilters().setFilter(new Filter());
//				}
//			}
//		}
//
//
//		return source;
//	}

//	private static MO listNodes(Element ele) {
//
//		MO mo = new MO();
//		Attribute className = ele.attribute("className");
//		Attribute fdn = ele.attribute("fdn");
//
//		mo.setClassName(className.getText());
//		mo.setFdn(fdn.getText());
//
//		Iterator<Element> itForMO = ele.elementIterator();
//		while (itForMO.hasNext()) {
//			Element nextEle = itForMO.next();
//			if (nextEle.getName().equals("attr")) {
//				Attribute name = nextEle.attribute("name");
//				mo.getMapAttr().put(name.getText(), nextEle.getText());
//
//			} else if (nextEle.getName().equals("MO")) {
//				MO moC = listNodes(nextEle);
//				mo.getListMO().add(moC);
//			}
//			
//		}
//
//		return mo;
//	}

	public static void main(String[] args) throws Exception {
//		parse();
	}

}
