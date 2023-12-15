package suffix.company.product.component.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import suffix.company.product.component.api.repositories.entities.Organization;

@RepositoryRestResource(collectionResourceRel = "organizations", path = "organizations")
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}

