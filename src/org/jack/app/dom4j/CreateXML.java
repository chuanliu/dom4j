package org.jack.app.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import java.io.*;

public class CreateXML {
	public void generateDocument() {
		Document document = DocumentHelper.createDocument();
		Element catalogElement = document.addElement("catalog");
		catalogElement.addComment("An XML Catalog");
		catalogElement.addProcessingInstruction("target", "text");
		Element journalElement = catalogElement.addElement("journal");
		journalElement.addAttribute("title", "XML Zone");
		journalElement.addAttribute("publisher", "IBM developerWorks");
		Element articleElement = journalElement.addElement("article");
		articleElement.addAttribute("level", "Intermediate");
		articleElement.addAttribute("date", "December-2001");
		Element titleElement = articleElement.addElement("title");
		titleElement.setText("Java configuration with XML Schema");
		Element authorElement = articleElement.addElement("author");
		Element firstNameElement = authorElement.addElement("firstname");
		firstNameElement.setText("Marcello");
		Element lastNameElement = authorElement.addElement("lastname");
		lastNameElement.setText("Vitaletti");
		
		document.addDocType("catalog", null, "file://c:/Dtds/catalog.dtd");
		try {
			XMLWriter output = new XMLWriter(new FileWriter(new File(
					"d:/catalog.xml")));
			output.write(document);
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] argv) {
		CreateXML dom4j = new CreateXML();
		dom4j.generateDocument();
	}
}
