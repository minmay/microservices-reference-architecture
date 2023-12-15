package suffix.company.product.component.api.repositories.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents a single refinery. An organization can have multiple refineries.
 */
@Getter
@Setter
@Entity
@Table(name = "organizational_units")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class OrganizationalUnit extends AuditableEntity {

	@Length(max = 50)
	private String externalId;

	@Length(max = 50)
	private String name;

	@Length(max = 10)
	@ToString.Include
	private String code;

	@Length(max = 1000)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	private Organization organization;

}
