package org.jack.app.dom4j;

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
	public static void parse() throws Exception {
		SAXReader reader = new SAXReader();
		long start=System.nanoTime();
		Document document = reader.read(new File(
				"src/hwcm_bigfile.xml"));
		Element root = document.getRootElement();
		Iterator<Element> it = root.elementIterator();
		MO mo=new MO();
		while (it.hasNext()) {
			Element MO = it.next();
			mo = listNodes(MO);
		}
		long end=System.nanoTime();
        System.out.println(String.format("sequential took: %d ms", TimeUnit.NANOSECONDS.toMillis(end - start)));
	}

	private static MO listNodes(Element ele) {

		MO mo = new MO();
		Attribute className = ele.attribute("className");
		Attribute fdn = ele.attribute("fdn");

		mo.setClassName(className.getText());
		mo.setFdn(fdn.getText());

		Iterator<Element> itForMO = ele.elementIterator();
		while (itForMO.hasNext()) {
			Element nextEle = itForMO.next();
			if (nextEle.getName().equals("attr")) {
				Attribute name = nextEle.attribute("name");
				mo.getMapAttr().put(name.getText(), nextEle.getText());

			} else if (nextEle.getName().equals("MO")) {
				MO moC = listNodes(nextEle);
				mo.getListMO().add(moC);
			}
			
		}

		return mo;
	}

	public static void main(String[] args) throws Exception {
		parse();
	}

}
