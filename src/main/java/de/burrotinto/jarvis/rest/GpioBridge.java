package de.burrotinto.jarvis.rest;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import lombok.var;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GpioBridge {

    final GpioController gpioController = GpioFactory.getInstance();
    final Map<Integer, GpioPinDigitalOutput> gpioPinDigitalOutputs = Arrays.stream(RaspiPin.allPins())
            .filter(p -> p.getName().startsWith("GPIO"))
            .map(p -> gpioController.provisionDigitalOutputPin(p, "", com.pi4j.io.gpio.PinState.LOW))
            .collect(Collectors.toMap(gpioPinDigitalOutput -> gpioPinDigitalOutput.getPin().getAddress(), p -> p));

    public String setPin(Integer pin, String value) {
        var gpioPinDigitalOutput = gpioPinDigitalOutputs.get(pin);
        if (gpioPinDigitalOutput != null) {
            gpioPinDigitalOutput.setState(com.pi4j.io.gpio.PinState.valueOf(value));
        }
        return getPin(pin);
    }

    public String getPin(Integer pin) {
        var gpioPinDigitalOutput = gpioPinDigitalOutputs.get(pin);
        if (gpioPinDigitalOutput != null) {
            return gpioPinDigitalOutput.getState().toString();
        }
        return "";
    }

}
