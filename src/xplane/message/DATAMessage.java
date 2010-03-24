package xplane.message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
* DATAMessage.java
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
public class DATAMessage extends XPlaneUDPMessage {
	
	private int index;
	private float[] TXData;
	private float[] RXData;
	
	public DATAMessage() {
		setProlouge("DATA0");
		TXData = new float[8];
		RXData = new float[8];
		clearTXData();
	}
	
	public void clearTXData() {
		for (int i = 0; i < TXData.length; i++)
			TXData[i] = -999;
	}
	
	@Override
	public ByteBuffer toByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(getProlouge().length() + 4 + (TXData.length*4));

    	String sa = getProlouge();
    	
    	byteBuffer.put(sa.getBytes());
    	byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    	byteBuffer.putInt(index);
    	
    	for (int i = 0; i < TXData.length; i++)
    		byteBuffer.putFloat(TXData[i]);
    	
    	return byteBuffer;
	}
	
	@Override
	public ByteBuffer toByteBufferWithoutPrologue() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + TXData.length*4);

    	byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    	byteBuffer.putInt(index);
    	
    	for (int i = 0; i < TXData.length; i++)
    		byteBuffer.putFloat(TXData[i]);
    	
    	return byteBuffer;
	}

	
	public String toString() {
		String s = index + " - ";
		return s;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public float[] getTXData() {
		return TXData;
	}

	public void setTXData(float[] tXData) {
		TXData = tXData;
	}

	public float[] getRXData() {
		return RXData;
	}

	public void setRXData(float[] rXData) {
		RXData = rXData;
	}
}
