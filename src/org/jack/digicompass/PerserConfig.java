package org.jack.digicompass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class PerserConfig {
	
	public void perserDocument() throws IOException {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File("D:/config.xml"));
			Element root = document.getRootElement();
			Iterator rootIter = root.elementIterator();
			Map<String, Element> elementMap= new HashMap();
			while(rootIter.hasNext()){
				Element element= (Element) rootIter.next();
				if(element.attribute("name")!=null){
					elementMap.put(element.attribute("name").getText(), element);
				}else if(element.attribute("ref")!=null){
					root.add(elementMap.get(element.attribute("ref").getText()));
					root.remove(element);
				}
				TreeView(element, elementMap) ;
				
			}
			XMLWriter output = new XMLWriter(new FileWriter(new File(
					"d:/config-modified.xml")));
			output.write(document);
			output.close();
		}
		
		catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void TreeView(Element element, Map<String, Element> elementMap){
		if(element.attribute("name")!=null){
			elementMap.put(element.attribute("name").getText(), element);
			Iterator<Element> elementsubIter = element.elementIterator();
			while (elementsubIter.hasNext()) {
				TreeView((Element)elementsubIter.next(), elementMap);
		}
			
		}else if(element.attribute("ref")!=null && !"".equals(element.attribute("ref").getText())){
			Element elementParent=element.getParent();
			elementParent.add((Element)
					elementMap.get(element.attribute("ref").getText()).clone());
			elementParent.remove(element);
		}else{
			Iterator<Element> elementsubIter = element.elementIterator();
			while (elementsubIter.hasNext()) {
				TreeView((Element)elementsubIter.next(), elementMap);
		}
		}
	}

	public static void main(String[] argv) throws IOException {
		PerserConfig dom4jParser = new PerserConfig();
		dom4jParser.perserDocument();
	}
}
