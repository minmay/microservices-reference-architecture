package suffix.company.product.component.implementation.controllers;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import suffix.company.product.component.api.entities.OrganizationalUnit;

@RepositoryRestResource(collectionResourceRel = "organizationalUnits", path = "organizationalUnits")
public interface OrganizationalUnitsRepository extends PagingAndSortingRepository<OrganizationalUnit, Long> {
}
