package dom4j.jaxb.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

import dom4j.jaxb.FilterConfig;

/**
 * @author Jack
 * user dom4j to parser xml filter config.
 */
public class CopyOfParserConfig {

	public void perserDocument() throws IOException, JAXBException, ParserConfigurationException, SAXException {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File("D:/config.xml"));
			Element root = document.getRootElement();
			Map<String, Element> elementMap = new HashMap();
			/**
			 * Get the FilterConfig
			 */
			TreeView(root, elementMap);
			
			JAXBContext jc = JAXBContext.newInstance(FilterConfig.class);
	        SAXParserFactory spf = SAXParserFactory.newInstance();
	        spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
	        XMLReader xmlReader = spf.newSAXParser().getXMLReader();
	        String xmlStr = document.asXML(); 
	        StringReader sr = new StringReader(xmlStr); 
	        InputSource is = new InputSource(sr);
	        
	        SAXSource source = new SAXSource(xmlReader, is);
	        Unmarshaller unmarshaller = jc.createUnmarshaller();
	        FilterConfig filterConfig = (FilterConfig) unmarshaller.unmarshal(source);
			
//			filterConfig.getProfile().getPivots().getPivot().forEach(p->p.getDimensions().getGroup().
//			forEach(x->x.getDimension().forEach(y->System.out.println(y.getName()))));
//	filterConfig.getProfile().getPivots().getPivot().forEach(p->System.out.println(p.getMeasurements().getMeasurement().getName()));
//	filterConfig.getProfile().getPivots().getPivot().forEach(p->p.getDimensions().getGroup().
//			forEach(x->x.getDimension().forEach(y->y.getMetric().forEach(z->System.out.println(z.getName())))));
//	filterConfig.getProfile().getPivots().getPivot().forEach(p->System.out.println(p.getName()));
//	System.out.println(filterConfig.getProfile().getName());
//	filterConfig.getPivot().forEach(p->System.out.println(p.getObjectModel()));
	filterConfig.getDimension().forEach(p->p.getMetric().forEach(s->System.out.println(s.getObjectModel())));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Change the ref to the reference node, to get a middle xml tree. 
	 * Regard name as a bean, and a ref as a reference to a bean.
	 * @param element
	 * @param elementMap
	 */

	public static void TreeView(Element element, Map<String, Element> elementMap) {
		/**
		 * map the node with name as a map.
		 */
		if (element.attribute("name") != null) {
			elementMap.put(element.attribute("name").getText(), element);
			Iterator<Element> elementsubIter = element.elementIterator();
			while (elementsubIter.hasNext()) {
				TreeView((Element) elementsubIter.next(), elementMap);
			}

			/**
			 * ref must be a leaf node and the bean must be define before the ref
			 * and replace the ref with the one reference.
			 */
		} else if (element.attribute("ref") != null
				&& !"".equals(element.attribute("ref").getText())) {
			Element elementParent = element.getParent();
			elementParent.add((Element) elementMap.get(
					element.attribute("ref").getText()).clone());
			elementParent.remove(element);
			/**
			 * No name and ref node do nothing.
			 */
		} else {
			Iterator<Element> elementsubIter = element.elementIterator();
			while (elementsubIter.hasNext()) {
				TreeView((Element) elementsubIter.next(), elementMap);
			}
		}
	}

	/**
	 * convert a xml FilterConfig to java FilterConfig class
	 * the xml must reference the xsd.
	 * @param root
	 * @return
	 */
	public static FilterConfig XML2java(Element root) {

		FilterConfig filterConfig = new FilterConfig();
		Iterator rootIter = root.elementIterator();
		while (rootIter.hasNext()) {
			Element element = (Element) rootIter.next();
			if (element.getName().equals("source")) {
				filterConfig.setSource(ParserUtil.parserSource(element));
			} else if (element.getName().equals("dimension")) {
				Iterator<Element> elementsubIter = element.elementIterator();
				while (elementsubIter.hasNext()) {
					filterConfig.getDimension().add(
							ParserUtil.parserDimension(elementsubIter.next()));
				}
			} else if (element.getName().equals("measurement")) {
				filterConfig.setMeasurement(ParserUtil
						.parserMeasurement(element));
			} else if (element.getName().equals("pivot")) {
				filterConfig.getPivot().add(ParserUtil.parserPivot(element));
			} else if (element.getName().equals("profile")) {
				filterConfig.getProfile().setName(element.attributeValue("name"));
				Iterator<Element> elementsubIter = element.elementIterator();
				while (elementsubIter.hasNext()) {
					filterConfig.getProfile().setPivots(ParserUtil.parserPivots(elementsubIter.next()));
				}
			}
		}

		return filterConfig;
	}
	
	public static void main(String[] argv) throws IOException, JAXBException, ParserConfigurationException, SAXException {
		CopyOfParserConfig dom4jParser = new CopyOfParserConfig();
		dom4jParser.perserDocument();
	}
}
