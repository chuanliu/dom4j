package org.jack.app.dom4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Perser {
	public void perserDocument() {
		try {
			SAXReader saxReader = new SAXReader();
			long start=System.nanoTime();
			Document document = saxReader.read(new File("D:/hwcm_bigfile.xml"));
			Element root = document.getRootElement();
			Iterator rootIter = root.elementIterator();
			List<MO> listmo= new ArrayList();
			while(rootIter.hasNext()){
				listmo.add(ParseMO((Element)rootIter.next())) ;
			}
			
//			listmo.get(0).getListMO().forEach(p->System.out.println(p.getClassName()));
			long end=System.nanoTime();
			System.out.println(String.format("All took: %d ms", TimeUnit.NANOSECONDS.toMillis(end-start)));

		}
		

		catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static MO ParseMO(Element elementMo){

		MO mo = new MO();
		Attribute className = elementMo.attribute("className");
		Attribute fdn = elementMo.attribute("fdn");

		mo.setClassName(className.getText());
		mo.setFdn(fdn.getText());

		Iterator<Element> itForMO = elementMo.elementIterator();
		while (itForMO.hasNext()) {
			Element nextEle = itForMO.next();
			if (nextEle.getName().equals("attr")) {
				Attribute name = nextEle.attribute("name");
				mo.getMapAttr().put(name.getText(), nextEle.getText());

			} else if (nextEle.getName().equals("MO")) {
				MO moC = ParseMO(nextEle);
				mo.getListMO().add(moC);
			}
			
		}

		return mo;
		
	}


	public static void main(String[] argv) {
		Perser dom4jParser = new Perser();
		dom4jParser.perserDocument();
	}
}
