package suffix.company.product.component.implementation.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is using embedded PostgeSQL that is started by
 * <a hrer="https://www.testcontainers.org">Testcontainers</a>.
 */
class TestcontainersPgLiquibaseSetupIT extends BaseRepositoryIT {

	@Autowired
	private DataSource dataSource;

	// SCENARIO: retrieve a record inserted by Liquibase migration
	// GIVEN: an embedded PG database has been created and all Liquibase migrations have been applied
	// WHEN: we try to retrieve a record inserted by migration
	// THEN: the record is returned
	@Test
	void testLiquibase() throws Exception {
		try (Connection conn = dataSource.getConnection()) {
			// Lookup the record that was inserted by Liquibase: id=1
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("select id, name, code from organizations where code = 'ge'");
			assertTrue(rs.next());
			assertEquals("Galactic Empire", rs.getString("name"));
			rs.close();
			statement.close();
		}
	}

	// SCENARIO: insert a record into the table created by Liquibase migration
	// GIVEN: an embedded PG database has been created and all Liquibase migrations have been applied
	// WHEN: a new record is inserted into a table
	// THEN: the record is correctly stored and can be retrieved
	@Test
	void testLiquibaseInsert() throws Exception {
		try (Connection conn = dataSource.getConnection()) {

			// insert 1 record, after which there will be 2 records in the table, one inserted by Liquibase, and the other one inserted here.
			Statement statement = conn.createStatement();
			int count = statement.executeUpdate("insert into organizations (name, code) values ('ACME', 'acme')");
			assertEquals(1, count);
			statement.close();

			// Lookup the record that was inserted by this test, code=acme
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("select id, name, code, created_at, updated_at, version from organizations where code = 'acme'");
			assertTrue(rs.next());
			assertEquals("ACME", rs.getString("name"));
			assertNotNull(rs.getTimestamp("created_at"));
			assertNotNull(rs.getTimestamp("updated_at"));
			assertEquals(1, rs.getInt("version"));
			rs.close();
			statement.close();
		}
	}
}
