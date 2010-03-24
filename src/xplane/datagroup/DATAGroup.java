package xplane.datagroup;

import java.util.HashMap;
import java.util.Map;

public class DATAGroup {
	private Map<String, DATA> entries;
	private String name;
	
	public DATAGroup() {
		entries = new HashMap<String, DATA>();
	}
	
	public Map<String, DATA> getEntries() {
		return entries;
	}
	
	public void setEntries(Map<String, DATA> entries) {
		this.entries = entries;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
