package suffix.company.product.component.implementation.repositories;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Base class for all integration tests that require embedded database. By extending from this class tests can make sure
 * that there will be a clean copy of our Postgres database with some test data that is inserted by src/resources/db.changelog/test-data.sql
 * script. Before each test DB data will be cleaned up and test data will be inserted. All tests will run with a predefined DB state.
 */
@AutoConfigureMockMvc
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = {
				"spring.liquibase.change-log=classpath:db/changelog/db.changelog-test.xml",
				"spring.datasource.url=jdbc:tc:postgresql:12.5://localhost:5432/product",
				"spring.liquibase.enabled=true"
		})
@Testcontainers
@Sql(scripts = {"classpath:db/changelog/clean-data.sql", "classpath:db/changelog/test-data.sql"})
public class NonTransactionalBaseRepositoryIT {
}
