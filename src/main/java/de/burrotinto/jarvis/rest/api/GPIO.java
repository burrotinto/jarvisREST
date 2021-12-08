package de.burrotinto.jarvis.rest.api;

import de.burrotinto.jarvis.rest.GpioBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GPIO {

    @Autowired
    private GpioBridge bridge;

    @GetMapping("/gpio/{id}")
    public String getState(
            @PathVariable(value = "id") int gpio) {
        return bridge.getPin(gpio);
    }

    @GetMapping("/gpio/{id}/{state}")
    public String setState(
            @PathVariable(value = "id") int gpio,
            @PathVariable(value = "state") String state) {
        return bridge.setPin(gpio, state);
    }
}
