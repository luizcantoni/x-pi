package xplane.datagroup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DATAGroupFactory {
	
	public static Map<String, DATAGroup> createDATAGroupsFromXML(String xmlFile) {
        Map<String, DATAGroup> groups = new HashMap<String, DATAGroup>();
		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc = null;
		
        try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(xmlFile);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        // normalize text representation
        doc.getDocumentElement ().normalize();

        NodeList nl = doc.getElementsByTagName("DATAGroups");
        nl = ((Element) nl.item(0)).getElementsByTagName("DATAGroup");

        Element tag = null;		
        
        //Percorre todos os DATAMessage
        for (int i = 0; i < nl.getLength(); i++) {
        	tag = (Element)nl.item(i);
        	
        	DATAGroup group = new DATAGroup();
        	group.setName(tag.getAttribute("name"));
        	
        	NodeList nlParameters = tag.getElementsByTagName("Entries").item(0).getChildNodes();
        	
            for (int j = 0; j < nlParameters.getLength(); j++) {
            	if (nlParameters.item(j).getNodeType() == Node.ELEMENT_NODE) {
            		Element tag2 = (Element)nlParameters.item(j);
            		
            		int message, parameter;
            		
            		message = Integer.parseInt(tag2.getAttribute("message"));
            		parameter = Integer.parseInt(tag2.getAttribute("parameter"));
            		
            		
            		DATA data = new DATA();
            		data.setName(tag2.getAttribute("name"));
            		data.setMessage(message);
            		data.setParameter(parameter);
            		data.setXPlaneName(tag2.getAttribute("xplaneName"));
            		data.setUnit(tag2.getAttribute("unit"));
            		data.setConvertTo(tag2.getAttribute("convertTo"));
            		data.setReadOnly(tag2.getAttribute("readOnly").toUpperCase().equals("TRUE"));
            		data.setUnit(tag2.getAttribute("desc"));
                	
                	group.getEntries().put(data.getName(), data);
            	}
            }
            
            groups.put(group.getName(), group);
		}        
        
        return groups;
	}

}
