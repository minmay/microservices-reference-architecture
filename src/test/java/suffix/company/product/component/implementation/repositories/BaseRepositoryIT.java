package suffix.company.product.component.implementation.repositories;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import suffix.company.product.component.api.repositories.OrganizationRepository;
import suffix.company.product.component.api.repositories.OrganizationalUnitRepository;

/**
 * Base class for all integration tests that require embedded database. By extending from this class tests can make sure
 * that there will be a clean copy of our Postgres database with some test data that is inserted by src/resources/db.changelog/test-data.sql
 * script. All tests will run in a transaction that will be automatically rolled back so the database will always be
 * in its initial state in order to make sure that tests don't modify the data in a way that can effect other tests.
 */
@Rollback
@Transactional
@SpringBootTest(properties = {
		"spring.liquibase.change-log=classpath:db/changelog/db.changelog-test.xml",
		"spring.datasource.url=jdbc:tc:postgresql:12.5://localhost:5432/product",
		"spring.liquibase.enabled=true",
		"logging.level.suffix.company.product.component.api.repositories=TRACE",
		"logging.level.org.springframework.jdbc=DEBUG"
})
@Testcontainers
public abstract class BaseRepositoryIT {
	@Autowired
	protected OrganizationRepository organizationRepository;

	@Autowired
	protected OrganizationalUnitRepository organizationalUnitsRepository;


	@Autowired
	protected EntityManager entityManager;
}

