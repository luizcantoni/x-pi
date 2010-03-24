package xplane.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import xplane.message.DATAMessage;

/**
* DATAMessageRepository.java
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
public class DATAMessageRepository {
	private Map<Integer, DATAMessage> messages;
	private Set<Integer> allowedMessages; 
	
	public DATAMessageRepository() {
		messages = new HashMap<Integer, DATAMessage>();
		allowedMessages = new HashSet<Integer>();
	}

	public boolean containsMessage(int index) {
		return messages.containsKey(index);
	}
	
	public DATAMessage getMessage(int index) {
		return messages.get(index);
	}
	
	public Set<Integer> getAllowedMessages() {
		return allowedMessages;
	}

	public void setAllowedMessages(Set<Integer> allowedMessages) {
		this.allowedMessages = allowedMessages;
	}
	
	public Map<Integer, DATAMessage> getMessages() {
		return messages;
	}

	public void setMessages(Map<Integer, DATAMessage> messages) {
		this.messages = messages;
	}
}
