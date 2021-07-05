package de.dttt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/healthz")
	public String greeting() {
		return "You shouldn't be here. Go Away!";
	}
}