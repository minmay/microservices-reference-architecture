package suffix.company.product.component.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import suffix.company.product.component.api.repositories.entities.OrganizationalUnit;


@RepositoryRestResource(collectionResourceRel = "organizationalUnits", path = "organizationalUnits")
public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnit, Long> {
}

