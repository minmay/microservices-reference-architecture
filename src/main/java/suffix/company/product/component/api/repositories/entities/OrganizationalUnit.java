package suffix.company.product.component.api.repositories.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

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
	private String name;

	@Length(max = 10)
	@ToString.Include
	private String code;

	@Length(max = 1000)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	private Organization organization;

}
