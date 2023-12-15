package suffix.company.product.component.api.repositories.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a customer. This is the highest-level entity in the system. An organization may have one or more refineries.
 */
@Getter
@Setter
@Entity
@Table(name = "organizations")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class Organization extends AuditableEntity {

	@Length(max = 50)
	private String name;

	/**
	 * A unique identification code for the organization.
	 */
	@Length(max = 10)
	@ToString.Include
	private String code;

	@Length(max = 1000)
	private String description;

	/**
	 * The domain of this organization.
	 */
	@Length(max = 100)
	private String domain;

	/**
	 * The identity provider for this organization.
	 */
	@Length(max = 100)
	private String identityProvider;

	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
	private Set<OrganizationalUnit> organizationalUnits = new LinkedHashSet<>();

	public void addOrganizationalUnit(OrganizationalUnit organizationalUnit) {
		organizationalUnits.add(organizationalUnit);
		organizationalUnit.setOrganization(this);
	}
}
