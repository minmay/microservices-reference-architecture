package suffix.company.product.component.implementation.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is using embedded PostgeSQL that is started by
 * <a hrer="https://www.testcontainers.org">Testcontainers</a>.
 */
class TestcontainersPgIT extends BaseRepositoryIT {

	@Autowired
	private DataSource dataSource;

	// SCENARIO: test that Gradle embedded PG database has been successfully created
	// GIVEN: an embedded PG database has been created
	// WHEN: a query is executed
	// THEN: database responds with expected results
	@Test
	void testEmbeddedPg() throws Exception {
		try (Connection conn = dataSource.getConnection()) {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("select 1");
			assertTrue(rs.next());
			String result = rs.getString(1);
			assertEquals("1", result);
			rs.close();
			statement.close();
		}
	}
}
