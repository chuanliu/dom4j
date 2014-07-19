package dom4j.jaxb.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
import org.xml.sax.XMLReader;

import dom4j.jaxb.FilterConfig;

/**
 * @author Jack user dom4j to parser xml filter config.
 */
public class ParserConfigDomMarshaller {

	public void perserDocument() throws IOException, JAXBException,
			ParserConfigurationException, SAXException {
		try {
			
			long start=System.nanoTime();
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File("D:/config.xml"));
			Element root = document.getRootElement();
			Map<String, Element> elementMap = new HashMap();
			/**
			 * Get the FilterConfig
			 */
			TreeView(root, elementMap);
			
			long start2=System.nanoTime();
			
			JAXBContext jc = JAXBContext.newInstance(FilterConfig.class);
			
			long start3=System.nanoTime();
			SAXParserFactory spf = SAXParserFactory.newInstance();
//			spf.setFeature("http://apache.org/xml/features/validation/schema", false);
//		    spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			XMLReader xmlReader = spf.newSAXParser().getXMLReader();
			
			long start4=System.nanoTime();
			
			StringReader stringReader = new StringReader(document.asXML());
			
			long start5=System.nanoTime();
			
			InputSource inputSource = new InputSource(stringReader);
			SAXSource source = new SAXSource(xmlReader, inputSource);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			FilterConfig filterConfig = (FilterConfig) unmarshaller.unmarshal(source);
			long end=System.nanoTime();
			System.out.println(String.format("All took: %d ms", TimeUnit.NANOSECONDS.toMillis(start2-start3)));
//			filterConfig.getDimension().forEach(s->s.getMetric().forEach(p->System.out.println(p.getObjectModel())));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Change the ref to the reference node, to get a middle xml tree. Regard
	 * name as a bean, and a ref as a reference to a bean.
	 * 
	 * @param element
	 * @param elementMap
	 */

	public static void TreeView(Element element, Map<String, Element> elementMap) {
		/**
		 * map the node with name as a key and node as value.
		 */
		if (element.attribute("name") != null) {
			elementMap.put(element.attribute("name").getText(), element);
			Iterator<Element> elementsubIter = element.elementIterator();
			while (elementsubIter.hasNext()) {
				TreeView((Element) elementsubIter.next(), elementMap);
			}

			/**
			 * ref must be a leaf node and the bean must be define before the
			 * ref and replace the ref with the one reference.
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


	public static void main(String[] argv) throws IOException, JAXBException,
			ParserConfigurationException, SAXException {
		ParserConfigDomMarshaller dom4jParser = new ParserConfigDomMarshaller();
		dom4jParser.perserDocument();
	}
}
