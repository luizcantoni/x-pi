package xplane.message;

import java.nio.ByteBuffer;

public class CHARMessage extends XPlaneUDPMessage {
	
	private char key;
	
	public CHARMessage(char key) {
		setProlouge("CHAR0");
		this.key = key;
	}
	
	@Override
	public ByteBuffer toByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5 + 2);
    	String sa = getProlouge();
    	byteBuffer.put(sa.getBytes());
    	byteBuffer.put((byte)key);
    	return byteBuffer;
	}

	@Override
	public ByteBuffer toByteBufferWithoutPrologue() {
		// TODO Auto-generated method stub
		return null;
	}
}
