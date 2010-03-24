package xplane.test;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import xplane.XPlaneInterface;

public class Test  {
	public static void main(String[] args) {
		XPlaneInterface xpi = null;

		try {
			xpi = new XPlaneInterface("192.168.1.126", 49002, 49000, "DATAGroupConfig.xml");
			xpi.unregisterDATAMessages("*");
			xpi.registerDATAMessages("1,3,4,6,13,18,20,21,25,45,62,108,116,117,118");
			xpi.startReceiving();
			Thread.sleep(1000);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		float f = xpi.getValue("position.ias");
		System.out.println(f);
		xpi.setValue("engine.throttleCommand1", 0.567f);
		
		System.out.println(xpi.getValue("position.ias"));
		System.out.println(xpi.getValue("position.latitude"));
		System.out.println(xpi.getValue("position.longitude"));
		System.out.println(xpi.getValue("position.altMsl"));
		
		System.out.println(xpi.getValue("time.realTime"));
		System.out.println(xpi.getValue("time.totalTime"));
		System.out.println(xpi.getValue("time.missingTime"));
		System.out.println(xpi.getValue("time.timer"));
		
		System.out.println(xpi.getValue("engine.throttleCommand1"));
		
		xpi.setValue("time.timer", 2.0f);
		
		Map<String, Float> values;
		values = new HashMap<String, Float>();
		values.put("time.timer", 2.2f);
		values.put("engine.throttleCommand1", 1.0f);
		xpi.setValues(values);
	}
}
