package xplane.communication;

/**
 * XPlaneDataPacketDecoder.java
 * 
 * Decodes the data contained in received DATA packets from X-Plane.
 * 
 * Based on the implementation of XHSI (High Resolution HSI for X-Plane)
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


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.BlockingQueue;

import xplane.datagroup.DATA;
import xplane.datagroup.DATAGroupRepository;
import xplane.message.DATAMessage;
import xplane.message.DATAMessageRepository;
import xplane.message.XPlaneUDPMessage;


public class XPlaneDataPacketDecoder implements XPlaneDataPacketObserver {

	private DATAMessageRepository dataMessageRepository;
	private DATAGroupRepository dataGroupRepository;

	BlockingQueue<XPlaneUDPMessage> messageQueue;

	public XPlaneDataPacketDecoder(DATAMessageRepository dataMessageRepository, DATAGroupRepository dataGroupRepository) {
		this.dataMessageRepository = dataMessageRepository;
		this.dataGroupRepository = dataGroupRepository;
	}

	public void newSimData(byte[] sim_data) throws Exception {
		// identify the packet type (identified by the first four bytes)
		String packet_type = new String(sim_data,0,4).trim();

		if (packet_type.equals("DATA")) {
			int index;//, segments;
			float value = -999;
			//segments = (sim_data.length - 5)/36;

			ByteBuffer byteBuffer = ByteBuffer.wrap(sim_data, 5, sim_data.length - 5);
			byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

			for (int seg = 0; seg < dataMessageRepository.getAllowedMessages().size(); seg++) {
				index = byteBuffer.getInt();

				if (dataMessageRepository.getAllowedMessages().contains(index)) {
					DATAMessage dataMessage = dataMessageRepository.getMessage(index);
					DATA data = null;

					if (dataMessage != null) {
						for (int i = 0; i < 8; i++) {
							value = byteBuffer.getFloat();

							dataMessage.getRXData()[i] = value;

							data = dataGroupRepository.getDATA(index, i);

							if (data != null) {
								data.setValue(value);
							}
						}
					}
				}
			}
		}
	}
}
