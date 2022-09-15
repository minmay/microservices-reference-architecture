package suffix.company.product.component.implementation.controllers;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import suffix.company.product.component.api.entities.Organization;

@RepositoryRestResource(collectionResourceRel = "organizations", path = "organizations")
public interface OrganizationsRepository extends PagingAndSortingRepository<Organization, Long> {
}
