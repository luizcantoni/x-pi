package xplane.datagroup;

/**
* DATAGroup.java
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
