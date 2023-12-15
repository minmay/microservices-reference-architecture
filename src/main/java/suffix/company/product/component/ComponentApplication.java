package suffix.company.product.component;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ComponentApplication {
	protected ComponentApplication() {
	}
	public static void main(String[] args) {
		SpringApplication.run(ComponentApplication.class, args);
	}
}
