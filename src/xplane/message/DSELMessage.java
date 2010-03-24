package xplane.message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DSELMessage extends XPlaneUDPMessage {
	
	private int[] data;
	
	public DSELMessage() {
		setProlouge("DSEL0");
	}
	
	public DSELMessage(int[] data) {
		this();
		this.data = data;
	}

	public DSELMessage(String data) {
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
