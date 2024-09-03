package suffix.company.product.component.api.repositories.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

/**
 * Parent class for all entities.
 */
@Getter
@Setter
@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public abstract class AbstractEntity implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ToString.Include
	private Long id;

	/**
	 * Implementation of equals and hashcode where there are no natural business keys, taken from Vlad Mihalcea's
	 * <a href=https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier>blog</a>
	 * with minor changes.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!getClass().getCanonicalName().equals(obj.getClass().getCanonicalName())) {
			return false;
		}

		AbstractEntity other = (AbstractEntity) obj;

		return getId() != null && getId().equals(other.getId());
	}

	public AbstractEntity(Long id) {
		this.id = id;
	}

	/**
	 * Implementation of equals and hashcode where there are no natural business keys, taken from Vlad Mihalcea's
	 * <a href=https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier>blog</a>.
	 */
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public boolean isNew() {
		return id == null;
	}
}
