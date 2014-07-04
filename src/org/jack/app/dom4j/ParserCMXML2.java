package org.jack.app.dom4j;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class ParserCMXML2 {
	public void modifyDocument() {
		try {
			long t0 = System.nanoTime();
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File("src/CM.xml"));
			// String stringXML = document.asXML();

			long t1 = System.nanoTime();
			long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
			System.out.println(String.format("read took: %d ms", millis));

			List<Element> list = document.selectNodes("//MOTree/MO/MO");

			Iterator iter = list.iterator();
			
			
			
			// while (iter.hasNext()) {
			// Element element = (Element) iter.next();
			// Iterator iterator = element.elementIterator("attr");
			// while (iterator.hasNext()) {
			// Element attrElement = (Element) iterator.next();
			// System.out.println(attrElement.asXML());
			// }
			//
			// System.out.println("------------------");
			//
			// }

			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				Iterator iterator = element.elementIterator("attr");
				while (iterator.hasNext()) {
					Element attrElement = (Element) iterator.next();
					System.out.println(attrElement.attribute("name").getValue()
							+ " : " + attrElement.getText());
				}
				System.out.println("------------------");

			}

			// while (iter.hasNext()) {
			// Element element = (Element) iter.next();
			// element.valueOf("@name");
			//
			// }
			//

			document.getRootElement();

			long t2 = System.nanoTime();
			long millis2 = TimeUnit.NANOSECONDS.toMillis(t2 - t1);
			System.out.println(String.format("select took: %d ms", millis2));

		}

		catch (DocumentException e) {
			e.printStackTrace();
		}
	}


	public static void treeWalk(Element element) {
		for (int i = 0, size = element.nodeCount(); i < size; i++) {
			Node node = element.node(i);
			if (node instanceof Element) {
				treeWalk((Element) node);
			} else { 
			}
		}
	}

	public static void main(String[] argv) {
		ParserCMXML2 dom4jParser = new ParserCMXML2();
		dom4jParser.modifyDocument();
	}
}
