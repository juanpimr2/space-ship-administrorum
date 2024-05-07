package com.aeronauticaimperialis.spaceshipadministrorum.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerPrincipalis {
	
    @GetMapping("/praiseTheEmperor")
    public String praiseTheEmperor() {
        return "En la luz del Emperador, encontramos la fuerza para purgar la oscuridad y forjar un futuro glorioso para la humanidad";
    }
}
