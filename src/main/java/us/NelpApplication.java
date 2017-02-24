package us;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class NelpApplication {

	@RequestMapping("/")
	String home(@RequestParam(defaultValue = "World!!!!") String name) {
		return String.format("Hello %s", name);
	}

	public static void main(String[] args) {
		SpringApplication.run(NelpApplication.class, args);
	}
}
