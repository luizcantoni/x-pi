package xplane.datagroup;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
* DATAGroupRepository.java
*  
* Copyright (C) 2010 Luiz Cantoni (luiz.cantoni@gmail.com)
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2 
* of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/
public class DATAGroupRepository {
	private Map<String, DATAGroup> dataGroups;
	private Map<Integer, Map<Integer, DATA>> dataByMessage;

	public DATAGroupRepository(String dataGroupConfigXML) {
		dataGroups = DATAGroupFactory.createDATAGroupsFromXML(dataGroupConfigXML);
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
