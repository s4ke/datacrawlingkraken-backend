package com.datacrawling.kraken.controller;

import java.util.List;

import com.datacrawling.kraken.ApplicationListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Martin Braun
 * @version 1.1.0
 * @since 1.1.0
 */
@CrossOrigin
@RestController
public class EndpointController {

	@GetMapping("/endpoints")
	public List<String> getEndPoints() {
		return ApplicationListener.endPoints;
	}

}
