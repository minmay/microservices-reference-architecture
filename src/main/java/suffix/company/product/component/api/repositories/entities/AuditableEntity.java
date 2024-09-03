package suffix.company.product.component.api.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 * Parent for entities that are auditable. All the changes to these records are automatically logged by the database.
 *
 */
@Getter
@Setter
@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
public abstract class AuditableEntity extends AbstractEntity {

	public static final long DEFAULT_UPDATED_BY_ID = 1;

	/**
	 * When record was created.
	 */
	@JsonIgnore
	@Column(insertable = false, updatable = false)
	private Instant createdAt;

	/**
	 * When record was last updated.
	 */
	@JsonIgnore
	@Column(insertable = false, updatable = false)
	private Instant updatedAt;

	/**
	 * The last user to update this record.
	 */
	@JsonIgnore
	@Column
	private Long lastUpdatedBy = DEFAULT_UPDATED_BY_ID;

	/**
	 * The version count for this record.
	 */
	@JsonIgnore
	@Version
	private int version;

	public AuditableEntity(Long id) {
		super(id);
	}
}
