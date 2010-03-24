package xplane.message;

import java.nio.ByteBuffer;

public class PAUSMessage extends XPlaneUDPMessage {
	
	private char data;
	
	public PAUSMessage() {
		setProlouge("PAUS0");
	}
	
	@Override
	public ByteBuffer toByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);

    	String sa = getProlouge();
    	
    	byteBuffer.put(sa.getBytes());
    	
    	return byteBuffer;
	}

	@Override
	public ByteBuffer toByteBufferWithoutPrologue() {
		// TODO Auto-generated method stub
		return null;
	}

	public char getData() {
		return data;
	}

	public void setData(char data) {
		this.data = data;
	}

}
