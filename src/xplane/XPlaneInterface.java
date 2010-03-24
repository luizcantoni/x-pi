package xplane;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import xplane.communication.XPlaneDataPacketDecoder;
import xplane.communication.XPlaneUDPReceiver;
import xplane.communication.XPlaneUDPSender;
import xplane.datagroup.DATA;
import xplane.datagroup.DATAGroupRepository;
import xplane.message.DATAMessage;
import xplane.message.DATAMessageRepository;
import xplane.message.DATAREFMessage;
import xplane.message.DSELMessage;
import xplane.message.USELMessage;
import xplane.message.XPlaneUDPMessage;
import xplane.util.Util;

/**
* XPlaneInterface.java
* 
* This class provides the interface between an application and X-Plane. 
* With this class it is possible to receive and send data to X-Plane.
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
public class XPlaneInterface {
	private XPlaneUDPReceiver xPlaneUDPReceiver;
	private XPlaneUDPSender xPlaneUDPSender;
	private DATAMessageRepository dataMessageRepository;
	private Map<Integer, DATAMessage> messagesToSend;
	private DATAGroupRepository dataGroupRepository;
	
	/**
	 * 
	 * @param xplaneIP - The machine IP where X-Plane it is being executed. Remember that X-Plane must be configured to send data over the network
	 * @param receivePort - The UDP port which data will be received.
	 * @param sendPort - The UDP port which data will be send.
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	public XPlaneInterface(String xplaneIP, int receivePort, int sendPort) throws SocketException, UnknownHostException {
		xPlaneUDPReceiver = new XPlaneUDPReceiver(xplaneIP, receivePort);
		xPlaneUDPSender = new XPlaneUDPSender(xplaneIP, sendPort);
		dataMessageRepository = new DATAMessageRepository();
		dataGroupRepository = new DATAGroupRepository();
		xPlaneUDPReceiver.addReceptionObserver(new XPlaneDataPacketDecoder(dataMessageRepository, dataGroupRepository));
		messagesToSend = new HashMap<Integer, DATAMessage>();
	}
	
	/**
	 * This method starts the interface with X-Plane.
	 */
	public void start() {
		startReceiving();
		startSending();
	}
	
	/**
	 * This method stops the interface with X-Plane.
	 */
	public void stop() {
		stopReceiving();
		stopSending();
	}
	
	/**
	 * This method starts the thread that is responsible to receive data from X-Plane.
	 */
	public void startReceiving() {
		xPlaneUDPReceiver.start();
	}
	
	/**
	 * This method stops the thread that is responsible to receive data from X-Plane.
	 */
	public void stopReceiving() {
		xPlaneUDPReceiver.setKeep_running(false);
	}
	
	public void startSending() {
		xPlaneUDPSender.start();
	}
	
	public void stopSending() {
		xPlaneUDPSender.setKeep_running(false);
	}
	
	/**
	 * This method register (in X-Plane) the messages that must be received from X-Plane
	 * The argument is a comma separated string. 
	 * 
	 * In practice, this method will send DSEL messages to X-Plane.
	 * 
	 * registerDataMessage("1,2,56,89")
	 * 
	 * @param data
	 */
	public void registerDATAMessages(String data) {
		DSELMessage message = new DSELMessage(data);
		xPlaneUDPSender.send(message.toByteBuffer());
		
		Integer[] a = Util.toIntArray(data);
		
		dataMessageRepository.getAllowedMessages().addAll(
				Arrays.asList(a));
		
		for (int i = 0; i < a.length; i++) {
			DATAMessage m = new DATAMessage();
			m.setIndex(a[i]);
			dataMessageRepository.getMessages().put(a[i], m);
		}
	}

	/**
	 * This method unregister (in X-Plane) the messages that must not be received from X-Plane
	 * The argument is a comma separated string.
	 *  
	 * In practice, this method will send USEL messages to X-Plane.
	 * 
	 * unregisterDataMessage("1,2,56,89")
	 * 
	 * @param data
	 */
	public void unregisterDATAMessages(String data) {
		if (data.equals("*")) {
			data = "";
			int cont = 0;
			for (int i = 0; i <= 127; i++) {
				
				data = data + i + ",";
				
				if (cont == 30) {
					data = data.substring(0, data.length()-1);
					unregisterDATAMessages(data);
					data = "";
					cont = 0;
				}
				else 
					cont++;
			}
			
			data = data.substring(0, data.length()-1);
		}
		
		Integer[] a = Util.toIntArray(data);
		
		USELMessage message = new USELMessage(data);
		xPlaneUDPSender.send(message.toByteBuffer());
		
		dataMessageRepository.getAllowedMessages().removeAll(
				Arrays.asList(a));

		for (int i = 0; i < a.length; i++) {
			if (dataMessageRepository.getMessages().containsKey(a[i]))
				dataMessageRepository.getMessages().remove(a[i]);
		}
	}
	
	public float getValue(String key) {
		String[] auxKey = key.split("\\.");
		return getValue(auxKey[0], auxKey[1]);
	}
	
	public float getValue(String groupName, String dataName) {
		return dataGroupRepository.getDATA(groupName, dataName).getValue();
	}
	
	public void setValue(String key, float value, boolean sendNow) {
		String[] auxKey = key.split("\\.");
		DATA data = dataGroupRepository.getDATA(auxKey[0], auxKey[1]);
		DATAMessage message = dataMessageRepository.getMessage(data.getMessage());
		message.getTXData()[data.getParameter()] = value;

		if (sendNow) {
			sendMessage(message);
			message.clearTXData();
		}
		else {
			if (!messagesToSend.containsKey(message.getIndex())) {
				messagesToSend.put(message.getIndex(), message);
			}
		}			
	}

	public void setValue(String key, float value) {
		setValue(key, value, true);
	}

	public void setValues(Map<String, Float> entries) {
		Iterator<String> it = entries.keySet().iterator();
		
		messagesToSend.clear();
		
		while (it.hasNext()) {
			String dataName = it.next();
			setValue(dataName, entries.get(dataName), false);
		}
		
		ByteBuffer bb = ByteBuffer.allocate(5 + (messagesToSend.size()*36));
		
		DATAMessage m = null;
		
		Iterator<DATAMessage> itMessages = messagesToSend.values().iterator();
		
		if (itMessages.hasNext()) {
			m = itMessages.next();
			bb.put(m.toByteBuffer().array());
			m.clearTXData();
		}
		
		while (itMessages.hasNext()) {
			m = itMessages.next();
			bb.put(m.toByteBufferWithoutPrologue().array());
			m.clearTXData();
		}
		
		xPlaneUDPSender.send(bb);
	}

	public void setDataRefValue(String dataRef, float value) {
		DATAREFMessage drefMessage = new DATAREFMessage(dataRef, value);
		sendMessage(drefMessage);
	}
	
	public void sendVEH1(double latLonAlt[], float headPitchRoll[], float gearFlapThrust[]) throws UnknownHostException, IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(5 + 4 + latLonAlt.length*8 + headPitchRoll.length*4 + gearFlapThrust.length*4);

		String sa = "VEH10";

		byteBuffer.put(sa.getBytes());
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putInt(0);

		for (int i = 0; i < latLonAlt.length; i++) {
			byteBuffer.putDouble(latLonAlt[i]);
		}

		for (int i = 0; i < headPitchRoll.length; i++) {
			byteBuffer.putFloat(headPitchRoll[i]);
		}

		for (int i = 0; i < gearFlapThrust.length; i++) {
			byteBuffer.putFloat(gearFlapThrust[i]);
		}

		xPlaneUDPSender.send(byteBuffer);
	}	
	
	public void sendMessage(XPlaneUDPMessage xpm) {
		xPlaneUDPSender.send(xpm.toByteBuffer());
	}
	
	public DATAMessage getDATAMessage(int index) {
		return dataMessageRepository.getMessage(index);
	}
}
