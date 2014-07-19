package dom4j.jaxb;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class PerserConfig {

	public void perserDocument() throws IOException {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File("D:/config.xml"));
			Element root = document.getRootElement();
			Iterator rootIter = root.elementIterator();
			Map<String, Element> elementMap = new HashMap();
			while (rootIter.hasNext()) {
				Element element = (Element) rootIter.next();
				if (element.attribute("name") != null) {
					elementMap
							.put(element.attribute("name").getText(), element);
				} else if (element.attribute("ref") != null) {
					root.add(elementMap.get(element.attribute("ref").getText()));
					root.remove(element);
				}
				TreeView(element, elementMap);

			}
			FilterConfig filterConfig = XML2java(document.getRootElement());
//			filterConfig.getPivot().forEach(p->);
			
			filterConfig.getProfile().getPivots().getPivot().forEach(p->p.getDimensions().getGroup().
					forEach(x->x.getDimension().forEach(y->System.out.println(y.getName()))));
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static void TreeView(Element element, Map<String, Element> elementMap) {
		if (element.attribute("name") != null) {
			elementMap.put(element.attribute("name").getText(), element);
			Iterator<Element> elementsubIter = element.elementIterator();
			while (elementsubIter.hasNext()) {
				TreeView((Element) elementsubIter.next(), elementMap);
			}

		} else if (element.attribute("ref") != null
				&& !"".equals(element.attribute("ref").getText())) {
			Element elementParent = element.getParent();
			elementParent.add((Element) elementMap.get(
					element.attribute("ref").getText()).clone());
			elementParent.remove(element);
		} else {
			Iterator<Element> elementsubIter = element.elementIterator();
			while (elementsubIter.hasNext()) {
				TreeView((Element) elementsubIter.next(), elementMap);
			}
		}
	}

	public static FilterConfig XML2java(Element root) {

		FilterConfig filterConfig = new FilterConfig();
		Iterator rootIter = root.elementIterator();
		while (rootIter.hasNext()) {
			Element element = (Element) rootIter.next();
			if (element.getName().equals("source")) {
				filterConfig.setSource(PerserUtil.parserSource(element));
			} else if (element.getName().equals("dimension")) {
				Iterator<Element> elementsubIter = element.elementIterator();
				while (elementsubIter.hasNext()) {
					filterConfig.getDimension().add(
							PerserUtil.parserDimension(elementsubIter.next()));
				}
			} else if (element.getName().equals("measurement")) {
				filterConfig.setMeasurement(PerserUtil
						.parserMeasurement(element));
			} else if (element.getName().equals("pivot")) {
				filterConfig.getPivot().add(PerserUtil.parserPivot(element));
			} else if (element.getName().equals("profile")) {
				
				filterConfig.getProfile().setName(element.attributeValue("name"));
				Iterator<Element> elementsubIter = element.elementIterator();
				while (elementsubIter.hasNext()) {
					filterConfig.getProfile().setPivots(PerserUtil.parserPivots(elementsubIter.next()));
				}
			}
		}

		return filterConfig;
	}

	public static void main(String[] argv) throws IOException {
		PerserConfig dom4jParser = new PerserConfig();
		dom4jParser.perserDocument();
	}
}
