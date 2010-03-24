package xplane.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import xplane.message.DATAMessage;

/**
 * This class is a repository of DATAMessage and DATAMessageParameter.
 *  
 * @author luizcantoni
 *
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
