package xplane.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import xplane.message.DATAMessage;

/**
 * 
 * @author luizcantoni
 *
 */
public class XPlaneUDPSender extends StoppableThread {
	private DatagramSocket datagramSocket;
	private int port;
	private String ipAddress;
	private BlockingQueue<DATAMessage> messageQueue;

	public XPlaneUDPSender(String ipAddress, int port) throws SocketException, UnknownHostException {
		super();
		this.port = port;
		this.ipAddress = ipAddress;
		datagramSocket = new DatagramSocket();
		this.keep_running = true;
		messageQueue = new ArrayBlockingQueue<DATAMessage>(10000, true);
	}

	public void sendDATAMessage(DATAMessage message) {
		send(message.toByteBuffer());
	}

	public void send(ByteBuffer bb) {
		send(bb.array());
	}

	public void send(byte[] buffer) {
		try {
			datagramSocket.send(new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ipAddress), port));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (this.keep_running) {


			if (messageQueue.size() == 0) continue;
			
			int bufferSize = messageQueue.size()*36 + 5;
			
			if (bufferSize > 41) {
				System.out.println("Redim..." + bufferSize);
				bufferSize = 41;
			}
			
			ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);

			DATAMessage m = messageQueue.poll();
			//m.encode();
			byteBuffer.put(m.toByteBuffer().array());
			send(byteBuffer);
			 try {
					sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			int s = messageQueue.size();
//			
//			if (s > 1)
//				s = 0;
//
//			for (int i = 0; i < s; i++) {
//				if (byteBuffer.position() == byteBuffer.capacity())
//					break;
//				
//				m = messageQueue.poll();
//				m.encode();
//				byteBuffer.put(m.toByteBufferWithoutPrologue().array());
//			}
//			
//			send(byteBuffer);
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public BlockingQueue<DATAMessage> getMessageQueue() {
		return messageQueue;
	}

	public void setMessageQueue(BlockingQueue<DATAMessage> messageQueue) {
		this.messageQueue = messageQueue;
	}
}
