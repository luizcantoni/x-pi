package xplane.datagroup;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DATAGroupRepository {
	private Map<String, DATAGroup> dataGroups;
	private Map<Integer, Map<Integer, DATA>> dataByMessage;

	public DATAGroupRepository() {
		dataGroups = DATAGroupFactory.createDATAGroupsFromXML("/media/truecrypt1/Dados/Desenvolvimento/Java/Projetos/workspaceJ2SE/XPlaneInterface/DATAGroupConfig.xml");
		buildDATAByMessage();
	}
	
	private void buildDATAByMessage() {
		Iterator<DATAGroup> itGroups = dataGroups.values().iterator();
		
		dataByMessage = new HashMap<Integer, Map<Integer,DATA>>();
		
		while (itGroups.hasNext()) {
			DATAGroup group = itGroups.next();
			
			Iterator<DATA> itEntries = group.getEntries().values().iterator();
			
			while (itEntries.hasNext()) {
				DATA entry = itEntries.next();
				
				if (!dataByMessage.containsKey(entry.getMessage())) {
					dataByMessage.put(entry.getMessage(), new HashMap<Integer, DATA>());
				}
				
				dataByMessage.get(entry.getMessage()).put(entry.getParameter(), entry);
			}
		}
	}
	
	public DATA getDATA(int message, int parameter) {
		if (dataByMessage.containsKey(message) && dataByMessage.get(message).containsKey(parameter))
			return dataByMessage.get(message).get(parameter);
		else
			return null;
	}
	
	public DATA getDATA(String groupName, String dataName) {
		if (dataGroups.containsKey(groupName) && dataGroups.get(groupName).getEntries().containsKey(dataName)) 
			return dataGroups.get(groupName).getEntries().get(dataName);	
		else
			return null;
	}
	
	public Map<String, DATAGroup> getDataGroups() {
		return dataGroups;
	}

	public void setDataGroups(Map<String, DATAGroup> dataGroups) {
		this.dataGroups = dataGroups;
	}

	public Map<Integer, Map<Integer, DATA>> getDataByMessage() {
		return dataByMessage;
	}

	public void setDataByMessage(Map<Integer, Map<Integer, DATA>> dataByMessage) {
		this.dataByMessage = dataByMessage;
	}
}
