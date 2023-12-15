package suffix.company.product.component.implementation.configurations;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class JacksonConfig {
	@Bean
	public ObjectMapper objectMapper() {
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		configureDateTimeDeSerialization(javaTimeModule, DateTimeFormatter.ISO_INSTANT);
		return new Jackson2ObjectMapperBuilder()
				.modules(javaTimeModule).build();
	}

	private void configureDateTimeDeSerialization(JavaTimeModule javaTimeModule, DateTimeFormatter dateTimeFormatter) {
		javaTimeModule.addSerializer(Instant.class, new JsonSerializer<>() {
			@Override
			public void serialize(Instant value, JsonGenerator g, SerializerProvider serializers) throws IOException {
				g.writeString(dateTimeFormatter.format(value));
			}
		});
	}
}
