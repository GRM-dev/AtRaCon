package pl.grm.atracon.rasp.conn.gp;

import com.pi4j.io.gpio.*;

public class GpioManager {

	private GpioController gController;

	public GpioManager() {
		gController = GpioFactory.getInstance();
	}

	public void readOnPort(Pin pin, PinPullResistance res, int times) {
		GpioPinDigitalInput inPort = gController.provisionDigitalInputPin(pin, "MyInput18", res);
		for (int i = 0; i < times; i++) {
			if (inPort.isHigh()) {
				System.out.println("1");
			} else {
				System.out.println("0");
			}
			try {
				Thread.sleep(250);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
