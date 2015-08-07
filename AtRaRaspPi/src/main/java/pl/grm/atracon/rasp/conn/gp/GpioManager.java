package pl.grm.atracon.rasp.conn.gp;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;

public class GpioManager {

	private GpioController gController;

	public GpioManager() {
		gController = GpioFactory.getInstance();
		// readOnPort(RaspiPin.GPIO_18,PinPullResistance.PULL_DOWN);
	}

	public void readOnPort(Pin pin, PinPullResistance res) {
		GpioPinDigitalInput inPort18 = gController.provisionDigitalInputPin(pin, "MyInput18", res);
		if (inPort18.isHigh()) {
			System.out.println("1");
		} else {
			System.out.println("0");
		}
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
