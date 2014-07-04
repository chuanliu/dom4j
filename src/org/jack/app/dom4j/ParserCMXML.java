package org.jack.app.dom4j;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Attribute;

import java.util.List;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.dom4j.io.XMLWriter;

import java.io.*;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class ParserCMXML {
	public void modifyDocument() {
		try {
			long t0 = System.nanoTime();
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File("src/hwcm_bigfile.xml"));
//			String stringXML = document.asXML();
			
//
			long t1 = System.nanoTime();
	        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
	        System.out.println(String.format("read took: %d ms", millis));
	        
	        List<Attribute> list = document.selectNodes("/MOTree/MO/MO");
//			list.forEach(p -> System.out.println(p.asXML()));
			
			long t2 = System.nanoTime();
	        long millis2 = TimeUnit.NANOSECONDS.toMillis(t2 - t1);
	        System.out.println(String.format("select took: %d ms", millis2));
			
//			 Iterator iter = list.iterator();
			// while (iter.hasNext()) {
			// Attribute attribute = (Attribute) iter.next();
			// if (attribute.getValue().equals("Intermediate"))
			// attribute.setValue("Introductory");
			// }

//			list = document.selectNodes("//article/@date");
//			iter = list.iterator();
//			while (iter.hasNext()) {
//				Attribute attribute = (Attribute) iter.next();
//				if (attribute.getValue().equals("December-2001"))
//					attribute.setValue("October-2002");
//			}
//			list = document.selectNodes("//article");
//			iter = list.iterator();
//			while (iter.hasNext()) {
//				Element element = (Element) iter.next();
//				Iterator iterator = element.elementIterator("title");
//				while (iterator.hasNext()) {
//					Element titleElement = (Element) iterator.next();
//					if (titleElement.getText().equals(
//							"Java configuration with XMLSchema"))
//						titleElement
//								.setText("Create flexible and extensible XML schema");
//				}
//			}
//			list = document.selectNodes("//article/author");
//			iter = list.iterator();
//			while (iter.hasNext()) {
//				Element element = (Element) iter.next();
//				Iterator iterator = element.elementIterator("firstname");
//				while (iterator.hasNext()) {
//					Element firstNameElement = (Element) iterator.next();
//					if (firstNameElement.getText().equals("Marcello"))
//						firstNameElement.setText("Ayesha");
//					System.out.println(firstNameElement.asXML());
//				}
//			}
//			list = document.selectNodes("//article/author");
//			iter = list.iterator();
//			while (iter.hasNext()) {
//				Element element = (Element) iter.next();
//				Iterator iterator = element.elementIterator("lastname");
//				while (iterator.hasNext()) {
//					Element lastNameElement = (Element) iterator.next();
//					if (lastNameElement.getText().equals("Vitaletti"))
//						lastNameElement.setText("Malik");
//				}
//			}
//			XMLWriter output = new XMLWriter(new FileWriter(new File(
//					"d:/catalog-modified.xml")));
//			output.write(document);
//			output.close();
		}

		catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] argv) {
		ParserCMXML dom4jParser = new ParserCMXML();
		dom4jParser.modifyDocument();
	}
}
