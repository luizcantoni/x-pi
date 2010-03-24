package xplane.message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DATAREFMessage extends XPlaneUDPMessage {
	
	private float value;
	private String dataRef;
	
	public DATAREFMessage(String dataRef, float value) {
		setProlouge("DREF0");
		this.dataRef = dataRef;
		this.value = value;
	}
	
	@Override
	public ByteBuffer toByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(getProlouge().length() + 4 + 500);

    	String sa = getProlouge();
    	
    	byteBuffer.put(sa.getBytes());
    	byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    	byteBuffer.putFloat(value);
    	byteBuffer.put(dataRef.getBytes());
    	
    	return byteBuffer;
	}
	
	@Override
	public ByteBuffer toByteBufferWithoutPrologue() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + 1);
    	
    	return byteBuffer;
	}
}
