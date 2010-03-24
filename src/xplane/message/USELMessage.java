package xplane.message;

/**
* USELMessageRepository.java
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

public class USELMessage extends XPlaneUDPMessage {
	
	private int[] data;
	
	public USELMessage() {
		setProlouge("USEL0");
	}
	
	public USELMessage(int[] data) {
		this();
		this.data = data;
	}

	public USELMessage(String data) {
		this();
		
		String aux[] = data.split(",");
		
		this.data = new int[aux.length];
		
		for (int i = 0; i < aux.length; i++) {
			this.data[i] = Integer.parseInt(aux[i]);
		}
	}
	
	@Override
	public ByteBuffer toByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5 + (data.length*4));

    	String sa = getProlouge();
    	
    	byteBuffer.put(sa.getBytes());
    	byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    	
    	for (int i = 0; i < data.length; i++)
    		byteBuffer.putInt(data[i]);
    	
    	return byteBuffer;
	}

	@Override
	public ByteBuffer toByteBufferWithoutPrologue() {
		// TODO Auto-generated method stub
		return null;
	}
}
