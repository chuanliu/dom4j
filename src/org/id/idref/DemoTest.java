package org.id.idref;
//http://stackoverflow.com/questions/9909465/how-to-disable-dtd-fetching-using-jaxb2-0

import java.io.FileReader;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class DemoTest {

    public static void main(String[] args) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(FilterConfig.class);

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        XMLReader xmlReader = spf.newSAXParser().getXMLReader();
        InputSource inputSource = new InputSource(new FileReader("D:\\ref.xml"));
        SAXSource source = new SAXSource(xmlReader, inputSource);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        FilterConfig filterConfig = (FilterConfig) unmarshaller.unmarshal(source);
        
//        filterConfig.getDimension().forEach(p -> p.getMetric().forEach(s->System.out.println(s.getName())));
//        System.out.println(filterConfig.getMeasurement().getName());
//        filterConfig.getPivot().forEach(p->System.out.println(((Measurement)(p.getMeasurements().getMeasurement().getRef())).getFunction()));
        filterConfig.getPivot().forEach(p->System.out.println(p.getMeasurements().getMeasurement().getRef().getFunction()));
//        filterConfig.getPivot().forEach(p-> System.out.println((p.getMeasurements().getMeasurement().getRef())));
//        filterConfig.getPivot().forEach(s->System.out.println(s.getObjectModel()));
        
//        filterConfig.getPivot().forEach(p->p.getDimensions().getGroup().forEach(s->s.getDimension().forEach(d->d.getMetric().forEach(x->System.out.println(x.getName())))));
    }
}
