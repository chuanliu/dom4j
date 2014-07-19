package dom4j.jaxb.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import dom4j.jaxb.FilterConfig;

/**
 * @author Jack
 * user dom4j to parser xml filter config.
 */
public class ParserConfig {

	public void perserDocument() throws IOException {
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
			FilterConfig filterConfig = XML2java(document.getRootElement());
//			String string =document.asXML();
//			System.out.println(string);
			
			
//			filterConfig.getProfile().getPivots().getPivot().forEach(p->p.getDimensions().getGroup().
//					forEach(x->x.getDimension().forEach(y->System.out.println(y.getName()))));
//			filterConfig.getProfile().getPivots().getPivot().forEach(p->System.out.println(p.getMeasurements().getMeasurement().getName()));
//			filterConfig.getProfile().getPivots().getPivot().forEach(p->p.getDimensions().getGroup().
//					forEach(x->x.getDimension().forEach(y->y.getMetric().forEach(z->System.out.println(z.getName())))));
//			filterConfig.getProfile().getPivots().getPivot().forEach(p->System.out.println(p.getName()));
//			System.out.println(filterConfig.getProfile().getName());
//			filterConfig.getPivot().forEach(p->System.out.println(p.getObjectModel()));
			long end=System.nanoTime();
			System.out.println(String.format("All took: %d ms", TimeUnit.NANOSECONDS.toMillis(end-start)));
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
				filterConfig.getDimension().add(ParserUtil.parserDimension(element));
			} else if (element.getName().equals("measurement")) {
				filterConfig.setMeasurement(ParserUtil.parserMeasurement(element));
			} else if (element.getName().equals("pivot")) {
				filterConfig.getPivot().add(ParserUtil.parserPivot(element));
			} else if (element.getName().equals("profile")) {
				filterConfig.setProfile(ParserUtil.parserProfile(element));
				}
			}
		return filterConfig;
		}
	
	public static void main(String[] argv) throws IOException {
		ParserConfig dom4jParser = new ParserConfig();
		dom4jParser.perserDocument();
	}
}
